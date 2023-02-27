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

package cn.javaer.jany.type;

import cn.hutool.core.io.FileUtil;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;
import lombok.With;

import java.beans.ConstructorProperties;

/**
 * 对象存储。
 *
 * @author cn-src
 */
@Value
@Builder
@With
public class StorableObject {

    @Schema(description = "文件名称", required = true)
    String filename;

    @Schema(description = "文件大小")
    Integer fileSize;

    @Schema(description = "文件访问 url")
    String accessUrl;

    @Schema(description = "文件类型")
    String type;

    @Schema(description = "文件路径", required = true)
    String fullPath;

    @Schema(description = "存储方式")
    String channel;

    @JsonCreator
    @ConstructorProperties({"filename", "fileSize", "accessUrl", "type", "fullPath", "channel"})
    StorableObject(String filename, Integer fileSize, String accessUrl, String type,
                   String fullPath, String channel) {
        this.filename = filename == null ? FileUtil.getName(fullPath) : filename;
        this.fileSize = fileSize;
        this.accessUrl = accessUrl;
        this.type = type;
        this.fullPath = fullPath;
        this.channel = channel;
    }
}