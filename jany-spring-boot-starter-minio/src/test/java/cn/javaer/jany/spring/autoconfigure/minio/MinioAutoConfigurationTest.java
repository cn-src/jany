package cn.javaer.jany.spring.autoconfigure.minio;

import cn.javaer.jany.spring.autoconfigure.minio.empty.EmptyConfig;
import cn.javaer.jany.spring.autoconfigure.minio.test.MinioTest;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ContextConfiguration;

/**
 * @author cn-src
 */
@MinioTest
@ContextConfiguration(classes = {EmptyConfig.class})
class MinioAutoConfigurationTest {

    private MinioService minioService;

    @Test
    void name() {
        System.out.println(minioService.generateGetUrl("demo"));
    }
}