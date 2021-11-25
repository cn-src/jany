package cn.javaer.jany.spring.autoconfigure.minio;

/**
 * @author cn-src
 */
public interface MinioService {
    String generateGetObjectUrl(String objectName);

    String generatePutObjectUrl(String objectName);

    String generateDeleteObjectUrl(String objectName);
}