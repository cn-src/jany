package cn.javaer.jany.model;

import lombok.Value;

/**
 * @author cn-src
 */
@Value
public class TreeInfo<E> {
    TreeNode node;
    E model;
    int depth;
    int index;

    public void put(String key, Object value) {
        node.dynamic.put(key, value);
    }

    public boolean isLeaf() {
        return node.children.isEmpty();
    }
}