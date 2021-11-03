package cn.javaer.jany.spring.web.exception;

import cn.javaer.jany.spring.web.WebAppContext;
import cn.javaer.jany.spring.exception.RuntimeErrorInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;

/**
 * @author cn-src
 */
@ControllerAdvice
public class GlobalExceptionAdvice {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ErrorProperties errorProperties;
    private final ErrorInfoExtractor errorInfoExtractor;
    private final boolean includeMessage;

    public GlobalExceptionAdvice(final ErrorProperties errorProperties,
                                 final ErrorInfoExtractor errorInfoExtractor) {
        this.errorProperties = errorProperties;
        this.errorInfoExtractor = errorInfoExtractor;
        this.includeMessage =
            errorProperties.getIncludeMessage() == ErrorProperties.IncludeAttribute.ALWAYS;
    }

    @ResponseBody
    @ExceptionHandler({Exception.class})
    public ResponseEntity<RuntimeErrorInfo> handleBadRequestException(
        final HttpServletRequest request, final Exception e) {
        this.logger.error("", e);
        final RuntimeErrorInfo errorInfo = this.errorInfoExtractor.getRuntimeErrorInfo(
            e, this.includeMessage);
        this.fillInfo(errorInfo, request, e);
        return ResponseEntity.status(errorInfo.getStatus()).body(errorInfo);
    }

    private void fillInfo(final RuntimeErrorInfo runtimeErrorInfo,
                          final HttpServletRequest request,
                          final Exception e) {
        runtimeErrorInfo.setPath(request.getServletPath());
        runtimeErrorInfo.setTimestamp(LocalDateTime.now());
        runtimeErrorInfo.setRequestId(WebAppContext.getRequestId());
        // exception
        final String clazz = e.getClass().getName();
        if (this.errorProperties.isIncludeException()) {
            runtimeErrorInfo.setException(clazz);
        }

        if (ErrorProperties.IncludeAttribute.ALWAYS.equals(this.errorProperties.getIncludeStacktrace())) {
            runtimeErrorInfo.setTraceMessage(e.getMessage());
            final StringWriter stackTrace = new StringWriter();
            e.printStackTrace(new PrintWriter(stackTrace));
            stackTrace.flush();
            runtimeErrorInfo.setTrace(stackTrace.toString());
        }
    }
}