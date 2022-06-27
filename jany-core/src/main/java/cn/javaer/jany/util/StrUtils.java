package cn.javaer.jany.util;

import cn.hutool.core.util.StrUtil;

/**
 * @author cn-src
 */
public class StrUtils extends StrUtil {

    /**
     * 如果对象是字符串，则返回它，否则抛出异常。
     *
     * @param obj 要转换为字符串的对象。
     *
     * @return 一个字符串
     */
    public static String cast(Object obj) {
        if (null == obj) {
            return null;
        }
        if (obj instanceof String) {
            return (String) obj;
        }
        throw new IllegalArgumentException("Object must be String, but was " + obj.getClass());
    }

    /**
     * 将驼峰字符串转换成下划线大写格式.
     *
     * @param str String
     *
     * @return String
     */
    public static String toSnakeUpper(final String str) {
        if (null == str || str.isEmpty()) {
            return str;
        }
        final StringBuilder sb = new StringBuilder();
        final char[] chars = str.toCharArray();
        sb.append(Character.toUpperCase(chars[0]));
        for (int i = 1, le = chars.length; i < le; i++) {
            final boolean isUpAndPrevNotLine = Character.isUpperCase(chars[i])
                && '_' != chars[i - 1];

            final boolean isPrevLowOrNotLetter =
                Character.isLowerCase(chars[i - 1]) || !Character.isLetter(chars[i - 1]);

            if (isUpAndPrevNotLine && isPrevLowOrNotLetter) {
                sb.append('_').append(chars[i]);
                continue;
            }

            sb.append(Character.toUpperCase(chars[i]));
        }
        return sb.toString();
    }

    /**
     * 将驼峰字符串转换成下划线小写格式.
     *
     * @param str String
     *
     * @return String
     */
    public static String toSnakeLower(final String str) {
        if (null == str || str.isEmpty()) {
            return str;
        }
        final StringBuilder sb = new StringBuilder();
        final char[] chars = str.toCharArray();
        sb.append(Character.toLowerCase(chars[0]));
        for (int i = 1, le = chars.length; i < le; i++) {
            final boolean isUpAndPrevNotLine = Character.isUpperCase(chars[i])
                && '_' != chars[i - 1];

            final boolean isPrevLowOrNotLetter =
                Character.isLowerCase(chars[i - 1]) || !Character.isLetter(chars[i - 1]);

            if (isUpAndPrevNotLine && isPrevLowOrNotLetter) {
                sb.append('_').append(Character.toLowerCase(chars[i]));
                continue;
            }

            sb.append(Character.toLowerCase(chars[i]));
        }
        return sb.toString();
    }
}