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
package com.epam.eco.commons.utils;

import java.time.LocalDateTime;
import java.util.Objects;

import org.apache.commons.lang3.Validate;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Raman_Babich
 */
public class TestEntity {

    private long id;
    private String name;
    private short age;
    private int rating;
    private float initRandom;
    private double magic;
    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;
    private boolean deleted;

    public TestEntity() {
    }

    public TestEntity(
            @JsonProperty("id") long id,
            @JsonProperty("name") String name,
            @JsonProperty("age") short age,
            @JsonProperty("rating") int rating,
            @JsonProperty("initRandom") float initRandom,
            @JsonProperty("magic") double magic,
            @JsonProperty("createdAt") LocalDateTime createdAt,
            @JsonProperty("lastModifiedAt") LocalDateTime lastModifiedAt,
            @JsonProperty("deleted") boolean deleted) {
        Validate.notNull(name);

        this.id = id;
        this.name = name;
        this.age = age;
        this.rating = rating;
        this.initRandom = initRandom;
        this.magic = magic;
        this.createdAt = createdAt;
        this.lastModifiedAt = lastModifiedAt;
        this.deleted = deleted;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public short getAge() {
        return age;
    }

    public void setAge(short age) {
        this.age = age;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public float getInitRandom() {
        return initRandom;
    }

    public void setInitRandom(float initRandom) {
        this.initRandom = initRandom;
    }

    public double getMagic() {
        return magic;
    }

    public void setMagic(double magic) {
        this.magic = magic;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getLastModifiedAt() {
        return lastModifiedAt;
    }

    public void setLastModifiedAt(LocalDateTime lastModifiedAt) {
        this.lastModifiedAt = lastModifiedAt;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestEntity that = (TestEntity) o;
        return id == that.id &&
                age == that.age &&
                rating == that.rating &&
                Float.compare(that.initRandom, initRandom) == 0 &&
                Double.compare(that.magic, magic) == 0 &&
                deleted == that.deleted &&
                Objects.equals(name, that.name) &&
                Objects.equals(createdAt, that.createdAt) &&
                Objects.equals(lastModifiedAt, that.lastModifiedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age, rating, initRandom, magic, createdAt, lastModifiedAt, deleted);
    }

    @Override
    public String toString() {
        return "TestEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", rating=" + rating +
                ", initRandom=" + initRandom +
                ", magic=" + magic +
                ", createdAt=" + createdAt +
                ", lastModifiedAt=" + lastModifiedAt +
                ", deleted=" + deleted +
                '}';
    }
}
