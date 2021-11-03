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