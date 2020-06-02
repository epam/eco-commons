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
package com.epam.eco.commons.series;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Andrei_Tytsik
 */
public class TimeSeriesTest {

    @Test
    public void testValuesDistributedByGranularity() throws Exception {
        TimeSeries<String> series = new TimeSeries<>(ChronoUnit.HOURS);

        LocalDateTime now = LocalDateTime.now();

        series.append(now, "0");
        series.append(now.plusNanos(1), "1");
        series.append(now.plusSeconds(1), "2");
        series.append(now.plusMinutes(1), "3");

        Assert.assertEquals(1, series.size());
        Iterator<String> iterator = series.toMap().values().iterator();
        Assert.assertEquals("3", iterator.next());

        series.append(now, "0");
        series.append(now.plusHours(1), "1");
        series.append(now.plusHours(2), "2");
        series.append(now.plusHours(3), "3");
        series.append(now.plusHours(4), "4");

        Assert.assertEquals(5, series.size());
        iterator = series.toMap().values().iterator();
        Assert.assertEquals("0", iterator.next());
        Assert.assertEquals("1", iterator.next());
        Assert.assertEquals("2", iterator.next());
        Assert.assertEquals("3", iterator.next());
        Assert.assertEquals("4", iterator.next());
    }

    @Test
    public void testSizeLimited() throws Exception {
        TimeSeries<String> series = new TimeSeries<>(3);

        LocalDateTime now = LocalDateTime.now();

        series.append(now, "0");
        series.append(now.plusMinutes(1), "1");
        series.append(now.plusMinutes(2), "2");
        series.append(now.plusMinutes(3), "3");
        series.append(now.plusMinutes(4), "4");
        series.append(now.plusMinutes(5), "5");

        Assert.assertEquals(3, series.size());
    }

    @Test
    public void testDataMapResolved() throws Exception {
        TimeSeries<String> series = new TimeSeries<>(3);

        LocalDateTime now = LocalDateTime.now();

        series.append(now, "0");
        series.append(now.plusMinutes(1), "1");
        series.append(now.plusMinutes(2), "2");
        series.append(now.plusMinutes(3), "3");
        series.append(now.plusMinutes(4), "4");
        series.append(now.plusMinutes(5), "5");

        Map<LocalDateTime, String> data = series.toMap();
        Assert.assertNotNull(data);
        Assert.assertEquals(3, data.size());

        Iterator<String> iterator = series.toMap().values().iterator();
        Assert.assertEquals("3", iterator.next());
        Assert.assertEquals("4", iterator.next());
        Assert.assertEquals("5", iterator.next());
    }

    @Test
    public void testCopyCreated() throws Exception {
        TimeSeries<String> series = new TimeSeries<>(3);

        TimeSeries<String> copy = series.copy();

        Assert.assertNotNull(copy);
    }

    @Test(expected=Exception.class)
    public void testUnmodifiableCopyCreated() throws Exception {
        TimeSeries<String> series = new TimeSeries<>(3);

        TimeSeries<String> copy = series.unmodifiableCopy();

        Assert.assertNotNull(copy);

        copy.append(LocalDateTime.now(), "1");
    }

    @Test
    public void testDataIterated() throws Exception {
        TimeSeries<String> series = new TimeSeries<>(3);

        LocalDateTime now = LocalDateTime.now();

        series.append(now, "0");
        series.append(now.plusMinutes(1), "1");
        series.append(now.plusMinutes(2), "2");
        series.append(now.plusMinutes(3), "3");
        series.append(now.plusMinutes(4), "4");
        series.append(now.plusMinutes(5), "5");

        Iterator<Entry<LocalDateTime, String>> iterator = series.iterator();
        Assert.assertNotNull(iterator);

        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals("3", iterator.next().getValue());

        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals("4", iterator.next().getValue());

        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals("5", iterator.next().getValue());

        Assert.assertFalse(iterator.hasNext());
    }

    @Test
    public void testKeyAndValueResolved() throws Exception {
        TimeSeries<String> series = new TimeSeries<>(5);

        LocalDateTime now = LocalDateTime.now();

        LocalDateTime key1 = now.plusMinutes(1);
        LocalDateTime key2 = now.plusMinutes(20);
        LocalDateTime key3 = now.plusMinutes(300);

        series.append(key1, "1");
        series.append(key2, "2");
        series.append(key3, "3");

        Assert.assertNotNull(series.key(key1));
        String value = series.value(key1);
        Assert.assertEquals("1", value);

        Assert.assertNotNull(series.key(key2));
        value = series.value(key2);
        Assert.assertEquals("2", value);

        Assert.assertNotNull(series.key(key3));
        value = series.value(key3);
        Assert.assertEquals("3", value);
    }

