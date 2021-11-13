//package cn.javaer.jany.spring.exception;
//
//import lombok.Data;
//import org.jetbrains.annotations.NotNull;
//
//import java.time.LocalDateTime;
//
///**
// * @author cn-src
// */
//@Data
//public class RuntimeErrorInfo {
//    @NotNull
//    private final String error;
//    private Integer status;
//    private String message;
//
//    private String path;
//    private String requestId;
//    private String exception;
//    private String trace;
//    private String traceMessage;
//    private LocalDateTime timestamp;
//
//    public RuntimeErrorInfo(final DefinedErrorInfo definedErrorInfo) {
//        this.error = definedErrorInfo.getError();
//        this.status = definedErrorInfo.getStatus();
//        this.message = definedErrorInfo.getMessage();
//    }
//}