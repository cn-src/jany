package cn.javaer.jany.model;

/**
 * @author cn-src
 */
public interface TreeHandler<E> {

    @SuppressWarnings("rawtypes") TreeHandler EMPTY = (info) -> {};

    /**
     * Empty tree handler.
     *
     * @param <E> the type parameter
     *
     * @return the tree handler
     */
    @SuppressWarnings("unchecked")
    static <E> TreeHandler<E> empty() {
        return EMPTY;
    }

    /**
     * TreeNode 自定义处理.
     *
     * @param treeInfo the tree info
     */
    void apply(TreeInfo<E> treeInfo);
}