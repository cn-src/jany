package cn.javaer.jany.spring.autoconfigure.jackson;

import cn.javaer.jany.util.TimeUtils;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author cn-src
 */
@Data
@ConfigurationProperties(prefix = "jany.jackson")
public class JacksonPlusProperties {

    private boolean enabled = true;

    private Format format = new Format();

    @Data
    public static class Format {

        /**
         * Date format to use, for example `yyyy-MM-dd`.
         */
        private String date = TimeUtils.DATE_PATTERN;

        /**
         * Time format to use, for example `HH:mm:ss`.
         */
        private String time = TimeUtils.TIME_PATTERN;

        /**
         * Date-time format to use, for example `yyyy-MM-dd HH:mm:ss`.
         */
        private String dateTime = TimeUtils.DATE_TIME_PATTERN;
    }
}