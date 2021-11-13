//package cn.javaer.jany.spring.exception;
//
//import java.lang.annotation.Documented;
//import java.lang.annotation.ElementType;
//import java.lang.annotation.Retention;
//import java.lang.annotation.RetentionPolicy;
//import java.lang.annotation.Target;
//
///**
// * @author cn-src
// */
//@Documented
//@Retention(RetentionPolicy.RUNTIME)
//@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
//public @interface Error {
//
//    String error();
//
//    int status() default 500;
//
//    String message() default "Internal Server Error";
//}