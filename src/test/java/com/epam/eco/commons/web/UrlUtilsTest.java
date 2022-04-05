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

import org.junit.Assert;
import org.junit.Test;

import com.epam.eco.commons.web.UrlUtils;

/**
 * @author Andrei_Tytsik
 */
public class UrlUtilsTest {

    @Test
    public void testUrlIsValid() throws Exception {
        Assert.assertTrue(UrlUtils.isValidUrl("http://example.com"));
        Assert.assertTrue(UrlUtils.isValidUrl("http://example.com/segment?a=1&b=2"));
    }

    @Test
    public void testUrlIsInvalid() throws Exception {
        Assert.assertFalse(UrlUtils.isValidUrl(null));
        Assert.assertFalse(UrlUtils.isValidUrl(""));
        Assert.assertFalse(UrlUtils.isValidUrl("url"));
        Assert.assertFalse(UrlUtils.isValidUrl("http:"));
        Assert.assertFalse(UrlUtils.isValidUrl("http://"));
        Assert.assertFalse(UrlUtils.isValidUrl("javascript:alert('XSS')"));
        Assert.assertFalse(UrlUtils.isValidUrl("<script>alert('XSS')</script>"));
        Assert.assertFalse(UrlUtils.isValidUrl("http://example.com#javascript:alert('XSS')"));
        Assert.assertFalse(UrlUtils.isValidUrl("http://example.com?e=<script>alert('XSS')</script>"));
    }

}
