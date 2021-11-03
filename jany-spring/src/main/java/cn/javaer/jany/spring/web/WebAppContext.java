package cn.javaer.jany.spring.web;

import com.yomahub.tlog.context.TLogContext;

/**
 * @author cn-src
 */
public interface WebAppContext {
    String REQUEST_ID_PARAM = "requestId";

//    /**
//     * 设置请求 id.
//     */
//    static void setRequestId() {
//        MDC.put(REQUEST_ID_PARAM, IdUtil.fastSimpleUUID());
//    }
//

    /**
     * 回去请求 id.
     *
     * @return the request id
     */
    static String getRequestId() {
        return TLogContext.getTraceId();
    }
}