package cn.javaer.jany.format;

import cn.hutool.core.util.DesensitizedUtil;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.function.Function;

/**
 * @author cn-src
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
public @interface Desensitized {

    Type type();

    enum Type {
        CHINESE_NAME(DesensitizedUtil::chineseName),
        ID_CARD(str -> DesensitizedUtil.idCardNum(str, 4, 4)),
        FIXED_PHONE(DesensitizedUtil::fixedPhone),
        MOBILE_PHONE(DesensitizedUtil::mobilePhone),
        EMAIL(DesensitizedUtil::email),
        CAR_LICENSE(DesensitizedUtil::carLicense),
        BANK_CARD(DesensitizedUtil::bankCard),
        ;

        private final Function<String, String> fn;

        Type(Function<String, String> fn) {
            this.fn = fn;
        }

        public Function<String, String> fn() {
            return fn;
        }
    }
}