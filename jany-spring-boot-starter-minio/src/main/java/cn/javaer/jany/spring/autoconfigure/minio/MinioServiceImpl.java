package cn.javaer.jany.spring.autoconfigure.minio;

import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.http.Method;

/**
 * @author cn-src
 */
public class MinioServiceImpl {

    private final MinioClient minioClient;
    private final MinioProperties minioProperties;

    public MinioServiceImpl(MinioClient minioClient, MinioProperties minioProperties) {
        this.minioClient = minioClient;
        this.minioProperties = minioProperties;
    }

    public String getPresignedObjectUrl(String objectName) {
        try {
            return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                .method(Method.GET)
                .bucket(minioProperties.getDefaultBucket())
                .object(objectName)
                .expiry(minioProperties.getExpiry())
                .build());
        }
        catch (Exception e) {
            throw new MinioException(e);
        }
    }
}