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

package cn.javaer.jany.spring.util;

import cn.hutool.core.io.FileUtil;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author cn-src
 */
public interface RequestUtils {
    /**
     * 判断文件是否是 Excel 文件。
     *
     * @param file MultipartFile
     *
     * @return true 表示是 Excel 文件。
     */
    static boolean isExcel(final MultipartFile file) {

        if (null == file || file.isEmpty()) {
            return false;
        }
        final String suffix = FileUtil.getSuffix(file.getOriginalFilename());
        if (null == suffix) {
            return false;
        }
        return "xls".equalsIgnoreCase(suffix) ||
            "xlsx".equalsIgnoreCase(suffix) ||
            "xlsm".equalsIgnoreCase(suffix);
    }
}