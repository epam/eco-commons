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

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections4.map.AbstractMapDecorator;

/**
 * @author Andrei_Tytsik
 */
public class UnmodifiableMapDecorator<K, V> extends AbstractMapDecorator<K, V> {

    public UnmodifiableMapDecorator(Map<? extends K, ? extends V> map) {
        super(map != null ? Collections.unmodifiableMap(new LinkedHashMap<>(map)) : null);
    }

    @Deprecated
    @Override
    public final void clear() {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @Override
    public final V put(final K key, final V value) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @Override
    public final void putAll(final Map<? extends K, ? extends V> mapToCopy) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @Override
    public final V remove(final Object key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final Set<Map.Entry<K, V>> entrySet() {
        return super.entrySet();
    }

    @Override
    public final Set<K> keySet() {
        return super.keySet();
    }

    @Override
    public final Collection<V> values() {
        return super.values();
    }

    public static <K, V> UnmodifiableMapDecorator<K, V> with(Map<? extends K, ? extends V> map) {
        return new UnmodifiableMapDecorator<>(map);
    }

}
