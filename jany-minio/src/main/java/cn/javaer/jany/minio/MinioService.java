package cn.javaer.jany.minio;

/**
 * The interface Minio service.
 *
 * @author cn -src
 */
public interface MinioService {
    /**
     * 生成预签名 get url.
     *
     * @param objectName the object name
     *
     * @return string
     */
    String presignedGetUrl(String objectName);

    /**
     * 生成预签名 put url.
     *
     * @param objectName the object name
     *
     * @return the string
     */
    String presignedPutUrl(String objectName);

    /**
     * 生成预签名 delete url.
     *
     * @param objectName the object name
     *
     * @return the string
     */
    String presignedDeleteUrl(String objectName);

    /**
     * Create presigned object.
     *
     * @param objectName the object name
     *
     * @return the presigned object
     */
    PresignedObject createPresignedObject(String objectName);
}