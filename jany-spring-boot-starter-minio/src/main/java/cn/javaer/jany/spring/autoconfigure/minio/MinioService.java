package cn.javaer.jany.spring.autoconfigure.minio;

/**
 * @author cn-src
 */
public interface MinioService {
    String getPresignedObjectUrl(String objectName);
}