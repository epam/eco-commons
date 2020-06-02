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

import java.io.IOException;
import java.time.LocalDateTime;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import com.epam.eco.commons.utils.TestEntity;
import com.epam.eco.commons.utils.TestEntityFields;
import com.epam.eco.commons.utils.TestUtils;

/**
 * @author Raman_Babich
 */
public class JsonDeserializerUtilsTest {

    private static ObjectMapper objectMapper;

    @BeforeClass
    public static void init() {
        objectMapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .registerModule(new SimpleModule().addDeserializer(TestEntity.class, new TestEntityDeserializer()));
    }

    @Test
    public void testAssertions() throws IOException {
        ObjectNode node = TestUtils.getTestEntityObjectNode(objectMapper);
        TestEntity testEntity = objectMapper.readValue(node.toString(), TestEntity.class);
        Assert.assertNotNull(testEntity);
    }

    @Test(expected = IOException.class)
    public void testAssertNotNullValue() throws IOException {
        ObjectNode node = TestUtils.getTestEntityObjectNode(objectMapper);
        node.remove(TestEntityFields.NAME);
        TestEntity testEntity = objectMapper.readValue(node.toString(), TestEntity.class);
        Assert.assertNotNull(testEntity);
    }

    @Test(expected = IOException.class)
    public void testAssertRequiredField() throws IOException {
        ObjectNode node = TestUtils.getTestEntityObjectNode(objectMapper);
        node.remove(TestEntityFields.ID);
        TestEntity testEntity = objectMapper.readValue(node.toString(), TestEntity.class);
        Assert.assertNotNull(testEntity);
    }

    public static class TestEntityDeserializer extends StdDeserializer<TestEntity> {

        private static final long serialVersionUID = 8131556350138978886L;

        public TestEntityDeserializer() {
            super(TestEntity.class);
        }

        @Override
        public TestEntity deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException {
            if (jsonParser.getCurrentToken() == JsonToken.START_OBJECT) {
                jsonParser.nextToken();
            }
            String fieldName = jsonParser.getCurrentName();

            Long id = null;
            String name = null;
            Short age = null;
            Integer rating = null;
            Float initRandom = null;
            Double magic = null;
            LocalDateTime createdAt = null;
            LocalDateTime lastModifiedAt = null;
            Boolean deleted = null;
            while (fieldName != null) {
                if (TestEntityFields.ID.equals(fieldName)) {
                    jsonParser.nextToken();
                    id = _parseLongPrimitive(jsonParser, ctxt);
                } else if (TestEntityFields.NAME.equals(fieldName)) {
                    jsonParser.nextToken();
                    name = _parseString(jsonParser, ctxt);
                } else if (TestEntityFields.AGE.equals(fieldName)) {
                    jsonParser.nextToken();
                    age = _parseShortPrimitive(jsonParser, ctxt);
                } else if (TestEntityFields.RATING.equals(fieldName)) {
                    jsonParser.nextToken();
                    rating = _parseIntPrimitive(jsonParser, ctxt);
                } else if (TestEntityFields.INIT_RANDOM.equals(fieldName)) {
                    jsonParser.nextToken();
                    initRandom = _parseFloatPrimitive(jsonParser, ctxt);
                } else if (TestEntityFields.MAGIC.equals(fieldName)) {
                    jsonParser.nextToken();
                    magic = _parseDoublePrimitive(jsonParser, ctxt);
                } else if (TestEntityFields.CREATED_AT.equals(fieldName)) {
                    jsonParser.nextToken();
                    createdAt = jsonParser.readValueAs(LocalDateTime.class);
                } else if (TestEntityFields.LAST_MODIFIED_AT.equals(fieldName)) {
                    jsonParser.nextToken();
                    lastModifiedAt = jsonParser.readValueAs(LocalDateTime.class);
                } else if (TestEntityFields.DELETED.equals(fieldName)) {
                    jsonParser.nextToken();
                    deleted = _parseBooleanPrimitive(jsonParser, ctxt);
                } else {
                    handleUnknownProperty(jsonParser, ctxt, _valueClass, fieldName);
                }
                fieldName = jsonParser.nextFieldName();
            }

            JsonDeserializerUtils.assertRequiredField(id, TestEntityFields.ID, _valueClass, ctxt);
            JsonDeserializerUtils.assertNotNullValue(name, TestEntityFields.NAME, _valueClass, ctxt);
            JsonDeserializerUtils.assertRequiredField(age, TestEntityFields.AGE, _valueClass, ctxt);
            JsonDeserializerUtils.assertRequiredField(rating, TestEntityFields.RATING, _valueClass, ctxt);
            JsonDeserializerUtils.assertRequiredField(initRandom, TestEntityFields.INIT_RANDOM, _valueClass, ctxt);
            JsonDeserializerUtils.assertRequiredField(magic, TestEntityFields.MAGIC, _valueClass, ctxt);
            JsonDeserializerUtils.assertRequiredField(deleted, TestEntityFields.DELETED, _valueClass, ctxt);

            return new TestEntity(id, name, age, rating, initRandom, magic, createdAt, lastModifiedAt, deleted);
        }
    }

}
