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
package com.epam.eco.commons.parse;

import java.time.DayOfWeek;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Andrei_Tytsik
 */
public class ParserUtilsTest {

    @Test
    public void testBooleanParsedFromBoolean() throws Exception {
        Boolean parsed = ParserUtils.parseBoolean(Boolean.TRUE);

        Assert.assertEquals(Boolean.TRUE, parsed);
    }

    @Test
    public void testBooleanParsedFromString1() throws Exception {
        Boolean parsed = ParserUtils.parseBoolean("true");

        Assert.assertEquals(Boolean.TRUE, parsed);
    }

    @Test
    public void testBooleanParsedFromString2() throws Exception {
        Boolean parsed = ParserUtils.parseBoolean("yes");

        Assert.assertEquals(Boolean.FALSE, parsed);
    }

    @Test(expected = ParseException.class)
    public void testBooleanParsingFailsOnIllegalInput() throws Exception {
        ParserUtils.parseBoolean(new Object());
    }

    @Test
    public void testByteIsParsedFromByte() throws Exception {
        Byte parsed = ParserUtils.parseByte(Byte.MAX_VALUE);

        Assert.assertEquals(Byte.valueOf(Byte.MAX_VALUE), parsed);
    }

    @Test
    public void testByteIsParsedFromNumber() throws Exception {
        Byte parsed = ParserUtils.parseByte((long)Byte.MAX_VALUE);

        Assert.assertEquals(Byte.valueOf(Byte.MAX_VALUE), parsed);
    }

    @Test
    public void testByteIsParsedFromString() throws Exception {
        Byte parsed = ParserUtils.parseByte("" + Byte.MAX_VALUE);

        Assert.assertEquals(Byte.valueOf(Byte.MAX_VALUE), parsed);
    }

    @Test(expected=ParseException.class)
    public void testByteParsingFailsOnIllegalInput1() throws Exception {
        ParserUtils.parseByte(new Object());
    }

    @Test(expected=ParseException.class)
    public void testByteParsingFailsOnIllegalInput2() throws Exception {
        ParserUtils.parseByte("");
    }

    @Test
    public void testShortIsParsedFromShort() throws Exception {
        Short parsed = ParserUtils.parseShort(Short.MAX_VALUE);

        Assert.assertEquals(Short.valueOf(Short.MAX_VALUE), parsed);
    }

    @Test
    public void testShortIsParsedFromNumber() throws Exception {
        Short parsed = ParserUtils.parseShort(Byte.MAX_VALUE);

        Assert.assertEquals(Short.valueOf(Byte.MAX_VALUE), parsed);
    }

    @Test
    public void testShortIsParsedFromString() throws Exception {
        Short parsed = ParserUtils.parseShort("" + Short.MAX_VALUE);

        Assert.assertEquals(Short.valueOf(Short.MAX_VALUE), parsed);
    }

    @Test(expected=ParseException.class)
    public void testShortParsingFailsOnIllegalInput1() throws Exception {
        ParserUtils.parseShort(new Object());
    }

    @Test(expected=ParseException.class)
    public void testShortParsingFailsOnIllegalInput2() throws Exception {
        ParserUtils.parseShort("");
    }

    @Test
    public void testIntegerIsParsedFromInteger() throws Exception {
        Integer parsed = ParserUtils.parseInt(Integer.MAX_VALUE);

        Assert.assertEquals(Integer.valueOf(Integer.MAX_VALUE), parsed);
    }

    @Test
    public void testIntegerIsParsedFromNumber() throws Exception {
        Integer parsed = ParserUtils.parseInt(Short.MAX_VALUE);

        Assert.assertEquals(Integer.valueOf(Short.MAX_VALUE), parsed);
    }

    @Test
    public void testIntegerIsParsedFromString() throws Exception {
        Integer parsed = ParserUtils.parseInt("" + Integer.MAX_VALUE);

        Assert.assertEquals(Integer.valueOf(Integer.MAX_VALUE), parsed);
    }

    @Test(expected=ParseException.class)
    public void testIntegerParsingFailsOnIllegalInput1() throws Exception {
        ParserUtils.parseInt(new Object());
    }

    @Test(expected=ParseException.class)
    public void testIntegerParsingFailsOnIllegalInput2() throws Exception {
        ParserUtils.parseInt("");
    }

    @Test
    public void testLongIsParsedFromLong() throws Exception {
        Long parsed = ParserUtils.parseLong(Long.MAX_VALUE);

        Assert.assertEquals(Long.valueOf(Long.MAX_VALUE), parsed);
    }

    @Test
    public void testLongIsParsedFromNumber() throws Exception {
        Long parsed = ParserUtils.parseLong(Integer.MAX_VALUE);

        Assert.assertEquals(Long.valueOf(Integer.MAX_VALUE), parsed);
    }

    @Test
    public void testLongIsParsedFromString() throws Exception {
        Long parsed = ParserUtils.parseLong("" + Long.MAX_VALUE);

        Assert.assertEquals(Long.valueOf(Long.MAX_VALUE), parsed);
    }

    @Test(expected=ParseException.class)
    public void testLongParsingFailsOnIllegalInput1() throws Exception {
        ParserUtils.parseLong(new Object());
    }

    @Test(expected=ParseException.class)
    public void testLongParsingFailsOnIllegalInput2() throws Exception {
        ParserUtils.parseLong("");
    }

