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
package com.epam.eco.commons.diff;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.Validate;

/**
 * @author Andrei_Tytsik
 */
public class Diff {

    private final String originalId;
    private final String revisionId;
    private final List<String> diff;

    public Diff(String originalId, String revisionId, List<String> diff) {
        Validate.isTrue(revisionId != null || originalId != null,
                "Original id or revision id can't be null");

        this.originalId = originalId;
        this.revisionId = revisionId;
        this.diff = diff == null ? Collections.emptyList() : Collections.unmodifiableList(new ArrayList<>(diff));
    }

    public Optional<String> getOriginalId() {
        return Optional.ofNullable(originalId);
    }

    public Optional<String> getRevisionId() {
        return Optional.ofNullable(revisionId);
    }

    public List<String> getDiff() {
        return diff;
    }

    public boolean isEmpty() {
        return diff.isEmpty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Diff diff1 = (Diff) o;
        return Objects.equals(originalId, diff1.originalId) &&
                Objects.equals(revisionId, diff1.revisionId) &&
                Objects.equals(diff, diff1.diff);
    }

    @Override
    public int hashCode() {
        return Objects.hash(originalId, revisionId, diff);
    }

    @Override
    public String toString() {
        return "Diff{" +
                "originalId='" + originalId + '\'' +
                ", revisionId='" + revisionId + '\'' +
                ", diff=" + diff +
                '}';
    }
}