    @Test
    public void testFirstKeyAndValueResolved() throws Exception {
        TimeSeries<String> series = new TimeSeries<>(5);

        Assert.assertNull(series.firstKey());
        Assert.assertNull(series.firstValue());

        LocalDateTime now = LocalDateTime.now();

        series.append(now.plusMinutes(1), "1");
        series.append(now.plusMinutes(2), "2");
        series.append(now.plusMinutes(3), "3");

        LocalDateTime key = series.firstKey();
        Assert.assertNotNull(key);

        String value = series.value(key);
        Assert.assertEquals("1", value);

        value = series.firstValue();
        Assert.assertEquals("1", value);
    }

    @Test
    public void testLastKeyAndValueResolved() throws Exception {
        TimeSeries<String> series = new TimeSeries<>(5);

        Assert.assertNull(series.lastKey());
        Assert.assertNull(series.lastValue());

        LocalDateTime now = LocalDateTime.now();

        series.append(now.plusMinutes(1), "1");
        series.append(now.plusMinutes(2), "2");
        series.append(now.plusMinutes(3), "3");

        LocalDateTime key = series.lastKey();
        Assert.assertNotNull(key);

        String value = series.value(key);
        Assert.assertEquals("3", value);

        value = series.lastValue();
        Assert.assertEquals("3", value);
    }

    @Test
    public void testNextKeyAndValueResolved() throws Exception {
        TimeSeries<String> series = new TimeSeries<>(5);

        LocalDateTime now = LocalDateTime.now();

        Assert.assertNull(series.nextKey(now));
        Assert.assertNull(series.nextValue(now));

        LocalDateTime key1 = now.plusMinutes(1);
        LocalDateTime key2 = now.plusMinutes(20);
        LocalDateTime key3 = now.plusMinutes(300);

        series.append(key1, "1");
        series.append(key2, "2");
        series.append(key3, "3");

        // 2
        Assert.assertNotNull(series.nextKey(key1));

        String value = series.value(series.nextKey(key1));
        Assert.assertEquals("2", value);

        value = series.nextValue(key1);
        Assert.assertEquals("2", value);

        // 3
        Assert.assertNotNull(series.nextKey(key2));

        value = series.value(series.nextKey(key2));
        Assert.assertEquals("3", value);

        value = series.nextValue(key2);
        Assert.assertEquals("3", value);
    }

    @Test
    public void testPreviousKeyAndValueResolved() throws Exception {
        TimeSeries<String> series = new TimeSeries<>(5);

        LocalDateTime now = LocalDateTime.now();

        Assert.assertNull(series.nextKey(now));
        Assert.assertNull(series.nextValue(now));

        LocalDateTime key1 = now.plusMinutes(1);
        LocalDateTime key2 = now.plusMinutes(20);
        LocalDateTime key3 = now.plusMinutes(300);

        series.append(key1, "1");
        series.append(key2, "2");
        series.append(key3, "3");

        // 2
        Assert.assertNotNull(series.previousKey(key3));

        String value = series.value(series.previousKey(key3));
        Assert.assertEquals("2", value);

        value = series.previousValue(key3);
        Assert.assertEquals("2", value);

        // 1
        Assert.assertNotNull(series.previousKey(key2));

        value = series.value(series.previousKey(key2));
        Assert.assertEquals("1", value);

        value = series.previousValue(key2);
        Assert.assertEquals("1", value);
    }

    @Test
    public void testNextSerialKeyAndValueResolved() throws Exception {
        TimeSeries<String> series = new TimeSeries<>(5);

        LocalDateTime now = LocalDateTime.now();

        Assert.assertNull(series.nextKey(now));
        Assert.assertNull(series.nextValue(now));

        LocalDateTime key1 = now.plusMinutes(1);
        LocalDateTime key2 = now.plusMinutes(2);
        LocalDateTime key3 = now.plusMinutes(30);

        series.append(key1, "1");
        series.append(key2, "2");
        series.append(key3, "3");

        // 2
        Assert.assertNotNull(series.nextSerialKey(key1));

        String value = series.value(series.nextSerialKey(key1));
        Assert.assertEquals("2", value);

        value = series.nextSerialValue(key1);
        Assert.assertEquals("2", value);

        // null
        Assert.assertNull(series.nextSerialKey(key2));
        Assert.assertNull(series.nextSerialValue(key2));
    }

    @Test
    public void testPreviousSerialKeyAndValueResolved() throws Exception {
        TimeSeries<String> series = new TimeSeries<>(5);

        LocalDateTime now = LocalDateTime.now();

        Assert.assertNull(series.nextKey(now));
        Assert.assertNull(series.nextValue(now));

        LocalDateTime key1 = now.plusMinutes(1);
        LocalDateTime key2 = now.plusMinutes(20);
        LocalDateTime key3 = now.plusMinutes(21);

        series.append(key1, "1");
        series.append(key2, "2");
        series.append(key3, "3");

        // 2
        Assert.assertNotNull(series.previousSerialKey(key3));

        String value = series.value(series.previousSerialKey(key3));
        Assert.assertEquals("2", value);

        value = series.previousSerialValue(key3);
        Assert.assertEquals("2", value);

        // null
        Assert.assertNull(series.previousSerialKey(key2));
        Assert.assertNull(series.previousSerialValue(key2));
    }

