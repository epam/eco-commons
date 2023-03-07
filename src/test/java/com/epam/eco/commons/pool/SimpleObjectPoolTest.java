/*******************************************************************************
 *  Copyright 2022 EPAM Systems
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not
 *  use this file except in compliance with the License.  You may obtain a copy
 *  of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *  WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 *  License for the specific language governing permissions and limitations under
 *  the License.
 *******************************************************************************/
package com.epam.eco.commons.pool;

import java.io.Closeable;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Andrei_Tytsik
 */
public class SimpleObjectPoolTest {

    @Test
    public void testObjectsBorrowedAndReturned() {
        try (SimpleObjectPool<Object> pool = new SimpleObjectPool<>(Object::new)) {
            Object object1 = pool.borrowObject();

            Assertions.assertNotNull(object1);

            Object object2 = pool.borrowObject();
            Assertions.assertNotNull(object2);

            Assertions.assertNotEquals(object1, object2);

            pool.returnObject(object1);
            pool.returnObject(object2);

            Object object3 = pool.borrowObject();
            Assertions.assertNotNull(object3);
            Assertions.assertEquals(object1, object3);

            Object object4 = pool.borrowObject();
            Assertions.assertNotNull(object4);
            Assertions.assertEquals(object2, object4);
        }
    }

    @Test
    public void testSizeGrows() {
        try (SimpleObjectPool<Object> pool = new SimpleObjectPool<>(Object::new)) {
            Assertions.assertEquals(0,  pool.size());

            Object object1 = pool.borrowObject();
            Assertions.assertEquals(1,  pool.size());

            Object object2 = pool.borrowObject();
            Assertions.assertEquals(2,  pool.size());

            Object object3 = pool.borrowObject();
            Assertions.assertEquals(3,  pool.size());

            pool.returnObject(object1);
            pool.returnObject(object2);
            pool.returnObject(object3);

            Assertions.assertEquals(3,  pool.size());
        }
    }

    @Test
    public void testObjectsBorrowedAndReturnedWithDoWith() {
        try (SimpleObjectPool<Object> pool = new SimpleObjectPool<>(Object::new)) {
            Object object1 = pool.doWithObject(object -> object);
            Assertions.assertNotNull(object1);

            Object object2 = pool.doWithObject(object -> object);
            Assertions.assertNotNull(object2);
            Assertions.assertEquals(object1, object2);
        }
    }

    @Test
    public void testClosesCloseables() {
        AtomicBoolean closed = new AtomicBoolean(false);

        @SuppressWarnings("resource")
        Closeable closeable = () -> closed.set(true);

        try (SimpleObjectPool<Object> pool = new SimpleObjectPool<>(() -> closeable)) {
            pool.borrowObject();
        }
        Assertions.assertTrue(closed.get());
    }


    @Test
    public void testFailsOnIllegalArguments1() {
        assertThrows(NullPointerException.class, () -> new SimpleObjectPool<>(null));
    }

    @Test
    public void testFailsOnIllegalArguments2() {
        assertThrows(NullPointerException.class, () -> {
            try (SimpleObjectPool<Object> pool = new SimpleObjectPool<>(Object::new)) {
                pool.returnObject(null);
            }
        });
    }

    @Test
    public void testFailsOnUnknownObjectReturned() {
        assertThrows(IllegalArgumentException.class, () -> {
            try (SimpleObjectPool<Object> pool = new SimpleObjectPool<>(Object::new)) {
                Object unknown = new Object();
                pool.returnObject(unknown);
            }
        });
    }

    @Test
    public void testFailsOnSameObjectReturnedMultipleTimes() {
        assertThrows(IllegalStateException.class, () -> {
            try (SimpleObjectPool<Object> pool = new SimpleObjectPool<>(Object::new)) {
                Object object = pool.borrowObject();
                pool.returnObject(object);
                pool.returnObject(object);
            }
        });
    }

    @Test
    public void testFailsOnSameObjectCreatedMultipleTimes() {
        assertThrows(IllegalArgumentException.class, () -> {
            Object sameObject = new Object();
            try (SimpleObjectPool<Object> pool = new SimpleObjectPool<>(() -> sameObject)) {
                pool.borrowObject();
                pool.borrowObject();
            }
        });
    }

    @Test
    public void testSizeFailsOnClosed() {
        assertThrows(IllegalStateException.class, () -> {
            SimpleObjectPool<Object> pool = new SimpleObjectPool<>(Object::new);
            pool.close();
            pool.size();
        });
    }

    @Test
    public void testBorrowFailsOnClosed() {
        assertThrows(IllegalStateException.class, () -> {
            SimpleObjectPool<Object> pool = new SimpleObjectPool<>(Object::new);
            pool.close();
            pool.borrowObject();
        });
    }

    @Test
    public void testReturnFailsOnClosed() {
        assertThrows(IllegalStateException.class, () -> {
            SimpleObjectPool<Object> pool = new SimpleObjectPool<>(Object::new);
            pool.close();
            pool.borrowObject();
        });
    }
}
