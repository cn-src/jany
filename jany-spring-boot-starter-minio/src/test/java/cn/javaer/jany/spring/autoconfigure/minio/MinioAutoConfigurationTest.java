package cn.javaer.jany.spring.autoconfigure.minio;

import cn.javaer.jany.spring.autoconfigure.minio.empty.EmptyConfig;
import cn.javaer.jany.spring.autoconfigure.minio.test.MinioTest;
import cn.javaer.jany.storage.minio.MinioService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

/**
 * @author cn-src
 */
@MinioTest
@ContextConfiguration(classes = {EmptyConfig.class})
@Disabled
class MinioAutoConfigurationTest {

    @Autowired
    private MinioService minioService;

    @Test
    void name() {
        System.out.println(minioService.presignedGetUrl("demo"));
    }
}