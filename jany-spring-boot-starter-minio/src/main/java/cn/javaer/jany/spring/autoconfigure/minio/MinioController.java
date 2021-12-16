package cn.javaer.jany.spring.autoconfigure.minio;

import cn.javaer.jany.minio.MinioService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cn-src
 */
@RestController
@RequestMapping("minio")
public class MinioController {

    private final MinioService minioService;

    public MinioController(MinioService minioService) {
        this.minioService = minioService;
    }

    @GetMapping("presigned_urls")
    public String presignedGetUrl(@RequestParam String objectName) {
        return minioService.presignedGetUrl(objectName);
    }
}