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

package cn.javaer.jany.p6spy;

import com.p6spy.engine.logging.P6LogFactory;
import com.p6spy.engine.spy.P6ModuleManager;
import com.p6spy.engine.spy.P6SpyFactory;
import com.p6spy.engine.spy.option.EnvironmentVariables;
import com.p6spy.engine.spy.option.P6OptionsSource;
import com.p6spy.engine.spy.option.SpyDotProperties;
import com.p6spy.engine.spy.option.SystemProperties;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author cn-src
 */
public class P6spyHelper {
    private static boolean initialized = false;

    public static void initConfig() {
        if (initialized) {
            return;
        }
        SpyDotProperties spyDotProperties = null;
        try {
            spyDotProperties = new SpyDotProperties();
        }
        catch (final IOException ignored) {
        }
        final Map<String, String> props = Stream.of(spyDotProperties,
                new EnvironmentVariables(), new SystemProperties())
            .filter(Objects::nonNull)
            .map(P6OptionsSource::getOptions)
            .filter(Objects::nonNull)
            .flatMap(options -> options.entrySet().stream())
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                // always using value from the first P6OptionsSource
                (value1, value2) -> value1));
        String customModuleList = props.get("modulelist");
        if (customModuleList == null) {
            List<String> moduleList = new ArrayList<>();
            moduleList.add(P6SpyFactory.class.getName());
            moduleList.add(P6LogFactory.class.getName());
            System.setProperty("p6spy.config.modulelist", String.join(",", moduleList));
        }
        // noinspection AlibabaUndefineMagicConstant
        if (!props.containsKey("logMessageFormat")) {
            System.setProperty("p6spy.config.logMessageFormat",
                BeautifulFormat.class.getName());
            System.setProperty("p6spy.config.customLogMessageFormat",
                "time %(executionTime) ms | url %(url)\n%(sql)");
        }
        // noinspection AlibabaUndefineMagicConstant
        if (!props.containsKey("appender")) {
            System.setProperty("p6spy.config.appender",
                "com.p6spy.engine.spy.appender.Slf4JLogger");
        }
        P6ModuleManager.getInstance().reload();
        initialized = true;
    }
}