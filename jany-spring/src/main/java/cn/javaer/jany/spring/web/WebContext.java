package cn.javaer.jany.spring.web;

import com.yomahub.tlog.context.TLogContext;

/**
 * @author cn-src
 */
public interface WebContext {
    String REQUEST_ID_PARAM = "requestId";

    /**
     * 回去请求 id.
     *
     * @return the request id
     */
    static String getRequestId() {
        return TLogContext.getTraceId();
    }
}