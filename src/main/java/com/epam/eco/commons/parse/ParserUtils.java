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
package com.epam.eco.commons.parse;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.lang3.Validate;

/**
 * @author Andrei_Tytsik
 */
public abstract class ParserUtils {

    public static final String DATE_FORMAT_ISO = "yyyy-MM-dd'T'HH:mm:ss";

    private ParserUtils() {
    }

    public static Boolean parseBoolean(Object value) throws ParseException {
        if (value == null) {
            return null;
        }

        if (value instanceof Boolean) {
            return (Boolean) value;
        } else if (value instanceof String) {
            return Boolean.valueOf((String) value);
        } else {
            throw new ParseException(
                    String.format("Can't parse '%s' as %s", value, Boolean.class.getName()));
        }
    }

    public static Byte parseByte(Object value) throws ParseException {
        if (value == null) {
            return null;
        }

        if (value instanceof Byte) {
            return (Byte) value;
        } else if (value instanceof String) {
            try {
                return Byte.valueOf((String) value);
            } catch (NumberFormatException nfe) {
                throw new ParseException(
                        String.format("Failed to parse '%s' as %s", value, Byte.class.getName()),
                        nfe);
            }
        } else if (Number.class.isAssignableFrom(value.getClass())) {
            return ((Number) value).byteValue();
        } else {
            throw new ParseException(
                    String.format("Can't parse '%s' as %s", value, Byte.class.getName()));
        }
    }

    public static Short parseShort(Object value) throws ParseException {
        if (value == null) {
            return null;
        }

        if (value instanceof Short) {
            return (Short) value;
        } else if (value instanceof String) {
            try {
                return Short.valueOf((String) value);
            } catch (NumberFormatException nfe) {
                throw new ParseException(
                        String.format("Failed to parse '%s' as %s", value, Short.class.getName()),
                        nfe);
            }
        } else if (Number.class.isAssignableFrom(value.getClass())) {
            return ((Number) value).shortValue();
        } else {
            throw new ParseException(
                    String.format("Can't parse '%s' as %s", value, Short.class.getName()));
        }
    }

    public static Integer parseInt(Object value) throws ParseException {
        if (value == null) {
            return null;
        }

        if (value instanceof Integer) {
            return (Integer) value;
        } else if (value instanceof String) {
            try {
                return Integer.valueOf((String) value);
            } catch (NumberFormatException nfe) {
                throw new ParseException(
                        String.format("Failed to parse '%s' as %s", value, Integer.class.getName()),
                        nfe);
            }
        } else if (Number.class.isAssignableFrom(value.getClass())) {
            return ((Number) value).intValue();
        } else {
            throw new ParseException(
                    String.format("Can't parse '%s' as %s", value, Integer.class.getName()));
        }
    }

    public static Long parseLong(Object value) throws ParseException {
        if (value == null) {
            return null;
        }

        if (value instanceof Long) {
            return (Long) value;
        } else if (value instanceof String) {
            try {
                return Long.valueOf((String) value);
            } catch (NumberFormatException nfe) {
                throw new ParseException(
                        String.format("Failed to parse '%s' as %s", value, Long.class.getName()),
                        nfe);
            }
        } else if (Number.class.isAssignableFrom(value.getClass())) {
            return ((Number) value).longValue();
        } else {
            throw new ParseException(
                    String.format("Can't parse '%s' as %s", value, Long.class.getName()));
        }
    }

    public static Float parseFloat(Object value) throws ParseException {
        if (value == null) {
            return null;
        }

        if (value instanceof Float) {
            return (Float) value;
        } else if (value instanceof String) {
            try {
                return Float.valueOf((String) value);
            } catch (NumberFormatException nfe) {
                throw new ParseException(
                        String.format("Failed to parse '%s' as %s", value, Float.class.getName()),
                        nfe);
            }
        } else if (Number.class.isAssignableFrom(value.getClass())) {
            return ((Number) value).floatValue();
        } else {
            throw new ParseException(
                    String.format("Can't parse '%s' as %s", value, Float.class.getName()));
        }
    }

    public static Double parseDouble(Object value) throws ParseException {
        if (value == null) {
            return null;
        }

        if (value instanceof Double) {
            return (Double) value;
        } else if (value instanceof String) {
            try {
                return Double.valueOf((String) value);
            } catch (NumberFormatException nfe) {
                throw new ParseException(
                        String.format("Failed to parse '%s' as %s", value, Double.class.getName()),
                        nfe);
            }
        } else if (Number.class.isAssignableFrom(value.getClass())) {
            return ((Number) value).doubleValue();
        } else {
            throw new ParseException(
                    String.format("Can't parse '%s' as %s", value, Double.class.getName()));
        }
    }

    public static Date parseDate(Object value) throws ParseException {
        return parseDate(value, null);
    }

    public static Date parseDate(Object value, String pattern) throws ParseException {
        if (value == null) {
            return null;
        }

        if (value instanceof Date) {
            return (Date) value;
        } else if (value instanceof String) {
            try {
                pattern = pattern != null ? pattern : DATE_FORMAT_ISO;

                SimpleDateFormat format = new SimpleDateFormat(pattern);
                format.setTimeZone(TimeZone.getTimeZone("UTC"));
                return format.parse((String) value);
            } catch (java.text.ParseException | IllegalArgumentException ex) {
                throw new ParseException(
                        String.format("Failed to parse '%s' as %s", value, Date.class.getName()),
                        ex);
            }
        } else {
            throw new ParseException(
                    String.format("Can't parse '%s' as %s", value, Date.class.getName()));
        }
    }

    public static String parseString(Object value) throws ParseException {
        if (value == null) {
            return null;
        }

        if (value instanceof String) {
            return (String) value;
        } else {
            throw new ParseException(
                    String.format("Can't parse '%s' as %s", value, String.class.getName()));
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static <T extends Enum> T parseEnum(Object value, Class<T> aClass) throws ParseException {
        Validate.notNull(aClass, "Target class is null");

        if (value == null) {
            return null;
        }

        if (aClass.isInstance(value)) {
            return (T) value;
        } else if (value instanceof String) {
            try {
                return (T) Enum.valueOf(aClass, ((String) value).trim().toUpperCase());
            } catch (IllegalArgumentException ex) {
                throw new ParseException(
                        String.format("Failed to parse '%s' as %s", value, aClass.getName()),
                        ex);
            }
        } else {
            throw new ParseException(
                    String.format("Can't parse '%s' as %s", value, aClass.getName()));
        }
    }

}