    @Test
    public void testFloatIsParsedFromFloat() throws Exception {
        Float parsed = ParserUtils.parseFloat(Float.MAX_VALUE);

        Assert.assertEquals(Float.valueOf(Float.MAX_VALUE), parsed);
    }

    @Test
    public void testFloatIsParsedFromNumber() throws Exception {
        Float parsed = ParserUtils.parseFloat(Integer.MAX_VALUE);

        Assert.assertEquals(Float.valueOf(Integer.MAX_VALUE), parsed);
    }

    @Test
    public void testFloatIsParsedFromString() throws Exception {
        Float parsed = ParserUtils.parseFloat("" + Float.MAX_VALUE);

        Assert.assertEquals(Float.valueOf(Float.MAX_VALUE), parsed);
    }

    @Test(expected=ParseException.class)
    public void testFloatParsingFailsOnIllegalInput1() throws Exception {
        ParserUtils.parseFloat(new Object());
    }

    @Test(expected=ParseException.class)
    public void testFloatParsingFailsOnIllegalInput2() throws Exception {
        ParserUtils.parseFloat("");
    }

    @Test
    public void testDoubleIsParsedFromDouble() throws Exception {
        Double parsed = ParserUtils.parseDouble(Double.MAX_VALUE);

        Assert.assertEquals(Double.valueOf(Double.MAX_VALUE), parsed);
    }

    @Test
    public void testDoubleIsParsedFromNumber() throws Exception {
        Double parsed = ParserUtils.parseDouble(Integer.MAX_VALUE);

        Assert.assertEquals(Double.valueOf(Integer.MAX_VALUE), parsed);
    }

    @Test
    public void testDoubleIsParsedFromString() throws Exception {
        Double parsed = ParserUtils.parseDouble("" + Double.MAX_VALUE);

        Assert.assertEquals(Double.valueOf(Double.MAX_VALUE), parsed);
    }

    @Test(expected=ParseException.class)
    public void testDoubleParsingFailsOnIllegalInput1() throws Exception {
        ParserUtils.parseDouble(new Object());
    }

    @Test(expected=ParseException.class)
    public void testDoubleParsingFailsOnIllegalInput2() throws Exception {
        ParserUtils.parseDouble("");
    }

    @Test
    public void testDateIsParsedFromDate() throws Exception {
        Date date = new Date(System.currentTimeMillis());

        Date parsed = ParserUtils.parseDate(date);

        Assert.assertEquals(date, parsed);
    }

    @Test
    public void testDateIsParsedFromString() throws Exception {
        Date date = new Date(0);

        Date parsed = ParserUtils.parseDate("1970-01-01T00:00:00");

        Assert.assertEquals(date, parsed);
    }

    @SuppressWarnings("unused")
    @Test(expected = ParseException.class)
    public void testDateIsParsedFromBadStringWithBadPattern() throws Exception {
        Date date = new Date(System.currentTimeMillis());

        Date parsed = ParserUtils.parseDate("abcd", "abcd");
    }

    @Test(expected=ParseException.class)
    public void testDateParsingFailsOnIllegalInput1() throws Exception {
        ParserUtils.parseDate(new Object());
    }

    @Test(expected=ParseException.class)
    public void testDateParsingFailsOnIllegalInput2() throws Exception {
        ParserUtils.parseDate("");
    }

    @Test
    public void testStringIsParsedFromString() throws Exception {
        String parsed = ParserUtils.parseString("Test");

        Assert.assertEquals("Test", parsed);
    }

    @Test(expected=ParseException.class)
    public void testStringParsingFailsOnIllegalInput1() throws Exception {
        ParserUtils.parseDate(new Object());
    }

    @Test(expected=ParseException.class)
    public void testStringParsingFailsOnIllegalInput2() throws Exception {
        ParserUtils.parseString(42);
    }

    @Test(expected=ParseException.class)
    public void testListParsingFailsOnIllegalInput1() throws Exception {
        ParserUtils.parseDate(new Object());
    }

    @Test(expected=ParseException.class)
    public void testListParsingFailsOnIllegalInput2() throws Exception {
        ParserUtils.parseDate(42);
    }

    @Test
    public void testEnumIsParsedFromEnum() {
        DayOfWeek monday = ParserUtils.parseEnum(DayOfWeek.MONDAY, DayOfWeek.class);
        DayOfWeek tuesday = ParserUtils.parseEnum(DayOfWeek.TUESDAY, DayOfWeek.class);
        DayOfWeek friday = ParserUtils.parseEnum(DayOfWeek.FRIDAY, DayOfWeek.class);

        Assert.assertEquals(DayOfWeek.MONDAY, monday);
        Assert.assertEquals(DayOfWeek.TUESDAY, tuesday);
        Assert.assertEquals(DayOfWeek.FRIDAY, friday);
    }

    @Test
    public void testEnumIsParsedFromString() {
        DayOfWeek monday = ParserUtils.parseEnum("monday", DayOfWeek.class);
        DayOfWeek tuesday = ParserUtils.parseEnum("tuesday", DayOfWeek.class);
        DayOfWeek friday = ParserUtils.parseEnum("friday", DayOfWeek.class);

        Assert.assertEquals(DayOfWeek.MONDAY, monday);
        Assert.assertEquals(DayOfWeek.TUESDAY, tuesday);
        Assert.assertEquals(DayOfWeek.FRIDAY, friday);
    }

    @Test(expected = ParseException.class)
    public void testEnumParsingFailsOnIllegalInput() throws Exception {
        ParserUtils.parseEnum("weekend", DayOfWeek.class);
    }

}
