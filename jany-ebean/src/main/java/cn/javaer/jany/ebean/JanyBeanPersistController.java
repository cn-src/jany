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

package cn.javaer.jany.ebean;

import cn.hutool.core.map.WeakConcurrentMap;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.javaer.jany.util.AnnotationUtils;
import io.ebean.event.BeanPersistAdapter;
import io.ebean.event.BeanPersistRequest;
import io.ebeaninternal.server.core.PersistRequestBean;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

/**
 * @author cn-src
 */
public class JanyBeanPersistController extends BeanPersistAdapter {
    public static final JanyBeanPersistController INSTANCE = new JanyBeanPersistController();

    private static final WeakConcurrentMap<Class<?>, Set<UnsetInfo>> unsetProps = new WeakConcurrentMap<>();

    @Override
    public boolean isRegisterFor(Class<?> cls) {
        return true;
    }

    @Override
    public boolean preUpdate(BeanPersistRequest<?> request) {
        PersistRequestBean<?> requestBean = (PersistRequestBean<?>) request;
        Set<UnsetInfo> unsets = unsetProps.computeIfAbsent(request.bean().getClass(), () -> {
            final Field[] fields = ReflectUtil.getFields(request.bean().getClass());
            Set<UnsetInfo> unsetInfos = new HashSet<>(5);
            for (Field field : fields) {
                AnnotationUtils.findMergedAnnotation(Unset.class, field)
                    .ifPresent(it -> unsetInfos.add(new UnsetInfo(field.getName(), it.onlyEmpty())));
            }
            return unsetInfos;
        });
        for (UnsetInfo info : unsets) {
            final Object newValue = requestBean.updatedValues().get(info.getFieldName()).getNewValue();
            requestBean.intercept().setPropertyLoaded(info.getFieldName(),
                !(ObjectUtil.isEmpty(newValue) && info.isOnlyEmpty()));
        }
        return true;
    }
}

@Value
class UnsetInfo {
    String fieldName;

    @EqualsAndHashCode.Exclude
    boolean onlyEmpty;
}