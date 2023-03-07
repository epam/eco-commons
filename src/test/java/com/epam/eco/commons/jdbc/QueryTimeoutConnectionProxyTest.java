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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

/**
 * @author Uladzislau_Belykh
 */
@ExtendWith(MockitoExtension.class)
public class QueryTimeoutConnectionProxyTest {

    @Mock
    private Connection testConnection;
    @Mock
    private Statement testStatement;

    @Test
    public void testCreateFailsOnNegativeTimeout() {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> QueryTimeoutConnectionProxy.create(testConnection, -1),
                "Query timeout is invalid: -1"
        );
    }

    @Test
    public void testCreateFailsOnNullInstance() {
        Assertions.assertThrows(
                NullPointerException.class,
                () -> QueryTimeoutConnectionProxy.create(null, 1),
                "Instance can't be null"
        );
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
        Assertions.assertEquals(0, wrappedConnection.createStatement().getQueryTimeout());
    }
}