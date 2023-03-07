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
package com.epam.eco.commons.diff;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author Raman_Babich
 */
public class DiffCalculatorTest {

    private static final String ORIGINAL_TEXT = "\nline1 \nline2 \nline3";
    private static final String REVISION_TEXT = ORIGINAL_TEXT;
    private static final List<String> ORIGINAL_LINES = Arrays.asList(StringUtils.split(ORIGINAL_TEXT, '\n'));
    private static final List<String> REVISION_LINES = Arrays.asList(StringUtils.split(REVISION_TEXT, '\n'));

    @Test
    public void testCalculate1() {
        String originalId = "1";
        String revisionId = "2";
        Diff diff = DiffCalculator.calculate(
                originalId,
                ORIGINAL_TEXT,
                revisionId,
                REVISION_TEXT);

        Assertions.assertNotNull(diff);
        Assertions.assertEquals(originalId, diff.getOriginalId().get());
        Assertions.assertEquals(revisionId, diff.getRevisionId().get());
        Assertions.assertNotNull(diff.getDiff());
        Assertions.assertTrue(diff.isEmpty());
    }

    @Test
    public void testCalculate2() {
        String originalId = "1";
        String revisionId = "2";
        Diff diff = DiffCalculator.calculate(
                originalId,
                null,
                revisionId,
                REVISION_TEXT);

        Assertions.assertNotNull(diff);
        Assertions.assertEquals(originalId, diff.getOriginalId().get());
        Assertions.assertEquals(revisionId, diff.getRevisionId().get());
        Assertions.assertNotNull(diff.getDiff());
        Assertions.assertFalse(diff.isEmpty());
    }

    @Test
    public void testCalculate3() {
        String originalId = "1";
        String revisionId = "2";
        Diff diff = DiffCalculator.calculate(
                originalId,
                ORIGINAL_TEXT,
                revisionId,
                null);

        Assertions.assertNotNull(diff);
        Assertions.assertEquals(originalId, diff.getOriginalId().get());
        Assertions.assertEquals(revisionId, diff.getRevisionId().get());
        Assertions.assertNotNull(diff.getDiff());
        Assertions.assertFalse(diff.isEmpty());
    }

    @Test
    public void testCalculate4() {
        String originalId = "1";
        String revisionId = "2";
        Diff diff = DiffCalculator.calculate(
                originalId,
                ORIGINAL_LINES,
                revisionId,
                REVISION_LINES);

        Assertions.assertNotNull(diff);
        Assertions.assertEquals(originalId, diff.getOriginalId().get());
        Assertions.assertEquals(revisionId, diff.getRevisionId().get());
        Assertions.assertNotNull(diff.getDiff());
        Assertions.assertTrue(diff.isEmpty());
    }

    @Test
    public void testCalculate5() {
        String originalId = "1";
        String revisionId = "2";
        Diff diff = DiffCalculator.calculate(
                originalId,
                ORIGINAL_LINES,
                revisionId,
                null);

        Assertions.assertNotNull(diff);
        Assertions.assertEquals(originalId, diff.getOriginalId().get());
        Assertions.assertEquals(revisionId, diff.getRevisionId().get());
        Assertions.assertNotNull(diff.getDiff());
        Assertions.assertFalse(diff.isEmpty());
    }

    @Test
    public void testCalculate6() {
        String originalId = "1";
        String revisionId = "2";
        Diff diff = DiffCalculator.calculate(
                originalId,
                null,
                revisionId,
                REVISION_LINES);

        Assertions.assertNotNull(diff);
        Assertions.assertEquals(originalId, diff.getOriginalId().get());
        Assertions.assertEquals(revisionId, diff.getRevisionId().get());
        Assertions.assertNotNull(diff.getDiff());
        Assertions.assertFalse(diff.isEmpty());
    }

    @Test
    public void testCalculate7() {
        String id = "1";
        Diff diff = DiffCalculator.calculate(
                id,
                null,
                REVISION_LINES);

        Assertions.assertNotNull(diff);
        Assertions.assertEquals(id, diff.getOriginalId().get());
        Assertions.assertEquals(id, diff.getRevisionId().get());
        Assertions.assertNotNull(diff.getDiff());
        Assertions.assertFalse(diff.isEmpty());
    }

    @Test
    public void testCalculate8() {
        String id = "1";
        Diff diff = DiffCalculator.calculate(
                id,
                ORIGINAL_LINES,
                null);

        Assertions.assertNotNull(diff);
        Assertions.assertEquals(id, diff.getOriginalId().get());
        Assertions.assertEquals(id, diff.getRevisionId().get());
        Assertions.assertNotNull(diff.getDiff());
        Assertions.assertFalse(diff.isEmpty());
    }

    @Test
    public void testCalculate9() {
        String id = "1";
        Diff diff = DiffCalculator.calculate(
                id,
                null,
                REVISION_TEXT);

        Assertions.assertNotNull(diff);
        Assertions.assertEquals(id, diff.getOriginalId().get());
        Assertions.assertEquals(id, diff.getRevisionId().get());
        Assertions.assertNotNull(diff.getDiff());
        Assertions.assertFalse(diff.isEmpty());
    }

    @Test
    public void testCalculate10() {
        String id = "1";
        Diff diff = DiffCalculator.calculate(
                id,
                ORIGINAL_TEXT,
                null);

        Assertions.assertNotNull(diff);
        Assertions.assertEquals(id, diff.getOriginalId().get());
        Assertions.assertEquals(id, diff.getRevisionId().get());
        Assertions.assertNotNull(diff.getDiff());
        Assertions.assertFalse(diff.isEmpty());
    }

    @Test
    public void testCalculate11() {
        String originalId = "1";
        String revisionId = null;
        Diff diff = DiffCalculator.calculate(
                originalId,
                ORIGINAL_TEXT,
                revisionId,
                null);

        Assertions.assertNotNull(diff);
        Assertions.assertEquals(originalId, diff.getOriginalId().get());
        Assertions.assertFalse(diff.getRevisionId().isPresent());
        Assertions.assertNotNull(diff.getDiff());
        Assertions.assertFalse(diff.isEmpty());
    }

    @Test
    public void testCalculate12() {
        String originalId = null;
        String revisionId = "2";
        Diff diff = DiffCalculator.calculate(
                originalId,
                null,
                revisionId,
                REVISION_LINES);

        Assertions.assertNotNull(diff);
        Assertions.assertFalse(diff.getOriginalId().isPresent());
        Assertions.assertEquals(revisionId, diff.getRevisionId().get());
        Assertions.assertNotNull(diff.getDiff());
        Assertions.assertFalse(diff.isEmpty());
    }


}
