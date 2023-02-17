package cn.javaer.jany.ebean;

import cn.hutool.core.map.WeakConcurrentMap;
import cn.hutool.core.util.ObjectUtil;
import cn.javaer.jany.util.ReflectUtils;
import io.ebean.ValuePair;
import io.ebean.event.BeanPersistAdapter;
import io.ebean.event.BeanPersistRequest;
import io.ebeaninternal.server.core.PersistRequestBean;

import java.util.Map;
import java.util.Set;

/**
 * @author cn-src
 */
public class JanyBeanPersistController extends BeanPersistAdapter {
    public static final JanyBeanPersistController INSTANCE = new JanyBeanPersistController();

    private static final WeakConcurrentMap<Class<?>, Set<String>> unsetProps = new WeakConcurrentMap<>();

    @Override
    public boolean isRegisterFor(Class<?> cls) {
        return true;
    }

    @Override
    public boolean preUpdate(BeanPersistRequest<?> request) {
        PersistRequestBean<?> requestBean = (PersistRequestBean<?>) request;
        Set<String> unsets = unsetProps.computeIfAbsent(request.bean().getClass(), () ->
            ReflectUtils.fieldNames(request.bean().getClass(), UnsetIfEmpty.class));
        for (Map.Entry<String, ValuePair> entry : requestBean.updatedValues().entrySet()) {
            if (ObjectUtil.isEmpty(entry.getValue().getNewValue()) && unsets.contains(entry.getKey())) {
                requestBean.intercept().setPropertyLoaded(entry.getKey(), false);
            }
        }
        return true;
    }
}