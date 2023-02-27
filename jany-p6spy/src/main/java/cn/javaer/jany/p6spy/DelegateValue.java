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

import com.p6spy.engine.common.Value;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * @author cn-src
 */
class DelegateValue extends Value {
    private final String url;

    private static final String PATTERN = "yyyy-MM-dd HH:mm:ss";

    public DelegateValue(final Value value, final String url) {
        super(value.getValue());
        this.url = url;
    }

    /**
     * 替换默认 SQL 语句中时间格式直接使用字符串格式的值，使用数据库对应的时间函数来处理.
     *
     * @param value value
     *
     * @return value
     */
    @SuppressWarnings("AlibabaUndefineMagicConstant")
    @Override
    public String convertToString(final Object value) {
        if (value instanceof Timestamp) {
            if (this.url == null) {
                return super.convertToString(value);
            }
            if (this.url.contains(":h2:")) {
                final String val = new SimpleDateFormat(PATTERN).format(value);
                return String.format("parsedatetime('%s', 'yyyy-MM-dd HH:mm:ss')", val);
            }
            if (this.url.contains(":postgresql:")
                || this.url.contains(":pgsql:")) {
                final String val = new SimpleDateFormat(PATTERN).format(value);
                return String.format("to_timestamp('%s', 'yyyy-mm-dd hh24:mi:ss')", val);
            }
            if (this.url.contains(":mysql:") || this.url.contains(":mariadb:")) {
                final String val = new SimpleDateFormat(PATTERN).format(value);
                return "str_to_date('" + val + "','%Y-%m-%d %H:%i:%s')";
            }
            if (this.url.contains(":oracle:")) {
                final String val = new SimpleDateFormat(PATTERN).format(value);
                return "to_date('" + val + "','yyyy-mm-dd hh24:mi:ss')";
            }
        }
        return super.convertToString(value);
    }
}