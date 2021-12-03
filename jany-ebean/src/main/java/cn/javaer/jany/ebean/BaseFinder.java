package cn.javaer.jany.ebean;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.javaer.jany.model.Page;
import cn.javaer.jany.model.PageParam;
import cn.javaer.jany.util.ReflectionUtils;
import io.ebean.Finder;
import io.ebean.PagedList;
import io.ebean.Query;
import io.ebean.UpdateQuery;
import io.ebean.annotation.WhenCreated;
import io.ebean.annotation.WhenModified;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author cn-src
 */
public class BaseFinder<I, T> extends Finder<I, T> {

    private final boolean sort;
    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    private final Optional<String> whenCreatedOpt;

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    private final Optional<String> whenModifiedOpt;

    public BaseFinder() {
        this(null);
    }

    public BaseFinder(String databaseName) {
        //noinspection ConstantConditions
        super(null, databaseName);
        final Class<?> clazz = ClassUtil.getTypeArgument(this.getClass(), 1);
        ReflectUtil.setFieldValue(this, "type", clazz);
        this.whenCreatedOpt = ReflectionUtils.fieldNameByAnnotation(clazz, WhenCreated.class);
        this.whenModifiedOpt = ReflectionUtils.fieldNameByAnnotation(clazz, WhenModified.class);
        this.sort = true;
    }

    public Query<T> querySort() {
        final Query<T> query = query();
        whenModifiedOpt.ifPresent(it -> query.orderBy().desc(it));
        whenCreatedOpt.ifPresent(it -> query.orderBy().desc(it));
        return query;
    }

    public Query<T> queryPage(PageParam pageParam) {
        if (sort) {
            return querySort().setMaxRows(pageParam.getSize())
                .setFirstRow(pageParam.getOffset());
        }
        return query()
            .setMaxRows(pageParam.getSize())
            .setFirstRow(pageParam.getOffset());
    }

    @Override
    public @NotNull
    List<T> all() {
        if (sort) {
            return querySort().findList();
        }
        return super.all();
    }

    public List<T> all(PageParam pageParam) {
        return queryPage(pageParam).findList();
    }

    public int count() {
        return query().findCount();
    }

    public Page<T> paged(PageParam pageParam) {
        final PagedList<T> pagedList = queryPage(pageParam).findPagedList();
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