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

import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author cn-src
 */
class SynthesizedAnnotationInvocationHandler implements InvocationHandler {
    private final Map<String, Object> attributeValues;

    private final Annotation annotation;

    public SynthesizedAnnotationInvocationHandler(Annotation annotation,
                                                  Map<String, Object> attributeValues) {
        this.annotation = annotation;
        this.attributeValues = Map.copyOf(attributeValues);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        if ("toString".equals(method.getName())) {
            return annotationToString();
        }
        // TODO 简单实现
        if ("equals".equals(method.getName())) {
            return args[0] == this || annotation.equals(args[0]);
        }
        if ("hashCode".equals(method.getName())) {
            return annotation.hashCode();
        }
        if ("annotationType".equals(method.getName())) {
            return annotation.annotationType();
        }
        return attributeValues.get(method.getName());
    }

    private String annotationToString() {
        StringBuilder sb = new StringBuilder("@")
            .append(annotation.annotationType().getName()).append("(");

        for (Map.Entry<String, Object> entry : attributeValues.entrySet()) {
            sb.append(entry.getKey());
            sb.append('=');
            sb.append(attributeValueToString(entry.getValue()));
            sb.append(", ");
        }
        return sb.delete(sb.length() - 2, sb.length()).append(")").toString();
    }

    private String attributeValueToString(Object value) {
        if (value instanceof Object[]) {
            return "[" + StringUtils.arrayToDelimitedString((Object[]) value, ", ") + "]";
        }
        return String.valueOf(value);
    }
}