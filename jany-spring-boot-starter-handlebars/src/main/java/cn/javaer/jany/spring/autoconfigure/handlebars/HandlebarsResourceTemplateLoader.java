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

package cn.javaer.jany.spring.autoconfigure.handlebars;

import com.github.jknack.handlebars.io.URLTemplateLoader;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.net.URL;

/**
 * @author cn-src
 */
public class HandlebarsResourceTemplateLoader extends URLTemplateLoader implements ResourceLoaderAware {

    private ResourceLoader resourceLoader = new DefaultResourceLoader(null);

    @Override
    protected URL getResource(final String location) throws IOException {
        final Resource resource = this.resourceLoader.getResource(location);
        if (!resource.exists()) {
            return null;
        }
        return resource.getURL();
    }

    @Override
    public String resolve(final String location) {
        String protocol = null;
        if (location.startsWith(ResourceUtils.CLASSPATH_URL_PREFIX)) {
            protocol = ResourceUtils.CLASSPATH_URL_PREFIX;
        }
        else if (location.startsWith(ResourceUtils.FILE_URL_PREFIX)) {
            protocol = ResourceUtils.FILE_URL_PREFIX;
        }
        if (protocol == null) {
            return super.resolve(location);
        }
        return protocol + super.resolve(location.substring(protocol.length()));
    }

    @Override
    public void setResourceLoader(final @NotNull ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }
}