/*
 * Copyright 2019 EPAM Systems
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
package com.epam.eco.commons.concurrent;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

/**
 * @author Andrei_Tytsik
 */
public class ResourceSemaphores<K, O> {

    private static final long DEFAULT_WAIT_TIMEOUT_MS = 5000;

    private final Map<K, Set<ResourceSemaphore<K, O>>> semaphores = new HashMap<>();
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    public ResourceSemaphore<K, O> createSemaphore(K key, O operation) {
        lock.writeLock().lock();
        try {
            Set<ResourceSemaphore<K, O>> resourceSemaphores = semaphores.get(key);
            if (resourceSemaphores == null) {
                resourceSemaphores = Collections.newSetFromMap(new IdentityHashMap<>());
                semaphores.put(key, resourceSemaphores);
            }

            ResourceSemaphore<K, O> semaphore = new ResourceSemaphore<>(this, key, operation);
            resourceSemaphores.add(semaphore);

            return semaphore;
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void removeSemaphore(ResourceSemaphore<K, O> semaphore) {
        if (semaphore == null) {
            return;
        }

        lock.writeLock().lock();
        try {
            Set<ResourceSemaphore<K, O>> resourceSemaphores = semaphores.get(semaphore.key);
            if (resourceSemaphores == null) {
                return;
            }

            resourceSemaphores.remove(semaphore);

            if (resourceSemaphores.isEmpty()) {
                semaphores.remove(semaphore.key);
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void signalDoneFor(K key, O operation) {
        getSemaphores(key, operation).forEach(ResourceSemaphore::signalDone);
    }

    private Set<ResourceSemaphore<K, O>> getSemaphores(K key, O operation) {
        lock.readLock().lock();
        try {
            Set<ResourceSemaphore<K, O>> resourceSemaphores = semaphores.get(key);
            if (resourceSemaphores == null) {
                return Collections.emptySet();
            }

            return resourceSemaphores.stream().
                    filter(s -> s.operation == operation).
                    collect(Collectors.toSet());
        } finally {
            lock.readLock().unlock();
        }
    }

    /**
     * for testing
     */
    boolean isEmpty() {
        lock.readLock().lock();
        try {
            return semaphores.isEmpty();
        } finally {
            lock.readLock().unlock();
        }
    }

    public static final class ResourceSemaphore<K, O> implements AutoCloseable {

        private final K key;
        private final O operation;
        private final ResourceSemaphores<K, O> container;
        private final CountDownLatch latch = new CountDownLatch(1);

        private ResourceSemaphore(ResourceSemaphores<K, O> container, K key, O operation) {
            this.container = container;
            this.key = key;
            this.operation = operation;
        }

        public boolean awaitUnchecked() {
            try {
                return await();
            } catch (InterruptedException ie) {
                throw new RuntimeException(ie);
            }
        }

        public boolean await() throws InterruptedException {
            return await(DEFAULT_WAIT_TIMEOUT_MS, TimeUnit.MILLISECONDS);
        }

        public boolean awaitUnchecked(long timeout, TimeUnit unit) {
            try {
                return await(timeout, unit);
            } catch (InterruptedException ie) {
                throw new RuntimeException(ie);
            }
        }

        public boolean await(long timeout, TimeUnit unit) throws InterruptedException {
            return latch.await(timeout, unit);
        }

        private void signalDone() {
            latch.countDown();
        }

        @Override
        public void close() {
            container.removeSemaphore(this);
        }

    }

}
