package cn.javaer.jany.spring.autoconfigure.minio;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * @author cn-src
 */
@Data
@ConfigurationProperties(prefix = "jany.minio")
public class MinioProperties {

    private String endpoint;

    private String accessKey;

    private String secretKey;

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