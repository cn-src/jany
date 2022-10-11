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
        chineseName(DesensitizedUtil::chineseName),
        idCard(str -> DesensitizedUtil.idCardNum(str, 4, 4)),
        fixedPhone(DesensitizedUtil::fixedPhone),
        mobilePhone(DesensitizedUtil::mobilePhone),
        email(DesensitizedUtil::email),
        carLicense(DesensitizedUtil::carLicense),
        bankCard(DesensitizedUtil::bankCard),
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