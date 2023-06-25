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

import org.redisson.api.LocalCachedMapOptions;
import org.redisson.api.RLocalCachedMap;
import org.redisson.api.RedissonClient;
import org.redisson.api.map.MapLoader;

public class JanyConfigService {

    private final RLocalCachedMap<String, Object> cache;

    public JanyConfigService(final RedissonClient redissonClient) {
        this.cache = redissonClient.getLocalCachedMap("jany-config", LocalCachedMapOptions.<String, Object>defaults()
                .loader(new MapLoader<>() {
                    @Override
                    public Object load(String key) {
                        return null;
                    }

                    @Override
                    public Iterable<String> loadAllKeys() {
                        return null;
                    }
                }));
    }

    public Object getValue(String key) {
        return this.cache.get(key);
    }
}