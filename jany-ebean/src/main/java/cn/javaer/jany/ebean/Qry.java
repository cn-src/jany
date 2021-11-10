package cn.javaer.jany.ebean;

import cn.hutool.core.util.ObjectUtil;
import io.ebean.typequery.TQRootBean;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * @author cn-src
 */
public class Qry<RB> {

    private final RB rootBean;

    private Qry(RB rootBean) {
        this.rootBean = rootBean;
    }

    public <V> Qry<RB> opt(@NotNull final Consumer<V> fun,
                           final V value) {
        if (ObjectUtil.isEmpty(value)) {
            return this;
        }
        fun.accept(value);
        return this;
    }

    public <V> Qry<RB> opt(@NotNull final BiConsumer<V, V> fun,
                           final V value1, final V value2) {
        if (ObjectUtil.isEmpty(value1) || ObjectUtil.isEmpty(value2)) {
            return this;
        }
        fun.accept(value1, value2);
        return this;
    }

    public RB rb() {
        return rootBean;
    }

    public static <T, R, RB extends TQRootBean<T, R>> Qry<RB> of(RB rootBean) {
        return new Qry<>(rootBean);
    }
}