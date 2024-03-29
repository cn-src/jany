/*
 * Copyright 2020-2023 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.javaer.jany.spring.web.exception;

import cn.javaer.jany.exception.ErrorInfo;
import org.dromara.hutool.core.text.StrUtil;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
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

    private static final char EL_KEY_START = '$';

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
        return "";
    }

    public static String getMessage(final String error, final Object[] args) {
        try {
            return ACCESSOR.getMessage(error, args);
        }
        catch (NoSuchMessageException e) {
            return "";
        }
    }

    public static String getMessage(final ErrorInfo errorInfo, final Throwable t) {
        if (EL_KEY_START != errorInfo.getError().charAt(0)) {
            return getMessage(errorInfo);
        }
        final String messageEl = getMessage(errorInfo.getError());
        if (StrUtil.isEmpty(messageEl)) {
            return "";
        }
        final EvaluationContext context = new StandardEvaluationContext();
        context.setVariable("e", t);
        final String value = elParser.parseExpression(messageEl).getValue(context, String.class);
        if (StrUtil.isBlank(value)) {
            return "";
        }
        return value;
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