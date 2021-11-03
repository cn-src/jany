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