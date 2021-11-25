package cn.javaer.jany.spring.autoconfigure.minio;

import lombok.Data;
import lombok.NonNull;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author cn-src
 */
@Data
@ConfigurationProperties(prefix = "jany.minio")
public class MinioProperties {
    @NonNull
    private String endpoint;
    @NonNull
    private String accessKey;
    @NonNull
    private String secretKey;
    @NonNull
    private String defaultBucket;
    private int expiry = 10 * 60;
}