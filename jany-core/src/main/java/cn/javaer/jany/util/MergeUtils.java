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

import cn.javaer.jany.util.function.Consumer3;
import cn.javaer.jany.util.function.Function3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;

/**
 * 合并转换工具类。
 *
 * @author cn -src
 */
public interface MergeUtils {

    /**
     * Merge list.
     *
     * @param <S> the type parameter
     * @param <R> the type parameter
     * @param sList the s list
     * @param mergePredicate the merge predicate
     * @param handler the handler
     * @param rCreator the r creator
     *
     * @return the list
     */
    static <S, R> List<R> merge(final List<S> sList,
                                final BiPredicate<S, R> mergePredicate,
                                final BiConsumer<S, R> handler,
                                final Function<S, R> rCreator) {

        Objects.requireNonNull(mergePredicate);
        Objects.requireNonNull(handler);
        Objects.requireNonNull(rCreator);
        if (sList == null || sList.isEmpty()) {
            return Collections.emptyList();
        }

        final List<R> results = new ArrayList<>();
        for (final S s : sList) {
            final R r = results.stream()
                .filter(it -> mergePredicate.test(s, it))
                .findFirst()
                .orElseGet(() -> {
                    final R initR = rCreator.apply(s);
                    results.add(initR);
                    return initR;
                });
            handler.accept(s, r);
        }
        return results;
    }

    /**
     * Merge list.
     *
     * @param <S> the type parameter
     * @param sList the s list
     * @param mergePredicate the merge predicate
     * @param handler the handler
     *
     * @return the list
     */
    static <S> List<S> merge(final List<S> sList,
                             final BiPredicate<S, S> mergePredicate,
                             final BiConsumer<S, S> handler) {
        return merge(sList, mergePredicate, handler, s -> s);
    }

    /**
     * 将 pList 的数据合并到 sList 中，并返回新的 List.
     *
     * @param <S> S
     * @param <P> P
     * @param <R> R
     * @param sList 源 List
     * @param pList 进行合并的 List
     * @param mergePredicate 合并条件
     * @param resultFun 新结果对象创建函数
     *
     * @return 新 List
     */
    static <S, P, R> List<R> merge(final List<S> sList, final List<P> pList,
                                   final BiPredicate<S, P> mergePredicate,
                                   final BiFunction<S, Optional<P>, R> resultFun) {

        Objects.requireNonNull(mergePredicate);
        Objects.requireNonNull(resultFun);
        if (sList == null || sList.isEmpty()) {
            return Collections.emptyList();
        }

        final List<R> result = new ArrayList<>(sList.size());
        for (final S s : sList) {
            Optional<P> used = Optional.empty();
            if (null != pList) {
                for (final P p : pList) {
                    if (mergePredicate.test(s, p)) {
                        used = Optional.of(p);
                        break;
                    }
                }
            }
            result.add(resultFun.apply(s, used));
        }
        return result;
    }

    /**
     * 将 pList 的数据合并到 sList 中，并返回新的 sList.
     *
     * @param <S> S
     * @param <P> P
     * @param sList 源 List
     * @param pList 进行合并的 List
     * @param mergePredicate 合并条件
     * @param resultFun 新结果对象创建函数
     *
     * @return 新的 S List
     */
    static <S, P> List<S> merge(final List<S> sList, final List<P> pList,
                                final BiPredicate<S, P> mergePredicate,
                                final BiConsumer<S, Optional<P>> resultFun) {
        return merge(sList, pList, mergePredicate, (s, p) -> {
            resultFun.accept(s, p);
            return s;
        });
    }

    /**
     * Merge list.
     *
     * @param <S> the type parameter
     * @param <P> the type parameter
     * @param <R> the type parameter
     * @param sList the s list
     * @param pList the p list
     * @param mergePredicate1 the merge predicate 1
     * @param mergePredicate2 the merge predicate 2
     * @param resultFun the result fun
     *
     * @return the list
     */
    static <S, P, R> List<R> merge(final List<S> sList, final List<P> pList,
                                   final BiPredicate<S, P> mergePredicate1,
                                   final BiPredicate<S, P> mergePredicate2,
                                   final Function3<S, Optional<P>, Optional<P>, R> resultFun) {

        Objects.requireNonNull(mergePredicate1);
        Objects.requireNonNull(mergePredicate2);
        Objects.requireNonNull(resultFun);
        if (sList == null || sList.isEmpty()) {
            return Collections.emptyList();
        }

        final List<R> result = new ArrayList<>(sList.size());
        for (final S s : sList) {
            Optional<P> p1 = Optional.empty();
            Optional<P> p2 = Optional.empty();
            if (pList != null) {
                for (final P p : pList) {
                    if (mergePredicate1.test(s, p)) {
                        p1 = Optional.of(p);
                    }
                    if (mergePredicate2.test(s, p)) {
                        p2 = Optional.of(p);
                        break;
                    }
                }
            }
            result.add(resultFun.apply(s, p1, p2));
        }
        return result;
    }

