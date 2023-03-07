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
package com.epam.eco.commons.concurrent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.epam.eco.commons.concurrent.ResourceSemaphores.ResourceSemaphore;

/**
 * @author Andrei_Tytsik
 */
public class ResourceSemaphoresTest {

    @Test
    public void testAllSignalsDone() {
        ResourceSemaphores<String, String> instance = new ResourceSemaphores<>();

        List<String> keys = Arrays.asList("k1", "k2", "k3", "k4", "k5");

        List<ResourceSemaphore<String, String>> semahores =
                createSemaphores(instance, keys, "UPDATE", 5);

        keys.forEach(key -> instance.signalDoneFor(key, "UPDATE"));

        semahores.forEach(semaphore -> {
            semaphore.awaitUnchecked();
            instance.removeSemaphore(semaphore);
            });

        Assertions.assertTrue(instance.isEmpty());
    }

    @Test
    public void testNullSemaphoreIgnored() {
        new ResourceSemaphores<>().removeSemaphore(null);
    }

    private List<ResourceSemaphore<String, String>> createSemaphores(
            ResourceSemaphores<String, String> instance,
            Collection<String> keys,
            String operation,
            int multiplier) {
        List<ResourceSemaphore<String, String>> semaphores =
                new ArrayList<>();
        keys.forEach(
                key -> IntStream.range(0, multiplier).forEach(
                        i -> semaphores.add(instance.createSemaphore(key, operation))
                ));
        Collections.shuffle(semaphores);
        return semaphores;
    }

}
