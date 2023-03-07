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
package com.epam.eco.commons.parse;

import java.time.DayOfWeek;
import java.util.Date;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Andrei_Tytsik
 */
public class ParserUtilsTest {

    @Test
    public void testBooleanParsedFromBoolean() {
        Boolean parsed = ParserUtils.parseBoolean(Boolean.TRUE);

        Assertions.assertEquals(Boolean.TRUE, parsed);
    }

    @Test
    public void testBooleanParsedFromString1() {
        Boolean parsed = ParserUtils.parseBoolean("true");

        Assertions.assertEquals(Boolean.TRUE, parsed);
    }

    @Test
    public void testBooleanParsedFromString2() {
        Boolean parsed = ParserUtils.parseBoolean("yes");

        Assertions.assertEquals(Boolean.FALSE, parsed);
    }

    @Test
    public void testBooleanParsingFailsOnIllegalInput() {
        assertThrows(ParseException.class, () -> ParserUtils.parseBoolean(new Object()));
    }

    @Test
    public void testByteIsParsedFromByte() {
        Byte parsed = ParserUtils.parseByte(Byte.MAX_VALUE);

        Assertions.assertEquals(Byte.valueOf(Byte.MAX_VALUE), parsed);
    }

    @Test
    public void testByteIsParsedFromNumber() {
        Byte parsed = ParserUtils.parseByte((long)Byte.MAX_VALUE);

        Assertions.assertEquals(Byte.valueOf(Byte.MAX_VALUE), parsed);
    }

    @Test
    public void testByteIsParsedFromString() {
        Byte parsed = ParserUtils.parseByte("" + Byte.MAX_VALUE);

        Assertions.assertEquals(Byte.valueOf(Byte.MAX_VALUE), parsed);
    }

    @Test
    public void testByteParsingFailsOnIllegalInput1() {
        assertThrows(ParseException.class, () -> ParserUtils.parseByte(new Object()));
    }

    @Test
    public void testByteParsingFailsOnIllegalInput2() {
        assertThrows(ParseException.class, () -> ParserUtils.parseByte(""));
    }

    @Test
    public void testShortIsParsedFromShort() {
        Short parsed = ParserUtils.parseShort(Short.MAX_VALUE);

        Assertions.assertEquals(Short.valueOf(Short.MAX_VALUE), parsed);
    }

    @Test
    public void testShortIsParsedFromNumber() {
        Short parsed = ParserUtils.parseShort(Byte.MAX_VALUE);

        Assertions.assertEquals(Short.valueOf(Byte.MAX_VALUE), parsed);
    }

    @Test
    public void testShortIsParsedFromString() {
        Short parsed = ParserUtils.parseShort("" + Short.MAX_VALUE);

        Assertions.assertEquals(Short.valueOf(Short.MAX_VALUE), parsed);
    }

    @Test
    public void testShortParsingFailsOnIllegalInput1() {
        assertThrows(ParseException.class, () -> ParserUtils.parseShort(new Object()));
    }

    @Test
    public void testShortParsingFailsOnIllegalInput2() {
        assertThrows(ParseException.class, () -> ParserUtils.parseShort(""));
    }

    @Test
    public void testIntegerIsParsedFromInteger() {
        Integer parsed = ParserUtils.parseInt(Integer.MAX_VALUE);

        Assertions.assertEquals(Integer.valueOf(Integer.MAX_VALUE), parsed);
    }

    @Test
    public void testIntegerIsParsedFromNumber() {
        Integer parsed = ParserUtils.parseInt(Short.MAX_VALUE);

        Assertions.assertEquals(Integer.valueOf(Short.MAX_VALUE), parsed);
    }

    @Test
    public void testIntegerIsParsedFromString() {
        Integer parsed = ParserUtils.parseInt("" + Integer.MAX_VALUE);

        Assertions.assertEquals(Integer.valueOf(Integer.MAX_VALUE), parsed);
    }

    @Test
    public void testIntegerParsingFailsOnIllegalInput1() {
        assertThrows(ParseException.class, () -> ParserUtils.parseInt(new Object()));
    }

    @Test
    public void testIntegerParsingFailsOnIllegalInput2() {
        assertThrows(ParseException.class, () -> ParserUtils.parseInt(""));
    }

    @Test
    public void testLongIsParsedFromLong() {
        Long parsed = ParserUtils.parseLong(Long.MAX_VALUE);

        Assertions.assertEquals(Long.valueOf(Long.MAX_VALUE), parsed);
    }

    @Test
    public void testLongIsParsedFromNumber() {
        Long parsed = ParserUtils.parseLong(Integer.MAX_VALUE);

        Assertions.assertEquals(Long.valueOf(Integer.MAX_VALUE), parsed);
    }

    @Test
    public void testLongIsParsedFromString() {
        Long parsed = ParserUtils.parseLong("" + Long.MAX_VALUE);

        Assertions.assertEquals(Long.valueOf(Long.MAX_VALUE), parsed);
    }

    @Test
    public void testLongParsingFailsOnIllegalInput1() {
        assertThrows(ParseException.class, () -> ParserUtils.parseLong(new Object()));
    }

    @Test
    public void testLongParsingFailsOnIllegalInput2() {
        assertThrows(ParseException.class, () -> ParserUtils.parseLong(""));
    }

    @Test
    public void testFloatIsParsedFromFloat() {
        Float parsed = ParserUtils.parseFloat(Float.MAX_VALUE);

        Assertions.assertEquals(Float.valueOf(Float.MAX_VALUE), parsed);
    }

