package cn.javaer.jany.storage.minio;

import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.http.Method;

import java.time.LocalDateTime;

/**
 * @author cn-src
 */
public class MinioServiceImpl implements MinioService {

    private final MinioClient minioClient;

    private final BucketConfig bucketConfig;

    public MinioServiceImpl(MinioClient minioClient, BucketConfig bucketConfig) {
        this.minioClient = minioClient;
        this.bucketConfig = bucketConfig;
    }

    @Override
    public String presignedGetUrl(String objectName) {
        try {
            return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                .method(Method.GET)
                .bucket(bucketConfig.getName())
                .object(objectName)
                .expiry(bucketConfig.getReadUrlExpiry())
                .build());
        }
        catch (Exception e) {
            throw new MinioException(e);
        }
    }

    @Override
    public String presignedPutUrl(String objectName) {
        try {
            return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                .method(Method.PUT)
                .bucket(bucketConfig.getName())
                .object(objectName)
                .expiry(bucketConfig.getWriteUrlExpiry())
                .build());
        }
        catch (Exception e) {
            throw new MinioException(e);
        }
    }

    @Override
    public String presignedDeleteUrl(String objectName) {
        try {
            return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                .method(Method.DELETE)
                .bucket(bucketConfig.getName())
                .object(objectName)
                .expiry(bucketConfig.getWriteUrlExpiry())
                .build());
        }
        catch (Exception e) {
            throw new MinioException(e);
        }
    }

    @Override
    public PresignedObject createPresignedObject(String objectName) {
        try {
            final GetPresignedObjectUrlArgs args = GetPresignedObjectUrlArgs.builder()
                .method(Method.PUT)
                .bucket(bucketConfig.getName())
                .object(objectName)
                .expiry(bucketConfig.getReadUrlExpiry())
                .build();
            final String url = minioClient.getPresignedObjectUrl(args);
            return new PresignedObject(objectName, url,
                LocalDateTime.now().plusSeconds(bucketConfig.getReadUrlExpiry()));
        }
        catch (Exception e) {
            throw new MinioException(e);
        }
    }
}