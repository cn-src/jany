/*
 * Copyright 2020-2023 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.javaer.jany.p6spy;

import com.p6spy.engine.common.PreparedStatementInformation;
import com.p6spy.engine.common.Value;
import com.p6spy.engine.event.JdbcEventListener;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.Map;
import java.util.Objects;

/**
 * @author cn-src
 */
public class TimestampJdbcEventListener extends JdbcEventListener {
    @Override
    public void onBeforeExecuteQuery(final PreparedStatementInformation statementInformation) {
        this.onBeforeExecute(statementInformation);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onBeforeExecute(final PreparedStatementInformation statementInformation) {

        final Map<Integer, Value> params;
        try {
            final Method method = PreparedStatementInformation.class
                .getDeclaredMethod("getParameterValues");
            Objects.requireNonNull(method).setAccessible(true);
            params = (Map<Integer, Value>) method.invoke(statementInformation);
        }
        catch (final NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
        if (null == params) {
            return;
        }
        final String url = statementInformation.getConnectionInformation().getUrl();
        for (final Map.Entry<Integer, Value> entry : params.entrySet()) {
            if (entry.getValue().getValue() instanceof Timestamp) {
                params.put(entry.getKey(), new DelegateValue(entry.getValue(), url));
            }
        }
    }
}