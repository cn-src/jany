package cn.javaer.jany.spring.web.exception;

import cn.javaer.jany.exception.ErrorInfo;

/**
 * 自定义异常信息提供器。
 *
 * @author cn-src
 */
public interface ErrorInfoProvider {

    /**
     * 根据异常，提供错误信息。
     *
     * @param t Throwable
     *
     * @return ErrorInfo
     */
    ErrorInfo getErrorInfo(Throwable t);
}