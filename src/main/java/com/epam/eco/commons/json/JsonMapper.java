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
package com.epam.eco.commons.json;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

/**
 * @author Andrei_Tytsik
 */
public abstract class JsonMapper {

    private static final ObjectMapper MAPPER = new ObjectMapper()
            .registerModule(new ParameterNamesModule())
            .registerModule(new Jdk8Module())
            .registerModule(new JavaTimeModule());

    private JsonMapper() {
    }

    public static byte[] toBytes(Object value) {
        try {
            return MAPPER.writeValueAsBytes(value);
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

    public static String toJson(Object value) {
        try {
            return MAPPER.writeValueAsString(value);
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

    public static String toPrettyJson(String json) {
        try {
            Object jsonObject = MAPPER.readValue(json, Object.class);
            return toPrettyJson(jsonObject);
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

    public static String toPrettyJson(Object value) {
        try {
            return MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(value);
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

    public static Map<String, Object> jsonToMap(String json) {
        try {
            return MAPPER.reader().forType(Map.class).readValue(json);
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

    public static <T> T jsonToObject(String json, Class<T> type) {
        try {
            return MAPPER.reader().forType(type).readValue(json);
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

    public static <T> T bytesToObject(byte[] bytes, Class<T> type) {
        try {
            return MAPPER.reader().forType(type).readValue(bytes);
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> bytesToMap(byte[] bytes) {
        return bytesToObject(bytes, Map.class);
    }

    public static <T> T inputStreamToObject(InputStream inputStream, Class<T> type) {
        try {
            return MAPPER.reader().forType(type).readValue(inputStream);
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> inputStreamToMap(InputStream inputStream) {
        return inputStreamToObject(inputStream, Map.class);
    }

    public static <T> T readerToObject(Reader reader, Class<T> type) {
        try {
            return MAPPER.reader().forType(type).readValue(reader);
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> readerToMap(Reader reader) {
        return readerToObject(reader, Map.class);
    }

    public static <T> T convert(Object object, Class<T> type) {
        return MAPPER.convertValue(object, type);
    }

    public static void writeAsJson(Writer out, Object value) {
        try {
            MAPPER.writeValue(out, value);
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

    public static void writeAsPrettyJson(Writer out, Object value) {
        try {
            MAPPER.writerWithDefaultPrettyPrinter().writeValue(out, value);
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

    public static void writeAsJson(OutputStream out, Object value) {
        try {
            MAPPER.writeValue(out, value);
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

    public static void writeAsPrettyJson(OutputStream out, Object value) {
        try {
            MAPPER.writerWithDefaultPrettyPrinter().writeValue(out, value);
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

}
