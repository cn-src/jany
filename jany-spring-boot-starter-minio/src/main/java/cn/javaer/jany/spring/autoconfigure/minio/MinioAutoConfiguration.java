package cn.javaer.jany.spring.autoconfigure.minio;

import cn.javaer.jany.minio.MinioService;
import cn.javaer.jany.minio.MinioServiceImpl;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @author cn-src
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({MinioClient.class})
@ConditionalOnProperty(prefix = "jany.minio", name = "enabled", havingValue = "true",
    matchIfMissing = true)
@EnableConfigurationProperties(MinioProperties.class)
public class MinioAutoConfiguration {

    @Bean
    @Primary
    @ConditionalOnMissingBean(MinioClient.class)
    public MinioClient minioClient(final MinioProperties minioProperties) {

        return MinioClient.builder()
            .endpoint(minioProperties.getEndpoint())
            .credentials(minioProperties.getAccessKey(), minioProperties.getSecretKey())
            .build();
    }

    @Bean
    @Primary
    @ConditionalOnMissingBean(MinioService.class)
    public MinioService minioService(final MinioClient minioClient,
                                     final MinioProperties minioProperties) {
        try {
            final BucketExistsArgs.Builder args = BucketExistsArgs.builder()
                .bucket(minioProperties.getDefaultBucket().getName());
            if (!minioClient.bucketExists(args.build())) {
                minioClient.makeBucket(MakeBucketArgs.builder()
                    .bucket(minioProperties.getDefaultBucket().getName()).build());
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new MinioServiceImpl(minioClient, minioProperties.getDefaultBucket());
    }
}