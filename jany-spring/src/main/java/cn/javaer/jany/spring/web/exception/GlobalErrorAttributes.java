package cn.javaer.jany.spring.web.exception;

import cn.javaer.jany.exception.ErrorInfo;
import cn.javaer.jany.exception.RuntimeErrorInfo;
import cn.javaer.jany.spring.web.WebContext;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
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
        final Map<String, Object> attributes = super.getErrorAttributes(webRequest, options);
        attributes.put(RuntimeErrorInfo.Fields.traceMessage, attributes.get("message"));
        attributes.put(RuntimeErrorInfo.Fields.timestamp, LocalDateTime.now());
        attributes.put(RuntimeErrorInfo.Fields.requestId, WebContext.requestId());

        final Throwable throwable = super.getError(webRequest);
        if (throwable != null) {
            final ErrorInfo errorInfo = extractor.getErrorInfo(throwable);
            attributes.put(RuntimeErrorInfo.Fields.message,
                ErrorMessageSource.getMessage(errorInfo));
            attributes.put(RuntimeErrorInfo.Fields.error, errorInfo.getError());
            attributes.put(RuntimeErrorInfo.Fields.status, errorInfo.getStatus());
        }
        else {
            final int status = (Integer) attributes.get("status");
            final HttpStatus httpStatus = HttpStatus.resolve(status);
            if (httpStatus == null) {
                attributes.put(RuntimeErrorInfo.Fields.message, "No message");
                attributes.put(RuntimeErrorInfo.Fields.error, "UNKNOWN");
            }
            else {
                attributes.put(RuntimeErrorInfo.Fields.message, httpStatus.getReasonPhrase());
                attributes.put(RuntimeErrorInfo.Fields.error, httpStatus.name());
            }
        }
        return attributes;
    }
}