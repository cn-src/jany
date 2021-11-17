package cn.javaer.jany.spring.web.exception;

import cn.javaer.jany.util.IoUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author cn-src
 */
@Tag(name = "系统")
@RestController
@RequestMapping
public class ErrorInfoController implements InitializingBean {

    private Map<String, String> errorsMap;

    @Operation(summary = "错误码表")
    @GetMapping("error_infos")
    public Map<String, String> errorInfos() {
        return errorsMap;
    }

    private void load(String resourceBundle) {
        final ClassPathResource resource = new ClassPathResource(resourceBundle);
        if (resource.exists()) {
            try {
                Properties props = IoUtils.readProperties(resource.getInputStream());
                for (String propertyName : props.stringPropertyNames()) {
                    if (propertyName.startsWith("RUNTIME_")) {
                        continue;
                    }
                    errorsMap.put(propertyName, ErrorMessageSource.getMessage(propertyName));
                }
            }
            catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }
    }

    @Override
    public void afterPropertiesSet() {
        errorsMap = new LinkedHashMap<>();
        load("default-errors-messages_zh.properties");
        load("errors-messages_zh.properties");
    }
}