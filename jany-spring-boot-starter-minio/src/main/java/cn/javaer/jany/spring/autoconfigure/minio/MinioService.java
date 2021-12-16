package cn.javaer.jany.spring.autoconfigure.minio;

/**
 * @author cn-src
 */
public interface MinioService {
    String presignedGetUrl(String objectName);

    String presignedPutUrl(String objectName);

    String presignedDeleteUrl(String objectName);
}