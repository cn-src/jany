package cn.javaer.jany.spring.boot.diagnostics.analyzer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.diagnostics.FailureAnalysis;
import org.springframework.boot.diagnostics.FailureAnalysisReporter;
import org.springframework.util.StringUtils;

/**
 * @author cn-src
 */
public final class ZhLoggingFailureAnalysisReporter implements FailureAnalysisReporter {

    private static final Log logger = LogFactory.getLog(ZhLoggingFailureAnalysisReporter.class);

    @Override
    public void report(final FailureAnalysis failureAnalysis) {
        if (logger.isDebugEnabled()) {
            logger.debug("由于出现异常，应用程序无法启动",
                failureAnalysis.getCause());
        }
        if (logger.isErrorEnabled()) {
            logger.error(this.buildMessage(failureAnalysis));
        }
    }

    private String buildMessage(final FailureAnalysis failureAnalysis) {
        final StringBuilder builder = new StringBuilder();
        builder.append(String.format("%n%n"));
        builder.append(String.format("***************************%n"));
        builder.append(String.format("应用程序启动失败%n"));
        builder.append(String.format("***************************%n%n"));
        builder.append(String.format("描述:%n%n"));
        builder.append(String.format("%s%n", failureAnalysis.getDescription()));
        if (StringUtils.hasText(failureAnalysis.getAction())) {
            builder.append(String.format("%n操作:%n%n"));
            builder.append(String.format("%s%n", failureAnalysis.getAction()));
        }
        return builder.toString();
    }
}