package cn.javaer.jany.spring.web.exception;

import cn.javaer.jany.spring.exception.DefinedErrorInfo;
import cn.javaer.jany.spring.exception.ErrorMessageSource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author cn-src
 */
@Tag(name = "错误码")
@RestController
@RequestMapping
public class ErrorInfoController implements ApplicationContextAware, InitializingBean {

    private final TreeSet<DefinedErrorInfo> errorInfos = new TreeSet<>();

    private ApplicationContext applicationContext;
    private final ErrorInfoExtractor errorInfoExtractor;

    public ErrorInfoController(final ErrorInfoExtractor errorInfoExtractor) {
        this.errorInfoExtractor = errorInfoExtractor;
    }

    @Operation(summary = "错误码列表")
    @GetMapping("error_infos")
    public Set<DefinedErrorInfo> errorInfos() {
        return this.errorInfos;
    }

    @Override
    public void setApplicationContext(@NonNull final ApplicationContext applicationContext)
        throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterPropertiesSet() {

        // TreeSet 优先级
        final Collection<Object> controllers =
            this.applicationContext.getBeansWithAnnotation(Controller.class).values();

        final Map<String, DefinedErrorInfo> controllersErrorMapping =
            this.errorInfoExtractor.getControllersErrorMapping(controllers);
        if (!controllersErrorMapping.isEmpty()) {
            this.errorInfos.addAll(controllersErrorMapping.values());
        }

        for (final DefinedErrorInfo info :
            this.errorInfoExtractor.getConfiguredErrorMapping().values()) {
            final String message = ErrorMessageSource.getMessage(info.getError(),
                info.getMessage());
            this.errorInfos.add(info.withMessage(message));
        }
    }
}