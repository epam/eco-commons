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
import java.util.ArrayDeque;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.lang3.Validate;

/**
 * @author Andrei_Tytsik
 */
public class SimpleObjectPool<O> implements ObjectPool<O> {

    private final ObjectFactory<O> factory;
    private final Queue<O> pool = new ArrayDeque<>();
    private final Map<O, ObjectState> registry = new IdentityHashMap<>();
    private final Lock lock = new ReentrantLock();
    private boolean closed = false;

    public SimpleObjectPool(ObjectFactory<O> factory) {
        Validate.notNull(factory, "Factory is null");

        this.factory = factory;
    }

    @Override
    public O borrowObject() {
        lock.lock();
        try {
            validateNotClosed();

            O object = pool.poll();
            if (object == null) {
                object = createObject();
            }

            getState(object).markBorrowed();

            return object;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void returnObject(O object) {
        Validate.notNull(object, "Object is null");

        lock.lock();
        try {
            validateNotClosed();

            getState(object).markReturned();

            pool.offer(object);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public int size() {
        lock.lock();
        try {
            validateNotClosed();

            return registry.size();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void close() {
        lock.lock();
        try {
            registry.keySet().forEach(this::closeIfPossible);

            pool.clear();
            registry.clear();

            closed = true;
        } finally {
            lock.unlock();
        }
    }

    private void validateNotClosed() {
        if (closed) {
            throw new IllegalStateException("Object pool is closed");
        }
    }

    private ObjectState getState(O object) {
        ObjectState state = registry.get(object);
        if (state == null) {
            throw new IllegalArgumentException("Unknown object: " + object);
        }
        return state;
    }

    private O createObject() {
        O object = factory.create();
        if (object == null) {
            throw new NullPointerException("Object is null");
        }
        if (registry.containsKey(object)) {
            throw new IllegalArgumentException("Object already exists: " + object);
        }
        registry.put(object, new ObjectState(object));
        return object;
    }

    private void closeIfPossible(O object) {
        if (object instanceof Closeable) {
            try {
                ((Closeable)object).close();
            } catch (Exception ex) {
                // ignore
            }
        }
    }

    private class ObjectState {

        private final O object;
        private boolean borrowed = false;

        public ObjectState(O object) {
            this.object = object;
        }

        public void markBorrowed() {
            if (borrowed == true) {
                throw new IllegalStateException("Object already borrowed: " + object);
            }

            borrowed = true;
        }
        public void markReturned() {
            if (borrowed == false) {
                throw new IllegalStateException("Object already returned: " + object);
            }

            borrowed = false;
        }

    }

}
