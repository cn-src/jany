package cn.javaer.jany.model;

import cn.javaer.jany.jackson.Json;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author cn-src
 */
class TreeNodeTest {

    @Test
    @DisplayName("测试 TreeNode 组装")
    void addChildren() {
        final TreeNode t1 = TreeNode.of("t1", TreeNode.of("t1_1"), TreeNode.of("t1_2"));

        assertThat(t1).extracting(TreeNode::getName).isEqualTo("t1");
        assertThat(t1.getChildren()).hasSize(2)
            .extracting(TreeNode::getName)
            .contains("t1_1", "t1_2");
    }

    @Test
    @DisplayName("测试 TreeNode 反序列化，单节点")
    void json() {
        //language=JSON
        final TreeNode node1 = Json.DEFAULT.read("{\"name\": \"t1\"}", TreeNode.class);
        assertThat(node1).extracting(TreeNode::getName).isEqualTo("t1");
    }

    @Test
    @DisplayName("测试 TreeNode 反序列化，带子节点")
    void json2() {
        //language=JSON
        final TreeNode node2 = Json.DEFAULT.read("{\"name\": \"t1\",\"children\":[{\"name\": " +
            "\"t2\"}]}", TreeNode.class);
        assertThat(node2).extracting(TreeNode::getName).isEqualTo("t1");
        assertThat(node2.getChildren()).hasSize(1).extracting(TreeNode::getName).contains("t2");
    }

    @Test
    @DisplayName("测试 TreeNode 反序列化，带扩展属性")
    void json3() {
        //language=JSON
        final TreeNode node3 = Json.DEFAULT.read("{\"name\": \"t1\",\"children\":[{\"name\": " +
            "\"t2\"}],\"m1\": 1}", TreeNode.class);
        assertThat(node3).extracting(TreeNode::getName).isEqualTo("t1");
        assertThat(node3.getChildren()).hasSize(1).extracting(TreeNode::getName).contains("t2");
        assertThat(node3.getDynamic()).containsEntry("m1", 1);
    }
}