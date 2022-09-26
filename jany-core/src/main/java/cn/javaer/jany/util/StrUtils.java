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
     * 如果字符串为空，则返回 false。否则，遍历字符串中的字符，如果其中任何一个是小写的，则返回 true 。
     *
     * @param str 要检查的字符串。
     *
     * @return 一个布尔值。
     */
    public static boolean containsLowerChar(String str) {
        if (isEmpty(str)) {
            return false;
        }
        final char[] chars = str.toCharArray();
        for (char c : chars) {
            if (Character.isLowerCase(c)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 如果字符串为空，则返回 false。否则，遍历字符串中的字符，如果其中任何一个是大写的，则返回 true 。
     *
     * @param str 要检查的字符串。
     *
     * @return 一个布尔值。
     */
    public static boolean containsUpperChar(String str) {
        if (isEmpty(str)) {
            return false;
        }
        final char[] chars = str.toCharArray();
        for (char c : chars) {
            if (Character.isUpperCase(c)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 检查一个字符串是否同时包含大写和小写字符。
     *
     * @param str 要检查的字符串。
     *
     * @return 如果字符串同时包含大写和小写字符，则为 true 。
     */
    public static boolean containsCaseChar(String str) {
        if (isEmpty(str)) {
            return false;
        }
        boolean hasLower = false;
        boolean hasUpper = false;
        final char[] chars = str.toCharArray();
        for (char c : chars) {
            if (hasLower && hasUpper) {
                return true;
            }
            if (Character.isLowerCase(c)) {
                hasLower = true;
                continue;
            }
            if (Character.isUpperCase(c)) {
                hasUpper = true;
                // continue;
            }
        }
        return false;
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