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
         * 断开空节点，如果当前 name 为空，则忽略当前节点以及其子节点。
         */
        BREAK_EMPTY,

        /**
         * 叶子节点 name 不为空，即：忽略所有为空的叶子节点。
         */
        NAMED_LEAF
    }

    private TreeConf(Function<E, List<String>> namesFun, Function<E, Long> sortFun,
                     TreeHandler<E> handler, boolean showNonLeafSort, EmptyMode emptyMode) {
        this.namesFun = ObjectUtil.defaultIfNull(namesFun, Empty.function());
        this.sortFun = ObjectUtil.defaultIfNull(sortFun, Empty.function());
        this.handler = ObjectUtil.defaultIfNull(handler, TreeHandler.empty());
        this.showNonLeafSort = showNonLeafSort;
        this.emptyMode = emptyMode;
    }

    Function<E, @UnmodifiableView List<@Nullable String>> namesFun;

    Function<E, Long> sortFun;

    TreeHandler<E> handler;

    boolean showNonLeafSort;

    EmptyMode emptyMode;

    public static <E> TreeConf<E> of(Function<E, String[]> namesFun) {
        return new TreeConf<>(e -> Arrays.asList(namesFun.apply(e)), Empty.function(),
            TreeHandler.empty(), false, null);
    }

    @SafeVarargs
    public static <E> TreeConf<E> of(Function<E, String> nameFun, Function<E, String>... namesFun) {
        return new TreeConf<>(toNamesFun(nameFun, namesFun),
            Empty.function(), TreeHandler.empty(), false, null);
    }

    @SafeVarargs
    public static <E> TreeConf<E> ofBreakEmpty(Function<E, String> nameFun,
                                               Function<E, String>... namesFun) {
        return new TreeConf<>(toNamesFun(nameFun, namesFun),
            Empty.function(), TreeHandler.empty(), false, EmptyMode.BREAK_EMPTY);
    }

    @SafeVarargs
    public static <E> TreeConf<E> ofNamedLeaf(Function<E, String> nameFun,
                                              Function<E, String>... namesFun) {
        return new TreeConf<>(toNamesFun(nameFun, namesFun),
            Empty.function(), TreeHandler.empty(), false, EmptyMode.NAMED_LEAF);
    }

    @SafeVarargs
    private static <E> Function<E, List<String>> toNamesFun(Function<E, String> nameFun,
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
        public final TreeConfBuilder<E> namesFun(Function<E, String> nameFun,
                                                 Function<E, String>... namesFun) {
            this.namesFun = toNamesFun(nameFun, namesFun);
            return this;
        }
    }
}