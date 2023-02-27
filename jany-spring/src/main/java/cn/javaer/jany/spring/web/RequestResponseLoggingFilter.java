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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;

/**
 * 记录请求和响应详情的日志，一般用于调试场景，请使用 spring profile 机制启用，
 * 并确保当前类日志级别为 debug 级别.
 *
 * @author cn-src
 * @see org.springframework.web.filter.AbstractRequestLoggingFilter
 */
public class RequestResponseLoggingFilter extends OncePerRequestFilter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ObjectMapper objectMapper;

    public RequestResponseLoggingFilter() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    public RequestResponseLoggingFilter(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doFilterInternal(
        final @NotNull HttpServletRequest request,
        final @NotNull HttpServletResponse response,
        final @NotNull FilterChain filterChain)
        throws ServletException, IOException {

        final boolean isFirstRequest = !this.isAsyncDispatch(request);
        HttpServletRequest requestToUse = request;

        if (isFirstRequest && !(request instanceof ContentCachingRequestWrapper)) {
            requestToUse = new ContentCachingRequestWrapper(request, 2000);
        }

        HttpServletResponse responseToUse = response;
        if (!(response instanceof ContentCachingResponseWrapper)) {
            responseToUse = new ContentCachingResponseWrapper(response);
        }
        try {
            filterChain.doFilter(requestToUse, responseToUse);
        }
        finally {
            if (isFirstRequest) {
                this.beforeRequest(requestToUse);
            }
            if (!this.isAsyncStarted(requestToUse)) {
                this.afterResponse(responseToUse);
            }
        }
    }

    protected void beforeRequest(final HttpServletRequest request) {
        // path
        final StringBuilder msg = new StringBuilder();
        msg.append("Request\n");
        msg.append(request.getMethod().toUpperCase()).append(' ');
        msg.append(request.getRequestURI());

        final String queryString = request.getQueryString();
        if (queryString != null) {
            msg.append('?').append(queryString);
        }
        msg.append('\n');

        // header
        final Enumeration<String> names = request.getHeaderNames();
        while (names.hasMoreElements()) {
            final String header = names.nextElement();
            msg.append(header).append(':').append(' ').append(request.getHeader(header)).append('\n');
        }

        // body
        final ContentCachingRequestWrapper wrapper =
            WebUtils.getNativeRequest(request, ContentCachingRequestWrapper.class);
        if (wrapper != null) {
            final byte[] buf = wrapper.getContentAsByteArray();
            if (buf.length > 0) {
                try {
                    msg.append(new String(buf, wrapper.getCharacterEncoding()));
                }
                catch (final UnsupportedEncodingException ex) {
                    msg.append("[unknown]");
                }
            }
        }

        this.logger.debug(msg.toString());
    }

    protected void afterResponse(final HttpServletResponse response) throws IOException {
        final ContentCachingResponseWrapper wrapper =
            WebUtils.getNativeResponse(response, ContentCachingResponseWrapper.class);
        if (null != wrapper) {
            if (wrapper.getContentSize() > 10 * 1024 * 1024) {
                this.logger.debug("Response Content Size: " + wrapper.getContentSize());
                return;
            }
            final byte[] data = wrapper.getContentAsByteArray();
            if (data.length > 0) {
                wrapper.copyBodyToResponse();
                if (wrapper.getHeader(HttpHeaders.CONTENT_TYPE).contains(MediaType.APPLICATION_JSON_VALUE)) {
                    this.logger.debug("Response\n" + this.jsonFormat(new String(data,
                        StandardCharsets.UTF_8)));
                }
                else {
                    this.logger.debug("Response Content:\n" +
                        new String(data, StandardCharsets.UTF_8));
                }
                return;
            }
        }
        this.logger.debug("Response nothing");
    }

    private String jsonFormat(final String jsonStr) throws JsonProcessingException {
        final Object json = this.objectMapper.readValue(jsonStr, Object.class);
        return this.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
    }
}