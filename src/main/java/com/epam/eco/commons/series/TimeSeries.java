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
package com.epam.eco.commons.series;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TimeZone;

import org.apache.commons.collections4.map.LinkedMap;
import org.apache.commons.lang3.Validate;

/**
 * @author Andrei_Tytsik
 */
public class TimeSeries<V> implements Iterable<Map.Entry<LocalDateTime, V>> {

    private static final int DEFAULT_MAX_SIZE = 10;
    private static final TemporalUnit DEFAULT_GRANULARITY = ChronoUnit.MINUTES;

    private final boolean modifiable;
    private final int maxSize;
    private final TemporalUnit granularity;
    private final MergeFunction<V> mergeFunction;
    private final LinkedMap<LocalDateTime, V> data;

    public TimeSeries() {
        this(DEFAULT_MAX_SIZE, DEFAULT_GRANULARITY);
    }

    public TimeSeries(TemporalUnit granularity) {
        this(DEFAULT_MAX_SIZE, granularity);
    }

    public TimeSeries(int maxSize) {
        this(maxSize, DEFAULT_GRANULARITY);
    }

    public TimeSeries(MergeFunction<V> mergeFunction) {
        this(DEFAULT_MAX_SIZE, DEFAULT_GRANULARITY, mergeFunction);
    }

    public TimeSeries(int maxSize, TemporalUnit granularity) {
        this(maxSize, granularity, defaultMergeFunction());
    }

    public TimeSeries(TemporalUnit granularity, MergeFunction<V> mergeFunction) {
        this(DEFAULT_MAX_SIZE, granularity, mergeFunction);
    }

    public TimeSeries(int maxSize, MergeFunction<V> mergeFunction) {
        this(maxSize, DEFAULT_GRANULARITY, mergeFunction);
    }

    public TimeSeries(
            int maxSize,
            TemporalUnit granularity,
            MergeFunction<V> mergeFunction) {
        this(true, maxSize, granularity, mergeFunction, Collections.emptyMap());
    }

    protected TimeSeries(
            boolean modifiable,
            int maxSize,
            TemporalUnit granularity,
            MergeFunction<V> mergeFunction,
            Map<LocalDateTime, V> data) {
        Validate.isTrue(maxSize > 0, "Max size is invalid");
        Validate.notNull(granularity, "Granularity is null");
        Validate.notNull(mergeFunction, "MergeFunction is null");

        this.modifiable = modifiable;
        this.maxSize = maxSize;
        this.granularity = granularity;
        this.mergeFunction = mergeFunction;
        this.data = new LinkedMap<>(data != null ? data : Collections.emptyMap());
    }

    public void append(V value) {
        append(LocalDateTime.now(), value);
    }

    public void append(long date, V value) {
        append(
                LocalDateTime.ofInstant(
                        Instant.ofEpochMilli(date),
                        TimeZone.getDefault().toZoneId()),
                value);
    }

    public void append(LocalDateTime date, V value) {
        if (!modifiable) {
            throw new UnsupportedOperationException();
        }

        Validate.notNull(date, "Date is null");
        Validate.notNull(value, "Value is null");

        LocalDateTime lastDate = lastKey();
        if (lastDate != null && date.isBefore(lastDate)) {
            throw new IllegalArgumentException(
                    String.format(
                            "Date %s is lower than last timeseries' date %s",
                            date, lastDate));
        }

        date = toSerialKey(date);
        data.merge(date, value, mergeFunction);

        if (data.size() > maxSize) {
            data.remove(data.firstKey());
        }
    }

    public int size() {
        return data.size();
    }

    public TemporalUnit getGranularity() {
        return granularity;
    }

    public Map<LocalDateTime, V> toMap() {
        return Collections.unmodifiableMap(data);
    }

    public TimeSeries<V> copy() {
        return new TimeSeries<>(modifiable, maxSize, granularity, mergeFunction, data);
    }

    public TimeSeries<V> unmodifiableCopy() {
        return new TimeSeries<>(false, maxSize, granularity, mergeFunction, data);
    }

    @Override
    public Iterator<Entry<LocalDateTime, V>> iterator() {
        return toMap().entrySet().iterator();
    }

    public LocalDateTime key(LocalDateTime date) {
        Validate.notNull(date, "Date is null");

        date = toSerialKey(date);
        return data.containsKey(date) ? date : null;
    }

    public LocalDateTime lastKey() {
        return data.size() > 0 ? data.lastKey() : null;
    }

    public LocalDateTime firstKey() {
        return data.size() > 0 ? data.firstKey() : null;
    }

    public LocalDateTime previousKey(LocalDateTime date) {
        Validate.notNull(date, "Date is null");

        date = toSerialKey(date);
        return data.previousKey(date);
    }

    public LocalDateTime nextKey(LocalDateTime date) {
        Validate.notNull(date, "Date is null");

        date = toSerialKey(date);
        return data.nextKey(date);
    }

    public LocalDateTime previousSerialKey(LocalDateTime date) {
        Validate.notNull(date, "Date is null");

        date = toSerialKey(date, -1);
        return data.containsKey(date) ? date : null;
    }

    public LocalDateTime nextSerialKey(LocalDateTime date) {
        Validate.notNull(date, "Date is null");

        date = toSerialKey(date, 1);
        return data.containsKey(date) ? date : null;
    }

    public V value(LocalDateTime date) {
        Validate.notNull(date, "Date is null");

        date = toSerialKey(date);
        return data.get(date);
    }

    public V lastValue() {
        LocalDateTime date = lastKey();
        return date != null ? data.get(date) : null;
    }

    public V firstValue() {
        LocalDateTime date = firstKey();
        return date != null ? data.get(date) : null;
    }

    public V previousValue(LocalDateTime date) {
        date = previousKey(date);
        return date != null ? data.get(date) : null;
    }

    public V nextValue(LocalDateTime date) {
        date = nextKey(date);
        return date != null ? data.get(date) : null;
    }

    public V previousSerialValue(LocalDateTime date) {
        date = previousSerialKey(date);
        return date != null ? data.get(date) : null;
    }

    public V nextSerialValue(LocalDateTime date) {
        date = nextSerialKey(date);
        return date != null ? data.get(date) : null;
    }

    private LocalDateTime toSerialKey(LocalDateTime date) {
        return toSerialKey(date, 0);
    }

    private LocalDateTime toSerialKey(LocalDateTime date, long offset) {
        date = date.truncatedTo(granularity);
        if (offset != 0) {
            date = date.plus(offset, granularity);
        }
        return date;
    }

    private static <V> MergeFunction<V> defaultMergeFunction() {
        return MergeFunction.replace();
    }

}
