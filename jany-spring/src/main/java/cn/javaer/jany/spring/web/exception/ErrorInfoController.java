package cn.javaer.jany.spring.web.exception;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

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
        try {
            ResourceBundle defaultErrorsMessages = ResourceBundle.getBundle(resourceBundle);
            for (String error : defaultErrorsMessages.keySet()) {
                if (error.startsWith("RUNTIME_")) {
                    continue;
                }
                errorsMap.put(error, ErrorMessageSource.getMessage(error));
            }
        }
        catch (final MissingResourceException ignore) {
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        errorsMap = new LinkedHashMap<>();
        load("default-errors-messages");
        load("errors-messages");
    }
}