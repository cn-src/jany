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

package cn.javaer.jany.spring.web.exception;

import cn.javaer.jany.util.IoUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author cn-src
 */
@Tag(name = "系统")
@RestController
@RequestMapping
public class ErrorInfoController implements InitializingBean {

    private Map<String, String> errorsMap;

    @Operation(summary = "错误码表")
    @GetMapping("error_infos")
    public Map<String, String> errorInfos() {
        return errorsMap;
    }

    private void load(String resourceBundle) {
        final ClassPathResource resource = new ClassPathResource(resourceBundle);
        if (resource.exists()) {
            try {
                Properties props = IoUtils.readProperties(resource.getInputStream());
                for (String propertyName : props.stringPropertyNames()) {
                    if (propertyName.startsWith("RUNTIME_")) {
                        continue;
                    }
                    errorsMap.put(propertyName, ErrorMessageSource.getMessage(propertyName));
                }
            }
            catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }
    }

    @Override
    public void afterPropertiesSet() {
        errorsMap = new LinkedHashMap<>();
        load("default-errors-messages_zh.properties");
        load("errors-messages_zh.properties");
    }
}