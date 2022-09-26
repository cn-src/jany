package cn.javaer.jany.model;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.javaer.jany.util.Empty;
import lombok.Builder;
import lombok.Value;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/**
 * @author cn-src
 */
@Value
@Builder
public class TreeConf<E> {

    public enum EmptyMode {

        /**
         * 如果当前节点 name 为空，则忽略当前节点以及其子节点。
         */
        ignore_children,

        /**
         * 忽略所有为空的叶子节点（即：所有末级节点为非空）。
         */
        ignore_leaf
    }

    private TreeConf(Function<E, List<String>> namesFn, Function<E, Long> sortFn,
                     TreeHandler<E> handler, EmptyMode emptyMode) {
        this.namesFn = ObjectUtil.defaultIfNull(namesFn, Empty.function());
        this.sortFn = ObjectUtil.defaultIfNull(sortFn, Empty.function());
        this.handler = ObjectUtil.defaultIfNull(handler, TreeHandler.empty());
        this.emptyMode = emptyMode;
    }

    /**
     * 节点名称获取函数。
     */
    Function<E, @UnmodifiableView List<@Nullable String>> namesFn;

    /**
     * 节点排序函数。
     */
    Function<E, Long> sortFn;

    /**
     * 节点额外处理函数。
     */
    TreeHandler<E> handler;

    /**
     * 空节点处理模式。
     */
    EmptyMode emptyMode;

    public static <E> TreeConf<E> of(Function<E, String[]> namesFun) {
        return new TreeConf<>(e -> Arrays.asList(namesFun.apply(e)), Empty.function(),
            TreeHandler.empty(), null);
    }

    @SafeVarargs
    public static <E> TreeConf<E> of(Function<E, String> nameFun, Function<E, String>... namesFun) {
        return new TreeConf<>(toNamesFn(nameFun, namesFun),
            Empty.function(), TreeHandler.empty(), null);
    }

    @SafeVarargs
    public static <E> TreeConf<E> ofIgnoreChildrenIfEmpty(Function<E, String> nameFun,
                                                          Function<E, String>... namesFun) {
        return new TreeConf<>(toNamesFn(nameFun, namesFun),
            Empty.function(), TreeHandler.empty(), EmptyMode.ignore_children);
    }

    @SafeVarargs
    public static <E> TreeConf<E> ofIgnoreLeafIfEmpty(Function<E, String> nameFun,
                                                      Function<E, String>... namesFun) {
        return new TreeConf<>(toNamesFn(nameFun, namesFun),
            Empty.function(), TreeHandler.empty(), EmptyMode.ignore_leaf);
    }

    @SafeVarargs
    private static <E> Function<E, List<String>> toNamesFn(Function<E, String> nameFun,
                                                           Function<E, String>... namesFun) {
        Objects.requireNonNull(nameFun);
        Function<E, List<String>> rsNamesFun;
        if (ArrayUtil.isEmpty(namesFun)) {
            rsNamesFun = e -> Collections.singletonList(nameFun.apply(e));
        }
        else {
            rsNamesFun = e -> {
                List<String> rs = new ArrayList<>(namesFun.length + 1);
                rs.add(nameFun.apply(e));
                for (Function<E, String> fn : namesFun) {
                    rs.add(fn.apply(e));
                }
                return rs;
            };
        }
        return rsNamesFun;
    }

    public static class TreeConfBuilder<E> {

        @SafeVarargs
        public final TreeConfBuilder<E> namesFn(Function<E, String> nameFun,
                                                Function<E, String>... namesFun) {
            this.namesFn = toNamesFn(nameFun, namesFun);
            return this;
        }
    }
}