    @Test
    public void testValuesMerged() throws Exception {
        TimeSeries<Integer> series = new TimeSeries<>(3, MergeFunction.addInt());

        LocalDateTime date = LocalDateTime.now().plusMinutes(10);

        series.append(date, 1);
        Assert.assertEquals(Integer.valueOf(1), series.value(date));

        series.append(date, 2);
        Assert.assertEquals(Integer.valueOf(3), series.value(date));

        series.append(date, 3);
        Assert.assertEquals(Integer.valueOf(6), series.value(date));

        series.append(date, 4);
        Assert.assertEquals(Integer.valueOf(10), series.value(date));

        series.append(date, 5);
        Assert.assertEquals(Integer.valueOf(15), series.value(date));
    }

    @Test(expected=Exception.class)
    public void testUnmodifiable1() throws Exception {
        new TimeSeries<>().toMap().put(LocalDateTime.now(), "1");
    }

    @Test(expected=Exception.class)
    public void testUnmodifiable2() throws Exception {
        TimeSeries<String> series = new TimeSeries<>(3);

        LocalDateTime now = LocalDateTime.now();

        series.append(now, "0");
        series.append(now.plusMinutes(1), "1");
        series.append(now.plusMinutes(2), "2");
        series.append(now.plusMinutes(3), "3");

        series.iterator().remove();
    }

    @Test(expected=Exception.class)
    public void testFailsOnIllegalArguments1() throws Exception {
        new TimeSeries<>((TemporalUnit)null);
    }

    @Test(expected=Exception.class)
    public void testFailsOnIllegalArguments2() throws Exception {
        new TimeSeries<>(-1);
    }

    @Test(expected=Exception.class)
    public void testFailsOnIllegalArguments3() throws Exception {
        new TimeSeries<>((MergeFunction<?>)null);
    }

    @Test(expected=Exception.class)
    public void testFailsOnIllegalArguments4() throws Exception {
        new TimeSeries<>(-1, ChronoUnit.DAYS);
    }

    @Test(expected=Exception.class)
    public void testFailsOnIllegalArguments5() throws Exception {
        new TimeSeries<>(100, (TemporalUnit)null);
    }

    @Test(expected=Exception.class)
    public void testFailsOnIllegalArguments6() throws Exception {
        new TimeSeries<>(-1, MergeFunction.replace());
    }

    @Test(expected=Exception.class)
    public void testFailsOnIllegalArguments7() throws Exception {
        new TimeSeries<>(100, (MergeFunction<?>)null);
    }

    @Test(expected=Exception.class)
    public void testFailsOnIllegalArguments8() throws Exception {
        new TimeSeries<>().append(null, new Object());
    }

    @Test(expected=Exception.class)
    public void testFailsOnIllegalArguments9() throws Exception {
        new TimeSeries<>().append(LocalDateTime.now(), null);
    }

    @Test(expected=Exception.class)
    public void testFailsOnIllegalArguments10() throws Exception {
        new TimeSeries<>().nextKey(null);
    }

    @Test(expected=Exception.class)
    public void testFailsOnIllegalArguments11() throws Exception {
        new TimeSeries<>().previousKey(null);
    }

    @Test(expected=Exception.class)
    public void testFailsOnIllegalArguments12() throws Exception {
        new TimeSeries<>().nextSerialKey(null);
    }

    @Test(expected=Exception.class)
    public void testFailsOnIllegalArguments13() throws Exception {
        new TimeSeries<>().previousSerialKey(null);
    }

    @Test(expected=Exception.class)
    public void testFailsOnIllegalArguments14() throws Exception {
        new TimeSeries<>().nextValue(null);
    }

    @Test(expected=Exception.class)
    public void testFailsOnIllegalArguments15() throws Exception {
        new TimeSeries<>().previousValue(null);
    }

    @Test(expected=Exception.class)
    public void testFailsOnIllegalArguments16() throws Exception {
        new TimeSeries<>().nextSerialValue(null);
    }

    @Test(expected=Exception.class)
    public void testFailsOnIllegalArguments17() throws Exception {
        new TimeSeries<>().previousSerialValue(null);
    }

    @Test(expected=Exception.class)
    public void testFailsOnIllegalArguments18() throws Exception {
        TimeSeries<String> series = new TimeSeries<>(5);

        LocalDateTime now = LocalDateTime.now();

        series.append(now, "1");
        series.append(now.minusHours(1), "2");
    }

}
