package cn.javaer.jany.spring.security;

import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 在未登录或者权限不足等情况下的处理，默认情况下 ajax 请求结果会出现跳转页面。
 * <p>
 * <pre>
 * protected void configure(final HttpSecurity http) throws Exception {
 *    http.exceptionHandling()
 *    .authenticationEntryPoint(new ForbiddenEntryPoint("/login.html"))
 * }
 * </pre>
 *
 * @author cn-src
 */
public class ForbiddenEntryPoint implements AuthenticationEntryPoint {

    private String redirectUrl;

    public ForbiddenEntryPoint() {
    }

    public ForbiddenEntryPoint(final String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    @Override
    public void commence(final HttpServletRequest request, final HttpServletResponse response,
                         final AuthenticationException authException) throws IOException {
        final String xRequested = request.getHeader("X-Requested-With");
        final String accept = request.getHeader("Accept");
        final boolean isAjax = (accept != null && accept.contains(MediaType.APPLICATION_JSON_VALUE))
            || "XMLHttpRequest".equals(xRequested);
        if (isAjax) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write("{\"error\":\"FORBIDDEN\"}");
        }
        else if (StringUtils.hasLength(this.redirectUrl)) {
            response.sendRedirect(this.redirectUrl);
        }
        else {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
        }
    }
}