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