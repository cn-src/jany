package cn.javaer.jany.util.function;

/**
 * @author cn-src
 */
@FunctionalInterface
public interface Consumer3<T1, T2, T3> {

    /**
     * 无返回值，有3个参数的函数.
     *
     * @param t1 the t1
     * @param t2 the t2
     * @param t3 the t3
     */
    void accept(T1 t1, T2 t2, T3 t3);
}