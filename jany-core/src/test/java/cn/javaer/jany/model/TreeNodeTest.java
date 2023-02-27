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
 *
 */

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
        // language=JSON
        final TreeNode node1 = Json.DEFAULT.read("{\"name\": \"t1\"}", TreeNode.class);
        assertThat(node1).extracting(TreeNode::getName).isEqualTo("t1");
    }

    @Test
    @DisplayName("测试 TreeNode 反序列化，带子节点")
    void json2() {
        // language=JSON
        final TreeNode node2 = Json.DEFAULT.read("{\"name\": \"t1\",\"children\":[{\"name\": " +
            "\"t2\"}]}", TreeNode.class);
        assertThat(node2).extracting(TreeNode::getName).isEqualTo("t1");
        assertThat(node2.getChildren()).hasSize(1).extracting(TreeNode::getName).contains("t2");
    }

    @Test
    @DisplayName("测试 TreeNode 反序列化，带扩展属性")
    void json3() {
        // language=JSON
        final TreeNode node3 = Json.DEFAULT.read("{\"name\": \"t1\",\"children\":[{\"name\": " +
            "\"t2\"}],\"m1\": 1}", TreeNode.class);
        assertThat(node3).extracting(TreeNode::getName).isEqualTo("t1");
        assertThat(node3.getChildren()).hasSize(1).extracting(TreeNode::getName).contains("t2");
        assertThat(node3.getDynamic()).containsEntry("m1", 1);
    }
}