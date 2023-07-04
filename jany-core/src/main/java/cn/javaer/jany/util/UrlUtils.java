/*
 * Copyright 2020-2023 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *       https://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.javaer.jany.util;

import cn.hutool.core.util.URLUtil;

public class UrlUtils extends URLUtil {

    public static String join(String path, String... paths) {
        final StringBuilder sb = new StringBuilder();
        sb.append(path);
        for (final String p : paths) {
            if (!p.startsWith("/")) {
                sb.append("/");
            }
            sb.append(p);
        }
        return sb.toString();
    }

}