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

import org.springframework.boot.diagnostics.AbstractFailureAnalyzer;
import org.springframework.boot.diagnostics.FailureAnalysis;
import org.springframework.boot.web.server.PortInUseException;

/**
 * @author cn-src
 */
class ZhPortInUseFailureAnalyzer extends AbstractFailureAnalyzer<PortInUseException> {

    @Override
    protected FailureAnalysis analyze(final Throwable rootFailure, final PortInUseException cause) {
        return new FailureAnalysis("Web 服务器启动失败，端口 " + cause.getPort() + " 已被占用。",
            "请查找并停止使用端口 " + cause.getPort() + " 的进程，或者配置其它未使用的端口。",
            cause);
    }
}