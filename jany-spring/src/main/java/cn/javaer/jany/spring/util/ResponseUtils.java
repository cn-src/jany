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

import com.deepoove.poi.XWPFTemplate;
import org.dromara.hutool.core.compress.ZipUtil;
import org.dromara.hutool.poi.excel.ExcelWriter;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * @author cn-src
 */
public interface ResponseUtils {

    /**
     * 进行 GZIP压缩，并以 GZIP 响应.
     *
     * @param body body
     * @return bytes
     */
    static ResponseEntity<byte[]> jsonGzipCompress(@NonNull final String body) {

        return jsonGzip(ZipUtil.gzip(body, StandardCharsets.UTF_8));
    }

    /**
     * GZIP 响应.
     *
     * @param body body
     * @return bytes
     */
    static ResponseEntity<byte[]> jsonGzip(final byte[] body) {

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.CONTENT_ENCODING, "gzip")
                .body(body);
    }

    /**
     * 资源响应，文件下载.
     *
     * @param resource 资源
     * @return 资源
     */
    static ResponseEntity<Resource> resource(@NonNull final Resource resource) {
        return resource(Objects.requireNonNull(resource.getFilename()), resource);
    }

    /**
     * 资源响应，文件下载.
     *
     * @param resource 资源
     * @param filename 文件名
     * @return 资源
     */
    static ResponseEntity<Resource> resource(@NonNull final String filename,
                                             @NonNull final Resource resource) {
        Assert.notNull(resource, () -> "Resource must be not null");
        Assert.notNull(filename, () -> "Filename must be not null");

        final String file;
        file = URLEncoder.encode(filename, StandardCharsets.UTF_8).replace("\\+", "%20");

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + file + "\"")
                .body(resource);
    }

    /**
     * 资源响应，文件下载，基于 hutool ExcelWriter，会关闭 ExcelWriter 流.
     *
     * @param excelWriter hutool ExcelWriter
     * @param filename    文件名
     * @return 资源
     */
    static ResponseEntity<Resource> resource(@NonNull final String filename,
                                             @NonNull final ExcelWriter excelWriter) {
        Assert.notNull(excelWriter, () -> "ExcelWriter must be not null");
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        excelWriter.flush(out);
        excelWriter.close();
        return resource(filename, new ByteArrayResource(out.toByteArray()));
    }

    static ResponseEntity<Resource> resource(@NonNull final String filename,
                                             @NonNull final ByteArrayOutputStream output) {
        Assert.notNull(output, () -> "ByteArrayOutputStream must be not null");
        return resource(filename, new ByteArrayResource(output.toByteArray()));
    }

    /**
     * 资源响应，文件下载，基于 poi-tl XWPFTemplate，会关闭 ExcelWriter 流.
     *
     * @param template poi-tl XWPFTemplate
     * @param filename 文件名
     * @return 资源
     */
    static ResponseEntity<Resource> resource(@NonNull final String filename,
                                             @NonNull final XWPFTemplate template) {
        Assert.notNull(template, () -> "XWPFTemplate must be not null");
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        try (template) {
            template.write(out);
        }
        catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        return resource(filename, new ByteArrayResource(out.toByteArray()));
    }

    /**
     * 资源响应，文件下载.
     *
     * @param in       InputStream
     * @param filename 文件名
     * @return 资源
     */
    static ResponseEntity<Resource> resource(@NonNull final String filename,
                                             @NonNull final InputStream in) {
        Assert.notNull(in, () -> "InputStream must be not null");
        return resource(filename, new InputStreamResource(in));
    }
}