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
package com.epam.eco.commons.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.lang3.mutable.MutableObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

/**
 * @author Uladzislau_Belykh
 */
@RunWith(MockitoJUnitRunner.class)
public class QueryTimeoutConnectionProxyTest {

    @Mock
    private Connection testConnection;
    @Mock
    private Statement testStatement;

    @Test
    public void testCreateFailsOnNegativeTimeout() {
        Assert.assertThrows("Query timeout is invalid: -1", IllegalArgumentException.class,
                () -> QueryTimeoutConnectionProxy.create(testConnection, -1));
    }

    @Test
    public void testCreateFailsOnNullInstance() {
        Assert.assertThrows("Instance can't be null", NullPointerException.class,
                () -> QueryTimeoutConnectionProxy.create(null, 1));
    }

    @Test
    public void testTimeoutIsSet() throws SQLException {
        MutableObject<Integer> timeout = new MutableObject<>();
        when(testConnection.createStatement()).thenReturn(testStatement);
        doAnswer(invocationOnMock -> {
            timeout.setValue((Integer) invocationOnMock.getArguments()[0]);
            return null;
        }).when(testStatement).setQueryTimeout(anyInt());
        when(testStatement.getQueryTimeout()).thenAnswer(invocationOnMock -> timeout.getValue());

        Connection wrappedConnection = QueryTimeoutConnectionProxy.create(testConnection, 0);
        Assert.assertEquals(0, wrappedConnection.createStatement().getQueryTimeout());
    }
}