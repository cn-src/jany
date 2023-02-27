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

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;

/**
 * @author cn-src
 */
public class ObjectUtils extends ObjectUtil {

    /**
     * 如果源对象与所有对象都相等，则返回 true
     *
     * @param src 源对象。
     * @param tars 要比较的目标对象。
     *
     * @return boolean。
     */
    public static boolean allEquals(Object src, Object... tars) {
        if (src == null) {
            return false;
        }
        if (ArrayUtil.isEmpty(tars)) {
            return false;
        }
        for (Object tar : tars) {
            if (!src.equals(tar)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 如果源对象不与所有对象都相等，则返回 true
     *
     * @param src 源对象。
     * @param tars 要比较的目标对象。
     *
     * @return boolean。
     */
    public static boolean notAllEquals(Object src, Object... tars) {
        return !allEquals(src, tars);
    }

    /**
     * 如果源对象等于任何目标对象，则返回 true
     *
     * @param src 源对象。
     * @param tars 要比较的目标对象。
     *
     * @return boolean。
     */
    public static boolean anyEquals(Object src, Object... tars) {
        if (src == null) {
            return false;
        }
        if (ArrayUtil.isEmpty(tars)) {
            return false;
        }
        for (Object tar : tars) {
            if (src.equals(tar)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 如果源对象不与任何目标对象相等，则返回 true
     *
     * @param src 源对象。
     * @param tars 要比较的目标对象。
     *
     * @return boolean。
     */
    public static boolean notAnyEquals(Object src, Object... tars) {
        return !anyEquals(src, tars);
    }
}