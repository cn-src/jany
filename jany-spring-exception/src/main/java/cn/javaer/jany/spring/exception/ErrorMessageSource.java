package cn.javaer.jany.spring.exception;

import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ResourceBundleMessageSource;

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

    public static String getMessage(final String error) {
        return ACCESSOR.getMessage(error, Locale.CHINESE);
    }

    public static String getMessage(final String error, final String defaultMessage) {
        return ACCESSOR.getMessage(error, defaultMessage);
    }

    public static String getMessage(final String error, final Object[] args) {
        return ACCESSOR.getMessage(error, args, Locale.CHINESE);
    }

    public static DefinedErrorInfo replaceMessage(final DefinedErrorInfo errorInfo) {
        return errorInfo.withMessage(ACCESSOR.getMessage(errorInfo.getError(), Locale.CHINESE));
    }
}