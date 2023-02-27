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