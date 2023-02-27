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
     * 如果字符串为空，则返回 false。否则，遍历字符串中的每个字符，如果字符是数字则返回 true 。
     *
     * @param str 要检查的字符串。
     *
     * @return 一个布尔值。
     */
    public static boolean containsNumber(String str) {
        if (isEmpty(str)) {
            return false;
        }
        final char[] chars = str.toCharArray();
        for (char ch : chars) {
            if (ch >= '0' && ch <= '9') {
                return true;
            }
        }
        return false;
    }

    /**
     * 如果字符串为空，则返回 false。否则，遍历字符串中的每个字符，如果字符是字母则返回 true 。
     *
     * @param str 要检查的字符串。
     *
     * @return 一个布尔值。
     */
    public static boolean containsLetter(String str) {
        if (isEmpty(str)) {
            return false;
        }
        final char[] chars = str.toCharArray();
        for (char ch : chars) {
            // noinspection AlibabaAvoidComplexCondition
            if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z')) {
                return true;
            }
        }
        return false;
    }

    /**
     * 如果字符串为空，则返回 false。否则，遍历字符串中的字符，如果其中任何一个是小写的，则返回 true 。
     *
     * @param str 要检查的字符串。
     *
     * @return 一个布尔值。
     */
    public static boolean containsLower(String str) {
        if (isEmpty(str)) {
            return false;
        }
        final char[] chars = str.toCharArray();
        for (char ch : chars) {
            if (ch >= 'a' && ch <= 'z') {
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
    public static boolean containsUpper(String str) {
        if (isEmpty(str)) {
            return false;
        }
        final char[] chars = str.toCharArray();
        for (char ch : chars) {
            if (ch >= 'A' && ch <= 'Z') {
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
    public static boolean containsBothCase(String str) {
        if (isEmpty(str)) {
            return false;
        }
        boolean hasLower = false;
        boolean hasUpper = false;
        final char[] chars = str.toCharArray();
        for (char ch : chars) {
            if (hasLower && hasUpper) {
                return true;
            }
            if (ch >= 'a' && ch <= 'z') {
                hasLower = true;
                continue;
            }
            if (ch >= 'A' && ch <= 'Z') {
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