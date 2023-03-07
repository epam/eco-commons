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
package com.epam.eco.commons.collections;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Andrei_Tytsik
 */
@SuppressWarnings("deprecation")
public class UnmodifiableMapDecoratorTest {

    @Test
    public void testFailsOnClear() {
        assertThrows(UnsupportedOperationException.class, () -> testMap().clear());
    }

    @Test
    public void testFailsOnPut() {
        assertThrows(UnsupportedOperationException.class, () -> testMap().put("key1", "value1"));
    }

    @Test
    public void testFailsOnPutAll() {
        Map<String, String> map = new HashMap<>();
        map.put("key1", "value1");

        assertThrows(UnsupportedOperationException.class, () -> testMap().putAll(map));
    }

    @Test
    public void testFailsOnRemove() {
        assertThrows(UnsupportedOperationException.class, () -> testMap().remove("key"));
    }

    @Test
    public void testFailsOnRemoveFromEntrySet() {
        Set<Entry<String, String>> entrySet = testMap().entrySet();
        Entry<String, String> entry = entrySet.iterator().next();
        assertThrows(UnsupportedOperationException.class, () -> entrySet.remove(entry));
    }

    @Test
    public void testFailsOnRemoveFromEntrySetIterator() {
        Iterator<Entry<String, String>> iterator = testMap().entrySet().iterator();
        iterator.next();
        assertThrows(UnsupportedOperationException.class, iterator::remove);
    }

    @Test
    public void testFailsOnRemoveFromKeySet() {
        assertThrows(UnsupportedOperationException.class, () -> testMap().keySet().remove("key"));
    }

    @Test
    public void testFailsOnRemoveFromKeySetIterator() {
        Iterator<String> iterator = testMap().keySet().iterator();
        iterator.next();
        assertThrows(UnsupportedOperationException.class, iterator::remove);
    }

    @Test
    public void testFailsOnRemoveFromValues() {
        assertThrows(UnsupportedOperationException.class, () -> testMap().values().remove("value"));
    }

    @Test
    public void testFailsOnRemoveFromValuesIterator() {
        Iterator<String> iterator = testMap().values().iterator();
        iterator.next();
        assertThrows(UnsupportedOperationException.class, iterator::remove);
    }

    private static UnmodifiableMapDecorator<String, String> testMap() {
        Map<String, String> decorated = new HashMap<>();
        decorated.put("key", "value");
        return UnmodifiableMapDecorator.with(decorated);
    }

}
