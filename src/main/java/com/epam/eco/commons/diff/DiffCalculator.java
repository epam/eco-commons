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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import difflib.DiffUtils;
import difflib.Patch;

/**
 * @author Andrei_Tytsik
 */
public class DiffCalculator {

    private static final int DIFF_CONTEXT_SIZE = 10;
    private static final String DEFAULT_ORIGINAL_POSTFIX = "-original";
    private static final String DEFAULT_REVISION_POSTFIX = "-revision";
    private static final String DEFAULT_ID_STRING = "N/A";

    public static Diff calculate(
            String originalId,
            String originalText,
            String revisionId,
            String revisedText) {
        return calculate(originalId, toLines(originalText), revisionId, toLines(revisedText));
    }

    public static Diff calculate(
            String id,
            String originalText,
            String revisedText) {
        return calculate(id, toLines(originalText), toLines(revisedText));
    }

    public static Diff calculate(
            String id,
            List<String> originalLines,
            List<String> revisionLines) {
        return calculate(id, originalLines, id, revisionLines);
    }

    public static Diff calculate(
            String originalId,
            List<String> originalLines,
            String revisionId,
            List<String> revisionLines) {
        if (originalLines != null) {
            Validate.notNull(originalId, "Original id can't be null when original lines is specified");
        } else {
            originalLines = Collections.emptyList();
        }
        if (revisionLines != null) {
            Validate.notNull(revisionId, "Revision id can't be null when revision lines is specified");
        } else {
            revisionLines = Collections.emptyList();
        }
        Validate.isTrue(originalId != null || revisionId != null, "Origin id or revision id can't be null");

        String effectiveOriginalId = originalId;
        String effectiveRevisionId = revisionId;
        Patch patch = DiffUtils.diff(originalLines, revisionLines);
        if (StringUtils.equals(originalId, revisionId)) {
            effectiveOriginalId = originalId + DEFAULT_ORIGINAL_POSTFIX;
            effectiveRevisionId = revisionId + DEFAULT_REVISION_POSTFIX;
        }

        List<String> diff = DiffUtils.generateUnifiedDiff(
                effectiveOriginalId == null ? DEFAULT_ID_STRING : effectiveOriginalId,
                effectiveRevisionId == null ? DEFAULT_ID_STRING : effectiveRevisionId,
                originalLines,
                patch,
                DIFF_CONTEXT_SIZE);

        return new Diff(originalId, revisionId, diff);
    }

    private static List<String> toLines(String text) {
        if (text == null) {
            return null;
        }

        return Arrays.asList(StringUtils.split(text, '\n'));
    }

}
