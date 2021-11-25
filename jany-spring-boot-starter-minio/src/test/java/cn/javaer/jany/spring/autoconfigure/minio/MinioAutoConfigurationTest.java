package cn.javaer.jany.spring.autoconfigure.minio;

import cn.javaer.jany.spring.autoconfigure.minio.test.MinioTest;
import org.junit.jupiter.api.Test;

/**
 * @author cn-src
 */
@MinioTest
class MinioAutoConfigurationTest {

    private MinioService minioService;

    @Test
    void name() {
        System.out.println(minioService.getPresignedObjectUrl("demo"));
    }
}