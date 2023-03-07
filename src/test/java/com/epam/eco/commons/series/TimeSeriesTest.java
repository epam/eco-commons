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
package com.epam.eco.commons.series;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Andrei_Tytsik
 */
public class TimeSeriesTest {

    @Test
    public void testValuesDistributedByGranularity() {
        TimeSeries<String> series = new TimeSeries<>(ChronoUnit.HOURS);

        LocalDateTime now = LocalDateTime.now();

        series.append(now, "0");
        series.append(now.plusNanos(1), "1");
        series.append(now.plusSeconds(1), "2");
        series.append(now.plusMinutes(1), "3");

        Assertions.assertEquals(1, series.size());
        Iterator<String> iterator = series.toMap().values().iterator();
        Assertions.assertEquals("3", iterator.next());

        series.append(now, "0");
        series.append(now.plusHours(1), "1");
        series.append(now.plusHours(2), "2");
        series.append(now.plusHours(3), "3");
        series.append(now.plusHours(4), "4");

        Assertions.assertEquals(5, series.size());
        iterator = series.toMap().values().iterator();
        Assertions.assertEquals("0", iterator.next());
        Assertions.assertEquals("1", iterator.next());
        Assertions.assertEquals("2", iterator.next());
        Assertions.assertEquals("3", iterator.next());
        Assertions.assertEquals("4", iterator.next());
    }

    @Test
    public void testSizeLimited() {
        TimeSeries<String> series = new TimeSeries<>(3);

        LocalDateTime now = LocalDateTime.now();

        series.append(now, "0");
        series.append(now.plusMinutes(1), "1");
        series.append(now.plusMinutes(2), "2");
        series.append(now.plusMinutes(3), "3");
        series.append(now.plusMinutes(4), "4");
        series.append(now.plusMinutes(5), "5");

        Assertions.assertEquals(3, series.size());
    }

    @Test
    public void testDataMapResolved() {
        TimeSeries<String> series = new TimeSeries<>(3);

        LocalDateTime now = LocalDateTime.now();

        series.append(now, "0");
        series.append(now.plusMinutes(1), "1");
        series.append(now.plusMinutes(2), "2");
        series.append(now.plusMinutes(3), "3");
        series.append(now.plusMinutes(4), "4");
        series.append(now.plusMinutes(5), "5");

        Map<LocalDateTime, String> data = series.toMap();
        Assertions.assertNotNull(data);
        Assertions.assertEquals(3, data.size());

        Iterator<String> iterator = series.toMap().values().iterator();
        Assertions.assertEquals("3", iterator.next());
        Assertions.assertEquals("4", iterator.next());
        Assertions.assertEquals("5", iterator.next());
    }

    @Test
    public void testCopyCreated() {
        TimeSeries<String> series = new TimeSeries<>(3);

        TimeSeries<String> copy = series.copy();

        Assertions.assertNotNull(copy);
    }

    @Test
    public void testUnmodifiableCopyCreated() {
        assertThrows(Exception.class, () -> {
            TimeSeries<String> series = new TimeSeries<>(3);

            TimeSeries<String> copy = series.unmodifiableCopy();

            Assertions.assertNotNull(copy);

            copy.append(LocalDateTime.now(), "1");
        });
    }

    @Test
    public void testDataIterated() {
        TimeSeries<String> series = new TimeSeries<>(3);

        LocalDateTime now = LocalDateTime.now();

        series.append(now, "0");
        series.append(now.plusMinutes(1), "1");
        series.append(now.plusMinutes(2), "2");
        series.append(now.plusMinutes(3), "3");
        series.append(now.plusMinutes(4), "4");
        series.append(now.plusMinutes(5), "5");

        Iterator<Entry<LocalDateTime, String>> iterator = series.iterator();
        Assertions.assertNotNull(iterator);

        Assertions.assertTrue(iterator.hasNext());
        Assertions.assertEquals("3", iterator.next().getValue());

        Assertions.assertTrue(iterator.hasNext());
        Assertions.assertEquals("4", iterator.next().getValue());

        Assertions.assertTrue(iterator.hasNext());
        Assertions.assertEquals("5", iterator.next().getValue());

        Assertions.assertFalse(iterator.hasNext());
    }

    @Test
    public void testKeyAndValueResolved() {
        TimeSeries<String> series = new TimeSeries<>(5);

        LocalDateTime now = LocalDateTime.now();

        LocalDateTime key1 = now.plusMinutes(1);
        LocalDateTime key2 = now.plusMinutes(20);
        LocalDateTime key3 = now.plusMinutes(300);

        series.append(key1, "1");
        series.append(key2, "2");
        series.append(key3, "3");

        Assertions.assertNotNull(series.key(key1));
        String value = series.value(key1);
        Assertions.assertEquals("1", value);

        Assertions.assertNotNull(series.key(key2));
        value = series.value(key2);
        Assertions.assertEquals("2", value);

        Assertions.assertNotNull(series.key(key3));
        value = series.value(key3);
        Assertions.assertEquals("3", value);
    }

