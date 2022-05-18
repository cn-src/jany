package cn.javaer.jany.storage.minio;

import cn.hutool.core.lang.Assert;
import cn.javaer.jany.type.StorableObject;
import io.minio.CopyObjectArgs;
import io.minio.CopySource;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.http.Method;

import java.io.InputStream;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.UUID;

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
    public PresignedObject presignedObject(String objectName) {
        try {
            final GetPresignedObjectUrlArgs args = GetPresignedObjectUrlArgs.builder()
                .method(Method.PUT)
                .bucket(bucketConfig.getName())
                .object(objectName)
                .expiry(bucketConfig.getWriteUrlExpiry())
                .build();
            final String url = minioClient.getPresignedObjectUrl(args);
            return new PresignedObject(objectName, url,
                LocalDateTime.now().plusSeconds(bucketConfig.getWriteUrlExpiry()));
        }
        catch (Exception e) {
            throw new MinioException(e);
        }
    }

    @Override
    public StorableObject upload(String objectName, InputStream in) {
        Assert.notNull(in);
        Assert.notBlank(objectName);
        try {
            final int objectSize = in.available();
            minioClient.putObject(PutObjectArgs.builder()
                .bucket(bucketConfig.getName()).object(objectName)
                .stream(in, objectSize, -1)
                .build());
            return StorableObject.builder()
                .fullPath(objectName)
                .fileSize(objectSize)
                .channel("minio")
                .build();
        }
        catch (Exception e) {
            throw new MinioException(e);
        }
    }

    @Override
    public StorableObject uploadTmp(String filename, InputStream in) {
        final String objectName = Paths.get("tmp", UUID.randomUUID().toString(), filename)
            .toString();
        return upload(objectName, in);
    }

    public void copy(String sourceObject, String targetObject) {
        try {
            minioClient.copyObject(CopyObjectArgs.builder()
                .bucket(bucketConfig.getName())
                .object(targetObject)
                .source(
                    CopySource.builder()
                        .bucket(bucketConfig.getName())
                        .object(sourceObject)
                        .build())
                .build());
        }
        catch (Exception e) {
            throw new MinioException(e);
        }
    }

    public void move(String sourceObject, String targetObject) {
        copy(sourceObject, targetObject);
        delete(sourceObject);
    }

    public void delete(String objectName) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucketConfig.getName())
                .object(objectName).build());
        }
        catch (Exception e) {
            throw new MinioException(e);
        }
    }
}