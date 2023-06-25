/*
 * Copyright 2020-2023 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *       https://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.javaer.jany.config;

import com.redis.testcontainers.RedisContainer;
import com.redis.testcontainers.RedisServer;
import com.redis.testcontainers.junit.AbstractTestcontainersRedisTestBase;
import com.redis.testcontainers.junit.RedisTestContext;
import com.redis.testcontainers.junit.RedisTestContextsSource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.redisson.Redisson;
import org.redisson.config.Config;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class JanyConfigServiceTest extends AbstractTestcontainersRedisTestBase {

    private static final RedisContainer redis;

    static {
        redis = new RedisContainer(RedisContainer.DEFAULT_IMAGE_NAME);
    }

    private static JanyConfigService janyConfigService;

    @BeforeAll
    static void beforeAll() {
        Config config = new Config();
        config.useSingleServer()
                .setAddress(String.format("redis://%s:%s", redis.getHost(), redis.getFirstMappedPort()));
        janyConfigService = new JanyConfigService(Redisson.create(config));
    }

    @ParameterizedTest
    @RedisTestContextsSource
    void name(RedisTestContext context) {
        final Object v1 = janyConfigService.getValue("k1");
        System.out.println(v1);
//        System.out.println(context.async().get("k1"));
    }

    @Override
    protected Collection<RedisServer> redisServers() {
        return Collections.singletonList(redis);
    }
}