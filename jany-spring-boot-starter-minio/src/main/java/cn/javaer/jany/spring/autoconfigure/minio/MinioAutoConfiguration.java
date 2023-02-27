/*
 * Copyright 2020-2023 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.javaer.jany.spring.autoconfigure.minio;

import cn.javaer.jany.storage.minio.MinioService;
import cn.javaer.jany.storage.minio.MinioServiceImpl;
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