    /**
     * Merge list.
     *
     * @param <S> the type parameter
     * @param <P> the type parameter
     * @param sList the s list
     * @param pList the p list
     * @param mergePredicate1 the merge predicate 1
     * @param mergePredicate2 the merge predicate 2
     * @param resultFun the result fun
     *
     * @return the list
     */
    static <S, P> List<S> merge(final List<S> sList, final List<P> pList,
                                final BiPredicate<S, P> mergePredicate1,
                                final BiPredicate<S, P> mergePredicate2,
                                final Consumer3<S, Optional<P>, Optional<P>> resultFun) {
        return merge(sList, pList, mergePredicate1, mergePredicate2, (s, p1, p2) -> {
            resultFun.accept(s, p1, p2);
            return s;
        });
    }

    /**
     * Merge list.
     *
     * @param <R> the type parameter
     * @param <S> the type parameter
     * @param <P1> the type parameter
     * @param <P2> the type parameter
     * @param sList the s list
     * @param p1List the p 1 list
     * @param mergePredicate1 the merge predicate 1
     * @param p2List the p 2 list
     * @param mergePredicate2 the merge predicate 2
     * @param resultFun the result fun
     *
     * @return the list
     */
    static <R, S, P1, P2> List<R> merge(
        final List<S> sList,
        final List<P1> p1List, final BiPredicate<S, P1> mergePredicate1,
        final List<P2> p2List, final BiPredicate<S, P2> mergePredicate2,
        final Function3<S, Optional<P1>, Optional<P2>, R> resultFun) {

        Objects.requireNonNull(mergePredicate1);
        Objects.requireNonNull(mergePredicate2);
        Objects.requireNonNull(resultFun);
        if (sList == null || sList.isEmpty()) {
            return Collections.emptyList();
        }

        final Function<S, Optional<P1>> getP1;
        if (p1List == null || p1List.isEmpty()) {
            getP1 = (s) -> Optional.empty();
        }
        else {
            getP1 = (s) -> {
                for (final P1 p : p1List) {
                    if (mergePredicate1.test(s, p)) {
                        return Optional.of(p);
                    }
                }
                return Optional.empty();
            };
        }

        final Function<S, Optional<P2>> getP2;
        if (p2List == null || p2List.isEmpty()) {
            getP2 = (s) -> Optional.empty();
        }
        else {
            getP2 = (s) -> {
                for (final P2 p : p2List) {
                    if (mergePredicate2.test(s, p)) {
                        return Optional.of(p);
                    }
                }
                return Optional.empty();
            };
        }
        final List<R> result = new ArrayList<>(sList.size());
        for (final S s : sList) {
            result.add(resultFun.apply(s, getP1.apply(s), getP2.apply(s)));
        }
        return result;
    }

    /**
     * Merge list.
     *
     * @param <S> the type parameter
     * @param <P1> the type parameter
     * @param <P2> the type parameter
     * @param sList the s list
     * @param p1List the p 1 list
     * @param mergePredicate1 the merge predicate 1
     * @param p2List the p 2 list
     * @param mergePredicate2 the merge predicate 2
     * @param resultFun the result fun
     *
     * @return the list
     */
    static <S, P1, P2> List<S> merge(
        final List<S> sList,
        final List<P1> p1List, final BiPredicate<S, P1> mergePredicate1,
        final List<P2> p2List, final BiPredicate<S, P2> mergePredicate2,
        final Consumer3<S, Optional<P1>, Optional<P2>> resultFun) {
        return merge(sList, p1List, mergePredicate1, p2List, mergePredicate2, (s, p1, p2) -> {
            resultFun.accept(s, p1, p2);
            return s;
        });
    }

    /**
     * Merge list list.
     *
     * @param <S> the type parameter
     * @param <P> the type parameter
     * @param <R> the type parameter
     * @param sList the s list
     * @param pList the p list
     * @param mergePredicate the merge predicate
     * @param resultFun the result fun
     *
     * @return the list
     */
    static <S, P, R> List<R> mergeList(final List<S> sList, final List<P> pList,
                                       final BiPredicate<S, P> mergePredicate,
                                       final BiFunction<List<P>, S, R> resultFun) {

        Objects.requireNonNull(mergePredicate);
        Objects.requireNonNull(resultFun);
        if (sList == null || sList.isEmpty()) {
            return Collections.emptyList();
        }

        final List<R> result = new ArrayList<>(sList.size());
        for (final S s : sList) {
            final List<P> used = new ArrayList<>();
            if (null != pList) {
                for (final P p : pList) {
                    if (mergePredicate.test(s, p)) {
                        used.add(p);
                    }
                }
            }
            result.add(resultFun.apply(used, s));
        }
        return result;
    }
}