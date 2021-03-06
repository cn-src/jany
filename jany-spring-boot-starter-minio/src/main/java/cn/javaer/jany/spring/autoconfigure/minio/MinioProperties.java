package cn.javaer.jany.spring.autoconfigure.minio;

import cn.javaer.jany.storage.minio.BucketConfig;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * @author cn-src
 */
@Data
@ConfigurationProperties(prefix = "jany.minio")
public class MinioProperties {

    private boolean enabled = true;
    
    private String endpoint;

    private String accessKey;

    private String secretKey;

    private BucketConfig defaultBucket;

    private Map<String, BucketConfig> buckets;
}