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

import com.p6spy.engine.spy.appender.CustomLineFormat;

/**
 * 带缩进的 SQL 格式化.
 *
 * @author cn-src
 */
public class BeautifulFormat extends CustomLineFormat {
    private final BasicFormatter formatter = new BasicFormatter();

    @Override
    public String formatMessage(final int connectionId, final String now, final long elapsed,
                                final String category,
                                final String prepared, final String sql, final String url) {

        return super.formatMessage(connectionId, now, elapsed, category, prepared,
            this.formatter.format(sql), url);
    }
}