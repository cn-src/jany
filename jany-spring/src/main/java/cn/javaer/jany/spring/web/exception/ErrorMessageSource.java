package cn.javaer.jany.spring.web.exception;

import cn.javaer.jany.exception.ErrorInfo;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * @author cn-src
 */
public class ErrorMessageSource extends ResourceBundleMessageSource {

    private static final MessageSourceAccessor ACCESSOR =
        new MessageSourceAccessor(new ErrorMessageSource());

    public ErrorMessageSource() {
        this.setDefaultEncoding("UTF-8");
        try {
            ResourceBundle.getBundle("errors-messages");
            this.setBasenames("errors-messages", "default-errors-messages");
        }
        catch (final MissingResourceException ignore) {
            this.setBasenames("default-errors-messages");
        }
    }

    public static MessageSourceAccessor getAccessor() {
        return ACCESSOR;
    }

    public static String getMessage(final ErrorInfo errorInfo) {
        final String message = ACCESSOR.getMessage(errorInfo.getError(), Locale.CHINESE);
        if (StringUtils.hasText(message)) {
            return message;
        }
        return HttpStatus.valueOf(errorInfo.getStatus()).getReasonPhrase();
    }

    public static String getMessage(final String error, final Object[] args) {
        return ACCESSOR.getMessage(error, args, Locale.CHINESE);
    }
}