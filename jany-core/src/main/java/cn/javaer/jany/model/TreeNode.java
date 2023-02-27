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

package cn.javaer.jany.model;

import cn.hutool.core.collection.ListUtil;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

/**
 * @author cn-src
 */
public class TreeNode {
    @Getter
    final String name;

    final List<TreeNode> children;

    Map<String, TreeNode> childrenMap;

    @SuppressWarnings("rawtypes")
    TreeInfo treeInfo;

    @JsonAnySetter
    final Map<String, Object> dynamic = new HashMap<>();

    private TreeNode(String name, List<TreeNode> children) {
        this.name = name;
        this.children = children;
        this.childrenMap = new LinkedHashMap<>();
    }

    public static TreeNode of(String name, TreeNode... children) {
        return new TreeNode(name, ListUtil.toList(children));
    }

    @JsonCreator
    public static TreeNode of(@JsonProperty("name") String name,
                              @JsonProperty("children") List<TreeNode> children) {
        return new TreeNode(name, ListUtil.toList(children));
    }

    void removeFirstChild() {
        if (!this.children.isEmpty()) {
            this.children.remove(0);
        }
    }

    void moveToChildren() {
        this.children.addAll(childrenMap.values());
        this.childrenMap = null;
    }

    @UnmodifiableView
    public List<TreeNode> getChildren() {
        return Collections.unmodifiableList(children);
    }

    @JsonAnyGetter
    @UnmodifiableView
    public Map<String, Object> getDynamic() {
        return Collections.unmodifiableMap(this.dynamic);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", TreeNode.class.getSimpleName() + "[", "]")
            .add("name='" + name + "'")
            .add("@children.size=" + children.size())
            .add("@dynamic.size=" + dynamic.size())
            .toString();
    }
}