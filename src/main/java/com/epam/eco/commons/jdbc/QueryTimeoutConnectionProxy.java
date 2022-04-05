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

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.Statement;

import org.apache.commons.lang3.Validate;

/**
 * @author Uladzislau_Belykh
 */
public abstract class QueryTimeoutConnectionProxy {

    public static Connection create(Connection instance, int queryTimeoutSec) {
        Validate.isTrue(queryTimeoutSec >= 0, "Query timeout is invalid: %d", queryTimeoutSec);
        Validate.notNull(instance, "Instance can't be null");

        return (Connection) Proxy.newProxyInstance(
                QueryTimeoutConnectionProxy.class.getClassLoader(),
                new Class[]{Connection.class},
                new QueryTimeoutConnectionInvocationHandler(instance, queryTimeoutSec));
    }

    private static class QueryTimeoutConnectionInvocationHandler implements InvocationHandler {

        private final Connection instance;

        private final int queryTimeoutSec;
        public QueryTimeoutConnectionInvocationHandler(Connection instance, int queryTimeoutSec) {
            this.instance = instance;
            this.queryTimeoutSec = queryTimeoutSec;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Object result = method.invoke(instance, args);
            if (result instanceof Statement) {
                ((Statement) result).setQueryTimeout(queryTimeoutSec);
            }
            return result;
        }
    }

    private QueryTimeoutConnectionProxy() {
    }
}
