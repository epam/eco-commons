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
package com.epam.eco.commons.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * @author Raman_Babich
 */
public abstract class TestUtils {

    private static final LocalDateTime DEFAULT_TIME = Instant.ofEpochMilli(0)
            .atZone(ZoneId.of("UTC"))
            .toLocalDateTime();
    private static final String TEST_ENTITY_JSON = "{\"id\":1,\"name\":\"name\",\"createdAt\":[1970,1,1,0,0]," +
            "\"lastModifiedAt\":[1970,1,1,0,0,42],\"deleted\":false}";

    private TestUtils() {
    }

    public static TestEntity getTestEntity() {
        TestEntity testEntity = new TestEntity();
        testEntity.setId(1);
        testEntity.setName("name");
        testEntity.setCreatedAt(DEFAULT_TIME);
        testEntity.setLastModifiedAt(DEFAULT_TIME.plusSeconds(42));
        testEntity.setDeleted(false);

        return testEntity;
    }

    public static String getTestEntityJson() {
        return TEST_ENTITY_JSON;
    }
}
