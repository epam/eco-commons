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
package com.epam.eco.commons.collections;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.Test;

/**
 * @author Andrei_Tytsik
 */
@SuppressWarnings("deprecation")
public class UnmodifiableMapDecoratorTest {

    @Test(expected=UnsupportedOperationException.class)
    public void testFailsOnClear() throws Exception {
        testMap().clear();
    }

    @Test(expected=UnsupportedOperationException.class)
    public void testFailsOnPut() throws Exception {
        testMap().put("key1", "value1");
    }

    @Test(expected=UnsupportedOperationException.class)
    public void testFailsOnPutAll() throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("key1", "value1");

        testMap().putAll(map);
    }

    @Test(expected=UnsupportedOperationException.class)
    public void testFailsOnRemove() throws Exception {
        testMap().remove("key");
    }

    @Test(expected=UnsupportedOperationException.class)
    public void testFailsOnRemoveFromEntrySet() throws Exception {
        Set<Entry<String, String>> entrySet = testMap().entrySet();
        Entry<String, String> entry = entrySet.iterator().next();
        entrySet.remove(entry);
    }

    @Test(expected=UnsupportedOperationException.class)
    public void testFailsOnRemoveFromEntrySetIterator() throws Exception {
        Iterator<Entry<String, String>> iterator = testMap().entrySet().iterator();
        iterator.next();
        iterator.remove();
    }

    @Test(expected=UnsupportedOperationException.class)
    public void testFailsOnRemoveFromKeySet() throws Exception {
        testMap().keySet().remove("key");
    }

    @Test(expected=UnsupportedOperationException.class)
    public void testFailsOnRemoveFromKeySetIterator() throws Exception {
        Iterator<String> iterator = testMap().keySet().iterator();
        iterator.next();
        iterator.remove();
    }

    @Test(expected=UnsupportedOperationException.class)
    public void testFailsOnRemoveFromValues() throws Exception {
        testMap().values().remove("value");
    }

    @Test(expected=UnsupportedOperationException.class)
    public void testFailsOnRemoveFromValuesIterator() throws Exception {
        Iterator<String> iterator = testMap().values().iterator();
        iterator.next();
        iterator.remove();
    }

    private static UnmodifiableMapDecorator<String, String> testMap() {
        Map<String, String> decorated = new HashMap<>();
        decorated.put("key", "value");
        return UnmodifiableMapDecorator.with(decorated);
    }

}