    @Test
    public void testFloatIsParsedFromNumber() {
        Float parsed = ParserUtils.parseFloat(Integer.MAX_VALUE);

        Assertions.assertEquals(Float.valueOf(Integer.MAX_VALUE), parsed);
    }

    @Test
    public void testFloatIsParsedFromString() {
        Float parsed = ParserUtils.parseFloat("" + Float.MAX_VALUE);

        Assertions.assertEquals(Float.valueOf(Float.MAX_VALUE), parsed);
    }

    @Test
    public void testFloatParsingFailsOnIllegalInput1() {
        assertThrows(ParseException.class, () -> ParserUtils.parseFloat(new Object()));
    }

    @Test
    public void testFloatParsingFailsOnIllegalInput2() {
        assertThrows(ParseException.class, () -> ParserUtils.parseFloat(""));
    }

    @Test
    public void testDoubleIsParsedFromDouble() {
        Double parsed = ParserUtils.parseDouble(Double.MAX_VALUE);

        Assertions.assertEquals(Double.valueOf(Double.MAX_VALUE), parsed);
    }

    @Test
    public void testDoubleIsParsedFromNumber() {
        Double parsed = ParserUtils.parseDouble(Integer.MAX_VALUE);

        Assertions.assertEquals(Double.valueOf(Integer.MAX_VALUE), parsed);
    }

    @Test
    public void testDoubleIsParsedFromString() {
        Double parsed = ParserUtils.parseDouble("" + Double.MAX_VALUE);

        Assertions.assertEquals(Double.valueOf(Double.MAX_VALUE), parsed);
    }

    @Test
    public void testDoubleParsingFailsOnIllegalInput1() {
        assertThrows(ParseException.class, () -> ParserUtils.parseDouble(new Object()));
    }

    @Test
    public void testDoubleParsingFailsOnIllegalInput2() {
        assertThrows(ParseException.class, () -> ParserUtils.parseDouble(""));
    }

    @Test
    public void testDateIsParsedFromDate() {
        Date date = new Date(System.currentTimeMillis());

        Date parsed = ParserUtils.parseDate(date);

        Assertions.assertEquals(date, parsed);
    }

    @Test
    public void testDateIsParsedFromString() {
        Date date = new Date(0);

        Date parsed = ParserUtils.parseDate("1970-01-01T00:00:00");

        Assertions.assertEquals(date, parsed);
    }

    @SuppressWarnings("unused")
    @Test
    public void testDateIsParsedFromBadStringWithBadPattern() {
        assertThrows(ParseException.class, () -> {
            Date date = new Date(System.currentTimeMillis());
            Date parsed = ParserUtils.parseDate("abcd", "abcd");
        });
    }

    @Test
    public void testDateParsingFailsOnIllegalInput1() {
        assertThrows(ParseException.class, () -> ParserUtils.parseDate(new Object()));
    }

    @Test
    public void testDateParsingFailsOnIllegalInput2() {
        assertThrows(ParseException.class, () -> ParserUtils.parseDate(""));
    }

    @Test
    public void testStringIsParsedFromString() {
        String parsed = ParserUtils.parseString("Test");

        Assertions.assertEquals("Test", parsed);
    }

    @Test
    public void testStringParsingFailsOnIllegalInput1() {
        assertThrows(ParseException.class, () -> ParserUtils.parseDate(new Object()));
    }

    @Test
    public void testStringParsingFailsOnIllegalInput2() {
        assertThrows(ParseException.class, () -> ParserUtils.parseString(42));
    }

    @Test
    public void testListParsingFailsOnIllegalInput1() {
        assertThrows(ParseException.class, () -> ParserUtils.parseDate(new Object()));
    }

    @Test
    public void testListParsingFailsOnIllegalInput2() {
        assertThrows(ParseException.class, () -> ParserUtils.parseDate(42));
    }

    @Test
    public void testEnumIsParsedFromEnum() {
        DayOfWeek monday = ParserUtils.parseEnum(DayOfWeek.MONDAY, DayOfWeek.class);
        DayOfWeek tuesday = ParserUtils.parseEnum(DayOfWeek.TUESDAY, DayOfWeek.class);
        DayOfWeek friday = ParserUtils.parseEnum(DayOfWeek.FRIDAY, DayOfWeek.class);

        Assertions.assertEquals(DayOfWeek.MONDAY, monday);
        Assertions.assertEquals(DayOfWeek.TUESDAY, tuesday);
        Assertions.assertEquals(DayOfWeek.FRIDAY, friday);
    }

    @Test
    public void testEnumIsParsedFromString() {
        DayOfWeek monday = ParserUtils.parseEnum("monday", DayOfWeek.class);
        DayOfWeek tuesday = ParserUtils.parseEnum("tuesday", DayOfWeek.class);
        DayOfWeek friday = ParserUtils.parseEnum("friday", DayOfWeek.class);

        Assertions.assertEquals(DayOfWeek.MONDAY, monday);
        Assertions.assertEquals(DayOfWeek.TUESDAY, tuesday);
        Assertions.assertEquals(DayOfWeek.FRIDAY, friday);
    }

    @Test
    public void testEnumParsingFailsOnIllegalInput() {
        assertThrows(ParseException.class, () -> ParserUtils.parseEnum("weekend", DayOfWeek.class));
    }

}
