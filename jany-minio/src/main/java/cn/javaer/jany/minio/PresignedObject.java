package cn.javaer.jany.minio;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Value;

import java.time.LocalDateTime;

/**
 * @author cn-src
 */
@Value
public class PresignedObject {

    @Schema(description = "对象名称")
    String objectName;

    @Schema(description = "对象预签名 url")
    String presignedUrl;

    @Schema(description = "url 到期时间")
    LocalDateTime expiryTime;
}