/*
 * Copyright 2020 EPAM Systems
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.epam.eco.commons.pool;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Andrei_Tytsik
 */
public class SimpleObjectPoolTest {

    @Test
    public void testObjectsBorrowedAndReturned() throws Exception {
        try (SimpleObjectPool<Object> pool = new SimpleObjectPool<>(() -> new Object())) {
            Object object1 = pool.borrowObject();

            Assert.assertNotNull(object1);

            Object object2 = pool.borrowObject();
            Assert.assertNotNull(object2);

            Assert.assertNotEquals(object1, object2);

            pool.returnObject(object1);
            pool.returnObject(object2);

            Object object3 = pool.borrowObject();
            Assert.assertNotNull(object3);
            Assert.assertEquals(object1, object3);

            Object object4 = pool.borrowObject();
            Assert.assertNotNull(object4);
            Assert.assertEquals(object2, object4);
        }
    }

    @Test
    public void testSizeGrows() throws Exception {
        try (SimpleObjectPool<Object> pool = new SimpleObjectPool<>(() -> new Object())) {
            Assert.assertEquals(0,  pool.size());

            Object object1 = pool.borrowObject();
            Assert.assertEquals(1,  pool.size());

            Object object2 = pool.borrowObject();
            Assert.assertEquals(2,  pool.size());

            Object object3 = pool.borrowObject();
            Assert.assertEquals(3,  pool.size());

            pool.returnObject(object1);
            pool.returnObject(object2);
            pool.returnObject(object3);

            Assert.assertEquals(3,  pool.size());
        }
    }

    @Test
    public void testObjectsBorrowedAndReturnedWithDoWith() throws Exception {
        try (SimpleObjectPool<Object> pool = new SimpleObjectPool<>(() -> new Object())) {
            Object object1 = pool.doWithObject(object -> object);
            Assert.assertNotNull(object1);

            Object object2 = pool.doWithObject(object -> object);
            Assert.assertNotNull(object2);
            Assert.assertEquals(object1, object2);
        }
    }

    @Test
    public void testClosesCloseables() throws Exception {
        AtomicBoolean closed = new AtomicBoolean(false);

        @SuppressWarnings("resource")
        Closeable closeable = new Closeable() {
            @Override
            public void close() throws IOException {
                closed.set(true);
            }
        };

        try (SimpleObjectPool<Object> pool = new SimpleObjectPool<>(() -> closeable)) {
            pool.borrowObject();
        }
        Assert.assertTrue(closed.get());
    }


    @Test(expected=NullPointerException.class)
    public void testFailsOnIllegalArguments1() throws Exception {
        try (SimpleObjectPool<Object> pool = new SimpleObjectPool<>(null)) {
        }
    }

    @Test(expected=NullPointerException.class)
    public void testFailsOnIllegalArguments2() throws Exception {
        try (SimpleObjectPool<Object> pool = new SimpleObjectPool<>(() -> new Object())) {
            pool.returnObject(null);
        }
    }

    @Test(expected=IllegalArgumentException.class)
    public void testFailsOnUnknownObjectReturned() throws Exception {
        try (SimpleObjectPool<Object> pool = new SimpleObjectPool<>(() -> new Object())) {
            Object unknown = new Object();
            pool.returnObject(unknown);
        }
    }

    @Test(expected=IllegalStateException.class)
    public void testFailsOnSameObjectReturnedMultipleTimes() throws Exception {
        try (SimpleObjectPool<Object> pool = new SimpleObjectPool<>(() -> new Object())) {
            Object object = pool.borrowObject();
            pool.returnObject(object);
            pool.returnObject(object);
        }
    }

    @Test(expected=IllegalArgumentException.class)
    public void testFailsOnSameObjectCreatedMultipleTimes() throws Exception {
        Object sameObject = new Object();
        try (SimpleObjectPool<Object> pool = new SimpleObjectPool<>(() -> sameObject)) {
            pool.borrowObject();
            pool.borrowObject();
        }
    }

    @Test(expected=IllegalStateException.class)
    public void testSizeFailsOnClosed() throws Exception {
        SimpleObjectPool<Object> pool = new SimpleObjectPool<>(() -> new Object());
        pool.close();
        pool.size();
    }

    @Test(expected=IllegalStateException.class)
    public void testBorrowFailsOnClosed() throws Exception {
        SimpleObjectPool<Object> pool = new SimpleObjectPool<>(() -> new Object());
        pool.close();
        pool.borrowObject();
    }

    @Test(expected=IllegalStateException.class)
    public void testReturnFailsOnClosed() throws Exception {
        SimpleObjectPool<Object> pool = new SimpleObjectPool<>(() -> new Object());
        pool.close();
        pool.borrowObject();
    }

}
