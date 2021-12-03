package cn.javaer.jany.ebean;

import cn.hutool.core.util.ObjectUtil;
import cn.javaer.jany.model.Page;
import cn.javaer.jany.model.PageParam;
import io.ebean.PagedList;
import io.ebean.typequery.TQRootBean;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * @author cn-src
 */
public class Qry<E, QR extends TQRootBean<E, QR>> {

    private final QR rootBean;

    private Qry(QR rootBean) {
        this.rootBean = rootBean;
    }

    public <V> Qry<E, QR> opt(@NotNull final Consumer<V> fun,
                              final V value) {
        if (ObjectUtil.isEmpty(value)) {
            return this;
        }
        fun.accept(value);
        return this;
    }

    public <V> Qry<E, QR> opt(@NotNull final BiConsumer<V, V> fun,
                              final V value1, final V value2) {
        if (ObjectUtil.isEmpty(value1) || ObjectUtil.isEmpty(value2)) {
            return this;
        }
        fun.accept(value1, value2);
        return this;
    }

    public Page<E> findAll(PageParam pageParam) {
        rootBean.setMaxRows(pageParam.getSize())
            .setFirstRow(pageParam.getOffset());
        final PagedList<E> pagedList = rootBean.findPagedList();
        return Page.of(pagedList.getList(), pagedList.getTotalCount());
    }

    public QR rb() {
        return rootBean;
    }

    public static <E, QR extends TQRootBean<E, QR>> Qry<E, QR> of(QR rootBean) {
        return new Qry<>(rootBean);
    }
}