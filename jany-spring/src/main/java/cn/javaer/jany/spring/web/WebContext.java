package cn.javaer.jany.spring.web;

import cn.javaer.jany.util.Empty;
import cn.javaer.jany.util.ReflectionUtils;
import com.yomahub.tlog.context.TLogContext;

import java.util.function.Supplier;

/**
 * @author cn-src
 */
public class WebContext {

    String REQUEST_ID_PARAM = "requestId";

    private static Supplier<String> requestIdFun = Empty.SUPPLIER;

    static {
        ReflectionUtils.getClass("com.yomahub.tlog.context.TLogContext").ifPresent(aClass -> {
            requestIdFun = TLogContext::getTraceId;
        });
    }

    /**
     * 回去请求 id.
     *
     * @return the request id
     */
    static String getRequestId() {
        return requestIdFun.get();
    }
}