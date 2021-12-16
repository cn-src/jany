package cn.javaer.jany.minio;

import lombok.Data;

/**
 * @author cn-src
 */
@Data
public class BucketConfig {

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