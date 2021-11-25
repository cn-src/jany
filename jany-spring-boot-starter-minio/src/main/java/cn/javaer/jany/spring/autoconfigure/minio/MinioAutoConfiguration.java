package cn.javaer.jany.spring.autoconfigure.minio;

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
    @ConditionalOnMissingBean(MinioService.class)
    MinioService minioService(final MinioClient minioClient,
                              final MinioProperties minioProperties) {
        return new MinioServiceImpl(minioClient, minioProperties);
    }
}