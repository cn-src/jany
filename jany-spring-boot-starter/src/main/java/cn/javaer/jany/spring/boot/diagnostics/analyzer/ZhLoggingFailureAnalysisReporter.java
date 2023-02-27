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