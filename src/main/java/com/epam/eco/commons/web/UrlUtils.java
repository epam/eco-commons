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
package com.epam.eco.commons.web;

import java.util.regex.Pattern;

import org.apache.commons.lang3.Validate;

/**
 * @author Andrei_Tytsik
 */
public abstract class UrlUtils {

    // see https://owasp.org/www-community/OWASP_Validation_Regex_Repository
    private static final String URL_REGEX =
            "^((((https?|ftps?|gopher|telnet|nntp)://)|(mailto:|news:))(%[0-9A-Fa-f]{2}|[-()_.!~*';/?:@&=+$,A-Za-z0-9])+)([).!';/?:,][[:blank:|:blank:]])?$";
    private static final Pattern URL_PATTERN = Pattern.compile(URL_REGEX);

    private UrlUtils() {
    }

    public static void validateUrl(String url) {
        Validate.isTrue(isValidUrl(url));
    }

    public static void validateUrl(String url, String message, Object... values) {
        Validate.isTrue(isValidUrl(url), message, values);
    }

    public static boolean isValidUrl(String url) {
        return url != null && URL_PATTERN.matcher(url).matches();
    }

}
