package cn.javaer.jany.spring.util;

import cn.hutool.core.util.ZipUtil;
import cn.hutool.poi.excel.ExcelWriter;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Objects;

/**
 * @author cn-src
 */
public interface ResponseUtils {

    /**
     * 进行 GZIP压缩，并以 GZIP 响应.
     *
     * @param body body
     *
     * @return bytes
     */
    static ResponseEntity<byte[]> jsonGzipCompress(@NonNull final String body) {

        return jsonGzip(ZipUtil.gzip(body, "UTF-8"));
    }

    /**
     * GZIP 响应.
     *
     * @param body body
     *
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
     *
     * @return 资源
     */
    static ResponseEntity<Resource> resource(@NonNull final Resource resource) {
        return resource(resource, Objects.requireNonNull(resource.getFilename()));
    }

    /**
     * 资源响应，文件下载.
     *
     * @param resource 资源
     * @param filename 文件名
     *
     * @return 资源
     */
    static ResponseEntity<Resource> resource(@NonNull final Resource resource,
                                             @NonNull final String filename) {
        Assert.notNull(resource, () -> "Resource must be not null");
        Assert.notNull(filename, () -> "Filename must be not null");

        final String file;
        try {
            file = URLEncoder.encode(filename, "UTF-8");
        }
        catch (final UnsupportedEncodingException e) {
            throw new IllegalStateException(e);
        }

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
     * @param filename 文件名
     *
     * @return 资源
     */
    static ResponseEntity<Resource> resource(@NonNull final ExcelWriter excelWriter,
                                             @NonNull final String filename) {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        excelWriter.flush(out);
        excelWriter.close();
        return resource(new ByteArrayResource(out.toByteArray()), filename);
    }

    /**
     * 资源响应，文件下载.
     *
     * @param in InputStream
     * @param filename 文件名
     *
     * @return 资源
     */
    static ResponseEntity<Resource> resource(@NonNull final InputStream in,
                                             @NonNull final String filename) {
        return resource(new InputStreamResource(in), filename);
    }
}