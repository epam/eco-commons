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
import java.util.function.Function;

/**
 * @author Andrei_Tytsik
 */
public interface ObjectPool<O> extends Closeable {

    O borrowObject();

    void returnObject(O object);

    int size();

    @Override
    void close();

    default <R> R doWithObject(Function<O, R> function) {
        O object = borrowObject();
        try {
            return function.apply(object);
        } finally {
            returnObject(object);
        }
    }

}
