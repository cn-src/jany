package cn.javaer.jany.spring.web.exception;

import cn.javaer.jany.exception.ErrorInfo;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * @author cn-src
 */
public class ErrorMessageSource extends ResourceBundleMessageSource {

    @SuppressWarnings("AlibabaConstantFieldShouldBeUpperCase")
    private static final ExpressionParser elParser = new SpelExpressionParser();

    private static final MessageSourceAccessor ACCESSOR =
        new MessageSourceAccessor(new ErrorMessageSource(), Locale.CHINESE);

    public ErrorMessageSource() {
        this.setDefaultEncoding("UTF-8");
        try {
            final ClassLoader classLoader = getBundleClassLoader() == null ?
                Thread.currentThread().getContextClassLoader() : getBundleClassLoader();
            ResourceBundle.getBundle("errors-messages", Locale.CHINESE, classLoader);
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
        final String message = getMessage(errorInfo.getError());
        if (StringUtils.hasText(message)) {
            return message;
        }
        return HttpStatus.valueOf(errorInfo.getStatus()).getReasonPhrase();
    }

    public static String getMessage(final String error, final Object[] args) {
        return ACCESSOR.getMessage(error, args);
    }

    public static String getMessage(final ErrorInfo errorInfo, final Throwable t) {
        final String messageExpression = ACCESSOR.getMessage(errorInfo.getError());
        final EvaluationContext context = new StandardEvaluationContext();
        context.setVariable("e", t);
        return elParser.parseExpression(messageExpression).getValue(context, String.class);
    }

    public static String getMessage(int status, String defaultMessage) {
        return ACCESSOR.getMessage(String.valueOf(status), defaultMessage);
    }

    public static String getMessage(String error) {
        try {
            return ACCESSOR.getMessage(error);
        }
        catch (NoSuchMessageException e) {
            return "";
        }
    }
}