package cn.javaer.jany.ebean;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.javaer.jany.model.Page;
import cn.javaer.jany.model.PageParam;
import cn.javaer.jany.model.Sort;
import io.ebean.PagedList;
import io.ebean.Query;
import io.ebean.typequery.TQRootBean;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * 构建动态查询条件的 Builder 模式类。
 *
 * @author cn-src
 */
public class Qry<T> {

    private final Query<T> query;

    private Qry(Query<T> query) {
        this.query = query;
    }

    public <V> Qry<T> opt(@NotNull final Consumer<V> fun, final V value) {
        if (ObjectUtil.isEmpty(value)) {
            return this;
        }
        fun.accept(value);
        return this;
    }

    public <V> Qry<T> opt(@NotNull final BiConsumer<V, V> fun, final V value1, final V value2) {
        if (ObjectUtil.isEmpty(value1) || ObjectUtil.isEmpty(value2)) {
            return this;
        }
        fun.accept(value1, value2);
        return this;
    }

    public Qry<T> fn(@NotNull Runnable fun) {
        Assert.notNull(fun);
        fun.run();
        return this;
    }

    public Qry<T> fn(@NotNull Supplier<Qry<T>> fun) {
        Assert.notNull(fun);
        return fun.get();
    }

    public Page<T> page(PageParam pageParam) {
        Dsl.query(query, pageParam);
        final PagedList<T> pagedList = query.findPagedList();
        return Page.of(pagedList.getList(), pagedList.getTotalCount());
    }

    public List<T> list(Sort sort) {
        return Dsl.query(query, sort).findList();
    }

    public List<T> list() {
        return query.findList();
    }

    public T one() {
        return query.findOne();
    }

    public Optional<T> oneOrEmpty() {
        return query.findOneOrEmpty();
    }

    public Query<T> query() {
        return query;
    }

    public static <T> Qry<T> of(Query<T> query) {
        return new Qry<>(query);
    }

    public static <R, T, QR extends TQRootBean<T, R>> Qry<T> of(QR rootBean) {
        return new Qry<>(rootBean.query());
    }
}