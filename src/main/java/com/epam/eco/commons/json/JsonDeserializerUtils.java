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
package com.epam.eco.commons.json;

import org.apache.commons.lang3.Validate;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonMappingException;

/**
 * @author Raman_Babich
 */
public abstract class JsonDeserializerUtils {

    public static void assertNotNullValue(
            Object value,
            String fieldName,
            Class<?> targetType,
            DeserializationContext ctxt) throws JsonMappingException {
        Validate.notBlank(fieldName, "Field name can't be blank");
        Validate.notNull(targetType, "Target type can't be null");
        Validate.notNull(ctxt, "Deserialization context can't be null");

        if (value == null) {
            ctxt.reportInputMismatch(targetType, "Value of the '%s' field can't be null", fieldName);
        }
    }

    public static void assertRequiredField(
            Object value,
            String fieldName,
            Class<?> targetType,
            DeserializationContext ctxt) throws JsonMappingException {
        Validate.notBlank(fieldName, "Field name can't be blank");
        Validate.notNull(targetType, "Target type can't be null");
        Validate.notNull(ctxt, "Deserialization context can't be null");

        if (value == null) {
            ctxt.reportInputMismatch(targetType, "Field '%s' is required", fieldName);
        }
    }

    private JsonDeserializerUtils() {
    }
}
