package cn.javaer.jany.util;

import cn.javaer.jany.util.StrUtils;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author cn-src
 */
class StrUtilsTest {

    @Test
    void toSnakeUpper() {
        assertThat(StrUtils.toSnakeUpper(null)).isNull();
        assertThat(StrUtils.toSnakeUpper("")).isEmpty();
        assertThat(StrUtils.toSnakeUpper("s")).isEqualTo("S");
        assertThat(StrUtils.toSnakeUpper("S")).isEqualTo("S");
        assertThat(StrUtils.toSnakeUpper("_")).isEqualTo("_");
        assertThat(StrUtils.toSnakeUpper("1")).isEqualTo("1");
        assertThat(StrUtils.toSnakeUpper("SNAKE")).isEqualTo("SNAKE");
        assertThat(StrUtils.toSnakeUpper("snake")).isEqualTo("SNAKE");
        assertThat(StrUtils.toSnakeUpper("SnakeCase")).isEqualTo("SNAKE_CASE");
        assertThat(StrUtils.toSnakeUpper("Snake123")).isEqualTo("SNAKE123");
        assertThat(StrUtils.toSnakeUpper("Snake123SNAKE")).isEqualTo("SNAKE123_SNAKE");
        assertThat(StrUtils.toSnakeUpper("SNAKE_CASE")).isEqualTo("SNAKE_CASE");
        assertThat(StrUtils.toSnakeUpper("snake_case")).isEqualTo("SNAKE_CASE");
    }

    @Test
    void toSnakeLower() {
        assertThat(StrUtils.toSnakeLower(null)).isNull();
        assertThat(StrUtils.toSnakeLower("")).isEmpty();
        assertThat(StrUtils.toSnakeLower("s")).isEqualTo("s");
        assertThat(StrUtils.toSnakeLower("S")).isEqualTo("s");
        assertThat(StrUtils.toSnakeLower("_")).isEqualTo("_");
        assertThat(StrUtils.toSnakeLower("1")).isEqualTo("1");
        assertThat(StrUtils.toSnakeLower("SNAKE")).isEqualTo("snake");
        assertThat(StrUtils.toSnakeLower("snake")).isEqualTo("snake");
        assertThat(StrUtils.toSnakeLower("SnakeCase")).isEqualTo("snake_case");
        assertThat(StrUtils.toSnakeLower("Snake123")).isEqualTo("snake123");
        assertThat(StrUtils.toSnakeLower("Snake123SNAKE")).isEqualTo("snake123_snake");
        assertThat(StrUtils.toSnakeLower("SNAKE_CASE")).isEqualTo("snake_case");
        assertThat(StrUtils.toSnakeLower("snake_case")).isEqualTo("snake_case");
    }
}