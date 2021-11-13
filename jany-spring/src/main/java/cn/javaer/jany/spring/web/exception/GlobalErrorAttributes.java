package cn.javaer.jany.spring.web.exception;

import cn.javaer.jany.exception.ErrorInfo;
import cn.javaer.jany.exception.RuntimeErrorInfo;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author cn-src
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalErrorAttributes extends DefaultErrorAttributes {
    private final ErrorInfoExtractor extractor;

    public GlobalErrorAttributes(ErrorInfoExtractor extractor) {
        this.extractor = extractor;
    }

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest,
                                                  ErrorAttributeOptions options) {
        final Throwable throwable = super.getError(webRequest);
        final ErrorInfo errorInfo = extractor.getErrorInfo(throwable);
        final Map<String, Object> attributes = super.getErrorAttributes(webRequest, options);
        attributes.put(RuntimeErrorInfo.Fields.traceMessage, attributes.get("message"));
        attributes.put(RuntimeErrorInfo.Fields.error, errorInfo.getError());
        attributes.put(RuntimeErrorInfo.Fields.status, errorInfo.getStatus());
        attributes.put(RuntimeErrorInfo.Fields.timestamp, LocalDateTime.now());
        return attributes;
    }
}