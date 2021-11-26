package cn.javaer.jany.spring.autoconfigure.minio;

import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.http.Method;

/**
 * @author cn-src
 */
public class MinioServiceImpl implements MinioService {

    private final MinioClient minioClient;
    private final MinioProperties minioProperties;

    public MinioServiceImpl(MinioClient minioClient, MinioProperties minioProperties) {
        this.minioClient = minioClient;
        this.minioProperties = minioProperties;
    }

    @Override
    public String generateGetUrl(String objectName) {
        try {
            return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                .method(Method.GET)
                .bucket(minioProperties.getDefaultBucket().getName())
                .object(objectName)
                .expiry(minioProperties.getDefaultBucket().getReadUrlExpiry())
                .build());
        }
        catch (Exception e) {
            throw new MinioException(e);
        }
    }

    @Override
    public String generatePutUrl(String objectName) {
        try {
            return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                .method(Method.PUT)
                .bucket(minioProperties.getDefaultBucket().getName())
                .object(objectName)
                .expiry(minioProperties.getDefaultBucket().getWriteUrlExpiry())
                .build());
        }
        catch (Exception e) {
            throw new MinioException(e);
        }
    }

    @Override
    public String generateDeleteUrl(String objectName) {
        try {
            return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                .method(Method.DELETE)
                .bucket(minioProperties.getDefaultBucket().getName())
                .object(objectName)
                .expiry(minioProperties.getDefaultBucket().getWriteUrlExpiry())
                .build());
        }
        catch (Exception e) {
            throw new MinioException(e);
        }
    }
}