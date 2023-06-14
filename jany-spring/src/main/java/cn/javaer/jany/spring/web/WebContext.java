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

package cn.javaer.jany.spring.web;

import cn.hutool.extra.servlet.JakartaServletUtil;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import cn.javaer.jany.util.Empty;
import cn.javaer.jany.util.ReflectUtils;
import com.yomahub.tlog.context.TLogContext;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;
import java.util.function.Supplier;

/**
 * @author cn-src
 */
public class WebContext {

    private static Supplier<String> requestIdFun = Empty.supplier();

    static {
        ReflectUtils.getClass("com.yomahub.tlog.context.TLogContext")
                .ifPresent(aClass -> requestIdFun = TLogContext::getTraceId);
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
     * @return the ip
     * @see JakartaServletUtil#getClientIP(HttpServletRequest, java.lang.String...)
     */
    public static String clientIp(String... otherHeaderNames) {
        return JakartaServletUtil.getClientIP(httpServletRequest(), otherHeaderNames);
    }

    public static ClientInfo clientInfo() {
        ClientInfo clientInfo = new ClientInfo();
        HttpServletRequest request = httpServletRequest();

        String ip = JakartaServletUtil.getClientIP(httpServletRequest());
        clientInfo.setIp(ip);

        String userAgentStr = request.getHeader("User-Agent");
        UserAgent userAgent = UserAgentUtil.parse(userAgentStr);
        if (null != userAgentStr) {
            clientInfo.setBrowser(userAgent.getBrowser().getName());
            clientInfo.setOs(userAgent.getOs().getName());
        }
        return clientInfo;
    }
}