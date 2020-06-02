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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.epam.eco.commons.utils.TestEntity;
import com.epam.eco.commons.utils.TestUtils;

/**
 * @author Andrei_Tytsik
 */
public class JsonMapperTest {

    @Test
    public void testToBytesAndBytesToObject() {
        TestEntity expected = TestUtils.getTestEntity();
        byte[] bytes = JsonMapper.toBytes(expected);
        TestEntity result = JsonMapper.bytesToObject(bytes, TestEntity.class);
        Assert.assertEquals(expected, result);
    }

    @Test
    public void testToJsonAndJsonToObject() {
        TestEntity expected = TestUtils.getTestEntity();
        String json = JsonMapper.toJson(expected);
        TestEntity result = JsonMapper.jsonToObject(json, TestEntity.class);
        Assert.assertEquals(expected, result);
    }

    @Test
    public void testToPrettyJson1() {
        TestEntity expected = TestUtils.getTestEntity();
        String json = JsonMapper.toPrettyJson(expected);
        TestEntity result = JsonMapper.jsonToObject(json, TestEntity.class);
        Assert.assertEquals(expected, result);
    }

    @Test
    public void testToPrettyJson2() {
        TestEntity expected = TestUtils.getTestEntity();
        String json = JsonMapper.toJson(expected);
        String prettyJson = JsonMapper.toPrettyJson(json);
        TestEntity result = JsonMapper.jsonToObject(prettyJson, TestEntity.class);
        Assert.assertEquals(expected, result);
    }

    @Test
    public void testInputStreamToObject() {
        InputStream inputStream = new ByteArrayInputStream(TestUtils.getTestEntityJson().getBytes());
        TestEntity result = JsonMapper.inputStreamToObject(inputStream, TestEntity.class);
        Assert.assertEquals(TestUtils.getTestEntity(), result);
    }

    @Test
    public void testReaderToObject() {
        Reader reader = new StringReader(TestUtils.getTestEntityJson());
        TestEntity result = JsonMapper.readerToObject(reader, TestEntity.class);
        Assert.assertEquals(TestUtils.getTestEntity(), result);
    }

    @Test
    public void testJsonToMap() {
        String json = TestUtils.getTestEntityJson();
        Map<String, Object> map = JsonMapper.jsonToMap(json);
        assertTestEntityMapRepresentationIsValid(map);
    }

    @Test
    public void testBytesToMap() {
        Map<String, Object> map = JsonMapper.bytesToMap(TestUtils.getTestEntityJson().getBytes());
        assertTestEntityMapRepresentationIsValid(map);
    }

    @Test
    public void testInputStreamToMap() {
        InputStream inputStream = new ByteArrayInputStream(TestUtils.getTestEntityJson().getBytes());
        Map<String, Object> map = JsonMapper.inputStreamToMap(inputStream);
        assertTestEntityMapRepresentationIsValid(map);
    }

    @Test
    public void testReaderToMap() {
        Reader reader = new StringReader(TestUtils.getTestEntityJson());
        Map<String, Object> map = JsonMapper.readerToMap(reader);
        assertTestEntityMapRepresentationIsValid(map);
    }

    private void assertTestEntityMapRepresentationIsValid(Map<String, Object> map) {
        Assert.assertEquals(9, map.size());
        Assert.assertTrue(map.containsKey("id"));
        Assert.assertTrue(map.containsKey("name"));
        Assert.assertTrue(map.containsKey("age"));
        Assert.assertTrue(map.containsKey("rating"));
        Assert.assertTrue(map.containsKey("initRandom"));
        Assert.assertTrue(map.containsKey("magic"));
        Assert.assertTrue(map.containsKey("createdAt"));
        Assert.assertTrue(map.containsKey("lastModifiedAt"));
        Assert.assertTrue(map.containsKey("deleted"));
    }

    @Test
    public void testConvert1() {
        TestEntity expected = TestUtils.getTestEntity();
        String json = JsonMapper.toJson(expected);
        Map<String, Object> map = JsonMapper.jsonToMap(json);
        TestEntity result = JsonMapper.convert(map, TestEntity.class);
        Assert.assertEquals(expected, result);
    }

    @Test
    public void testConvert2() {
        TestEntity expected = TestUtils.getTestEntity();
        TestEntity result = JsonMapper.convert(expected, TestEntity.class);
        Assert.assertEquals(expected, result);
    }

    @Test
    public void testWriteAsJson1() {
        TestEntity expected = TestUtils.getTestEntity();
        StringWriter stringWriter = new StringWriter();
        JsonMapper.writeAsJson(stringWriter, expected);
        String json = stringWriter.toString();
        TestEntity result = JsonMapper.jsonToObject(json, TestEntity.class);
        Assert.assertEquals(expected, result);
    }

    @Test
    public void testWriteAsJson2() {
        TestEntity expected = TestUtils.getTestEntity();
        OutputStream out = new ByteArrayOutputStream();
        JsonMapper.writeAsJson(out, expected);
        String json = out.toString();
        TestEntity result = JsonMapper.jsonToObject(json, TestEntity.class);
        Assert.assertEquals(expected, result);
    }

    @Test
    public void testWriteAsPrettyJson1() {
        TestEntity expected = TestUtils.getTestEntity();
        StringWriter stringWriter = new StringWriter();
        JsonMapper.writeAsPrettyJson(stringWriter, expected);
        String json = stringWriter.toString();
        TestEntity result = JsonMapper.jsonToObject(json, TestEntity.class);
        Assert.assertEquals(expected, result);
    }

    @Test
    public void testWriteAsPrettyJson2() {
        TestEntity expected = TestUtils.getTestEntity();
        OutputStream out = new ByteArrayOutputStream();
        JsonMapper.writeAsPrettyJson(out, expected);
        String json = out.toString();
        TestEntity result = JsonMapper.jsonToObject(json, TestEntity.class);
        Assert.assertEquals(expected, result);
    }

}