    @Test
    public void testFirstKeyAndValueResolved() {
        TimeSeries<String> series = new TimeSeries<>(5);

        Assertions.assertNull(series.firstKey());
        Assertions.assertNull(series.firstValue());

        LocalDateTime now = LocalDateTime.now();

        series.append(now.plusMinutes(1), "1");
        series.append(now.plusMinutes(2), "2");
        series.append(now.plusMinutes(3), "3");

        LocalDateTime key = series.firstKey();
        Assertions.assertNotNull(key);

        String value = series.value(key);
        Assertions.assertEquals("1", value);

        value = series.firstValue();
        Assertions.assertEquals("1", value);
    }

    @Test
    public void testLastKeyAndValueResolved() {
        TimeSeries<String> series = new TimeSeries<>(5);

        Assertions.assertNull(series.lastKey());
        Assertions.assertNull(series.lastValue());

        LocalDateTime now = LocalDateTime.now();

        series.append(now.plusMinutes(1), "1");
        series.append(now.plusMinutes(2), "2");
        series.append(now.plusMinutes(3), "3");

        LocalDateTime key = series.lastKey();
        Assertions.assertNotNull(key);

        String value = series.value(key);
        Assertions.assertEquals("3", value);

        value = series.lastValue();
        Assertions.assertEquals("3", value);
    }

    @Test
    public void testNextKeyAndValueResolved() {
        TimeSeries<String> series = new TimeSeries<>(5);

        LocalDateTime now = LocalDateTime.now();

        Assertions.assertNull(series.nextKey(now));
        Assertions.assertNull(series.nextValue(now));

        LocalDateTime key1 = now.plusMinutes(1);
        LocalDateTime key2 = now.plusMinutes(20);
        LocalDateTime key3 = now.plusMinutes(300);

        series.append(key1, "1");
        series.append(key2, "2");
        series.append(key3, "3");

        // 2
        Assertions.assertNotNull(series.nextKey(key1));

        String value = series.value(series.nextKey(key1));
        Assertions.assertEquals("2", value);

        value = series.nextValue(key1);
        Assertions.assertEquals("2", value);

        // 3
        Assertions.assertNotNull(series.nextKey(key2));

        value = series.value(series.nextKey(key2));
        Assertions.assertEquals("3", value);

        value = series.nextValue(key2);
        Assertions.assertEquals("3", value);
    }

    @Test
    public void testPreviousKeyAndValueResolved() {
        TimeSeries<String> series = new TimeSeries<>(5);

        LocalDateTime now = LocalDateTime.now();

        Assertions.assertNull(series.nextKey(now));
        Assertions.assertNull(series.nextValue(now));

        LocalDateTime key1 = now.plusMinutes(1);
        LocalDateTime key2 = now.plusMinutes(20);
        LocalDateTime key3 = now.plusMinutes(300);

        series.append(key1, "1");
        series.append(key2, "2");
        series.append(key3, "3");

        // 2
        Assertions.assertNotNull(series.previousKey(key3));

        String value = series.value(series.previousKey(key3));
        Assertions.assertEquals("2", value);

        value = series.previousValue(key3);
        Assertions.assertEquals("2", value);

        // 1
        Assertions.assertNotNull(series.previousKey(key2));

        value = series.value(series.previousKey(key2));
        Assertions.assertEquals("1", value);

        value = series.previousValue(key2);
        Assertions.assertEquals("1", value);
    }

    @Test
    public void testNextSerialKeyAndValueResolved() {
        TimeSeries<String> series = new TimeSeries<>(5);

        LocalDateTime now = LocalDateTime.now();

        Assertions.assertNull(series.nextKey(now));
        Assertions.assertNull(series.nextValue(now));

        LocalDateTime key1 = now.plusMinutes(1);
        LocalDateTime key2 = now.plusMinutes(2);
        LocalDateTime key3 = now.plusMinutes(30);

        series.append(key1, "1");
        series.append(key2, "2");
        series.append(key3, "3");

        // 2
        Assertions.assertNotNull(series.nextSerialKey(key1));

        String value = series.value(series.nextSerialKey(key1));
        Assertions.assertEquals("2", value);

        value = series.nextSerialValue(key1);
        Assertions.assertEquals("2", value);

        // null
        Assertions.assertNull(series.nextSerialKey(key2));
        Assertions.assertNull(series.nextSerialValue(key2));
    }

    @Test
    public void testPreviousSerialKeyAndValueResolved() {
        TimeSeries<String> series = new TimeSeries<>(5);

        LocalDateTime now = LocalDateTime.now();

        Assertions.assertNull(series.nextKey(now));
        Assertions.assertNull(series.nextValue(now));

        LocalDateTime key1 = now.plusMinutes(1);
        LocalDateTime key2 = now.plusMinutes(20);
        LocalDateTime key3 = now.plusMinutes(21);

        series.append(key1, "1");
        series.append(key2, "2");
        series.append(key3, "3");

        // 2
        Assertions.assertNotNull(series.previousSerialKey(key3));

        String value = series.value(series.previousSerialKey(key3));
        Assertions.assertEquals("2", value);

        value = series.previousSerialValue(key3);
        Assertions.assertEquals("2", value);

        // null
        Assertions.assertNull(series.previousSerialKey(key2));
        Assertions.assertNull(series.previousSerialValue(key2));
    }

