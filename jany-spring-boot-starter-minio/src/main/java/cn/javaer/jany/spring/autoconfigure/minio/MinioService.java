package cn.javaer.jany.spring.autoconfigure.minio;

/**
 * @author cn-src
 */
public interface MinioService {
    String generateGetUrl(String objectName);

    String generatePutUrl(String objectName);

    String generateDeleteUrl(String objectName);
}