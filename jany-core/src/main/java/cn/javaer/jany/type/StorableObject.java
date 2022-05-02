package cn.javaer.jany.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Value;
import lombok.With;

import java.beans.ConstructorProperties;

/**
 * 对象存储。
 *
 * @author cn-src
 */
@Value
@With
public class StorableObject {

    @Schema(description = "名称", required = true)
    String name;

    @Schema(description = "url", required = true)
    String url;

    @Schema(description = "类型", required = true)
    String type;

    @Schema(description = "路径")
    String path;

    @Schema(description = "存储方式")
    String channel;

    @JsonCreator
    @ConstructorProperties({"name", "url", "type", "path", "channel"})
    public StorableObject(String name, String url, String type, String path, String channel) {
        this.name = name;
        this.url = url;
        this.type = type;
        this.path = path;
        this.channel = channel;
    }
}