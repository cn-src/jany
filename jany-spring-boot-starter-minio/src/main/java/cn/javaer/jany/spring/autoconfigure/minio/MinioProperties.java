package cn.javaer.jany.spring.autoconfigure.minio;

import lombok.Data;
import lombok.NonNull;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

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
    private BucketConfig defaultBucket;

    private Map<String, BucketConfig> buckets;

    @Data
    public static class BucketConfig {

        private String name;

        /**
         * 默认 10 分钟
         */
        private int readUrlExpiry = 10 * 60;

        /**
         * 默认 10 分钟
         */
        private int writeUrlExpiry = 10 * 60;
    }
}