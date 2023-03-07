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
package com.epam.eco.commons.web;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author Andrei_Tytsik
 */
public class UrlUtilsTest {

    @Test
    public void testUrlIsValid() {
        Assertions.assertTrue(UrlUtils.isValidUrl("http://example.com"));
        Assertions.assertTrue(UrlUtils.isValidUrl("http://example.com/segment?a=1&b=2"));
    }

    @Test
    public void testUrlIsInvalid() {
        Assertions.assertFalse(UrlUtils.isValidUrl(null));
        Assertions.assertFalse(UrlUtils.isValidUrl(""));
        Assertions.assertFalse(UrlUtils.isValidUrl("url"));
        Assertions.assertFalse(UrlUtils.isValidUrl("http:"));
        Assertions.assertFalse(UrlUtils.isValidUrl("http://"));
        Assertions.assertFalse(UrlUtils.isValidUrl("javascript:alert('XSS')"));
        Assertions.assertFalse(UrlUtils.isValidUrl("<script>alert('XSS')</script>"));
        Assertions.assertFalse(UrlUtils.isValidUrl("http://example.com#javascript:alert('XSS')"));
        Assertions.assertFalse(UrlUtils.isValidUrl("http://example.com?e=<script>alert('XSS')</script>"));
    }

}
