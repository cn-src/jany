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

package cn.javaer.jany.spring;

/**
 * Spring Profile 定义。
 *
 * @author cn-src
 */
public interface Profiles {

    /**
     * 默认环境。
     */
    String DEFAULT = "default";

    /**
     * 开发环境。
     */
    String DEV = "dev";

    /**
     * 测试环境。
     */
    String TEST = "test";

    /**
     * 生产环境。
     */
    String PROD = "prod";

    /**
     * Mock 环境。
     */
    String MOCK = "mock";
}