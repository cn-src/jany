package cn.javaer.jany.ebean;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.javaer.jany.model.Page;
import cn.javaer.jany.model.PageParam;
import cn.javaer.jany.model.Sort;
import io.ebean.Finder;
import io.ebean.PagedList;
import io.ebean.Query;
import io.ebean.UpdateQuery;

import java.util.List;
import java.util.Map;

/**
 * @author cn-src
 */
public class BaseFinder<I, T> extends Finder<I, T> {

    public BaseFinder() {
        this(null);
    }

    public BaseFinder(String databaseName) {
        //noinspection ConstantConditions
        super(null, databaseName);
        final Class<?> clazz = ClassUtil.getTypeArgument(this.getClass(), 1);
        ReflectUtil.setFieldValue(this, "type", clazz);
    }

    public Query<T> query(PageParam pageParam) {
        return Dsl.page(query(), pageParam);
    }

    public Query<T> query(Sort sort) {
        return Dsl.sort(query(), sort);
    }

    public List<T> list(PageParam pageParam) {
        return query(pageParam).findList();
    }

    public List<T> list(Sort sort) {
        return query(sort).findList();
    }

    public int count() {
        return query().findCount();
    }

    public Page<T> page(PageParam pageParam) {
        final PagedList<T> pagedList = query(pageParam).findPagedList();
        return Page.of(pagedList.getList(), pagedList.getTotalCount());
    }

    public int updateById(Object obj, I id) {
        final UpdateQuery<T> updateQuery = update();
        update(obj, updateQuery).where().idEq(id);
        return updateQuery.update();
    }

    public UpdateQuery<T> update(Object obj, UpdateQuery<T> updateQuery) {
        final Map<String, Object> beanMap = BeanUtil.beanToMap(obj);
        for (Map.Entry<String, Object> entry : beanMap.entrySet()) {
            if (ObjectUtil.isEmpty(entry.getValue())) {
                updateQuery.setNull(entry.getKey());
            }
            else {
                updateQuery.set(entry.getKey(), entry.getValue());
            }
        }
        return updateQuery;
    }
}