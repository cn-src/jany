package cn.javaer.jany.spring.web;

import cn.hutool.extra.servlet.ServletUtil;
import cn.javaer.jany.util.Empty;
import cn.javaer.jany.util.ReflectionUtils;
import com.yomahub.tlog.context.TLogContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * @author cn-src
 */
public class WebContext {

    private static Supplier<String> requestIdFun = Empty.supplier();

    static {
        ReflectionUtils.getClass("com.yomahub.tlog.context.TLogContext").ifPresent(aClass -> {
            requestIdFun = TLogContext::getTraceId;
        });
    }

    /**
     * 获取 RequestId.
     *
     * @return the request id
     */
    public static String requestId() {
        return requestIdFun.get();
    }

    /**
     * 获取当前的 HttpServletRequest.
     *
     * @return the HttpServletRequest
     */
    public static HttpServletRequest httpServletRequest() {
        return Optional.ofNullable(RequestContextHolder.getRequestAttributes())
            .map(ServletRequestAttributes.class::cast)
            .map(ServletRequestAttributes::getRequest)
            .orElseThrow(IllegalStateException::new);
    }

    /**
     * 获取客户端 ip.
     *
     * @param otherHeaderNames 用于获取 IP 的其他 http header
     *
     * @return the ip
     *
     * @see ServletUtil#getClientIP(javax.servlet.http.HttpServletRequest, java.lang.String...)
     */
    public static String clientIp(String... otherHeaderNames) {
        return ServletUtil.getClientIP(httpServletRequest(), otherHeaderNames);
    }
}