    @Test
    public void testValuesMerged() {
        TimeSeries<Integer> series = new TimeSeries<>(3, MergeFunction.addInt());

        LocalDateTime date = LocalDateTime.now().plusMinutes(10);

        series.append(date, 1);
        Assertions.assertEquals(Integer.valueOf(1), series.value(date));

        series.append(date, 2);
        Assertions.assertEquals(Integer.valueOf(3), series.value(date));

        series.append(date, 3);
        Assertions.assertEquals(Integer.valueOf(6), series.value(date));

        series.append(date, 4);
        Assertions.assertEquals(Integer.valueOf(10), series.value(date));

        series.append(date, 5);
        Assertions.assertEquals(Integer.valueOf(15), series.value(date));
    }

    @Test
    public void testUnmodifiable1() {
        assertThrows(Exception.class, () -> new TimeSeries<>().toMap().put(LocalDateTime.now(), "1"));
    }

    @Test
    public void testUnmodifiable2() {
        assertThrows(Exception.class, () -> {
            TimeSeries<String> series = new TimeSeries<>(3);

            LocalDateTime now = LocalDateTime.now();

            series.append(now, "0");
            series.append(now.plusMinutes(1), "1");
            series.append(now.plusMinutes(2), "2");
            series.append(now.plusMinutes(3), "3");

            series.iterator().remove();
        });
    }

    @Test
    public void testFailsOnIllegalArguments1() {
        assertThrows(Exception.class, () -> new TimeSeries<>((TemporalUnit)null));
    }

    @Test
    public void testFailsOnIllegalArguments2() {
        assertThrows(Exception.class, () -> new TimeSeries<>(-1));
    }

    @Test
    public void testFailsOnIllegalArguments3() {
        assertThrows(Exception.class, () -> new TimeSeries<>((MergeFunction<?>)null));
    }

    @Test
    public void testFailsOnIllegalArguments4() {
        assertThrows(Exception.class, () -> new TimeSeries<>(-1, ChronoUnit.DAYS));
    }

    @Test
    public void testFailsOnIllegalArguments5() {
        assertThrows(Exception.class, () -> new TimeSeries<>(100, (TemporalUnit)null));
    }

    @Test
    public void testFailsOnIllegalArguments6() {
        assertThrows(Exception.class, () -> new TimeSeries<>(-1, MergeFunction.replace()));
    }

    @Test
    public void testFailsOnIllegalArguments7() {
        assertThrows(Exception.class, () -> new TimeSeries<>(100, (MergeFunction<?>)null));
    }

    @Test
    public void testFailsOnIllegalArguments8() {
        assertThrows(Exception.class, () -> new TimeSeries<>().append(null, new Object()));
    }

    @Test
    public void testFailsOnIllegalArguments9() {
        assertThrows(Exception.class, () -> new TimeSeries<>().append(LocalDateTime.now(), null));
    }

    @Test
    public void testFailsOnIllegalArguments10() {
        assertThrows(Exception.class, () -> new TimeSeries<>().nextKey(null));
    }

    @Test
    public void testFailsOnIllegalArguments11() {
        assertThrows(Exception.class, () -> new TimeSeries<>().previousKey(null));
    }

    @Test
    public void testFailsOnIllegalArguments12() {
        assertThrows(Exception.class, () -> new TimeSeries<>().nextSerialKey(null));
    }

    @Test
    public void testFailsOnIllegalArguments13() {
        assertThrows(Exception.class, () -> new TimeSeries<>().previousSerialKey(null));
    }

    @Test
    public void testFailsOnIllegalArguments14() {
        assertThrows(Exception.class, () -> new TimeSeries<>().nextValue(null));
    }

    @Test
    public void testFailsOnIllegalArguments15() {
        assertThrows(Exception.class, () -> new TimeSeries<>().previousValue(null));
    }

    @Test
    public void testFailsOnIllegalArguments16() {
        assertThrows(Exception.class, () -> new TimeSeries<>().nextSerialValue(null));
    }

    @Test
    public void testFailsOnIllegalArguments17() {
        assertThrows(Exception.class, () -> new TimeSeries<>().previousSerialValue(null));
    }

    @Test
    public void testFailsOnIllegalArguments18() {
        assertThrows(Exception.class, () -> {
            TimeSeries<String> series = new TimeSeries<>(5);

            LocalDateTime now = LocalDateTime.now();

            series.append(now, "1");
            series.append(now.minusHours(1), "2");
        });
    }
}
