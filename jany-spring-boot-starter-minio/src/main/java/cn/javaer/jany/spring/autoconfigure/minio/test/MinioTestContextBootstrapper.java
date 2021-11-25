package cn.javaer.jany.spring.autoconfigure.minio.test;

import org.springframework.boot.test.context.SpringBootTestContextBootstrapper;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.core.annotation.MergedAnnotations.SearchStrategy;

/**
 * @author cn-src
 */
@SuppressWarnings("unused")
class MinioTestContextBootstrapper extends SpringBootTestContextBootstrapper {

    @Override
    protected String[] getProperties(final Class<?> testClass) {
        return MergedAnnotations.from(testClass, SearchStrategy.INHERITED_ANNOTATIONS).get(MinioTest.class)
            .getValue("properties", String[].class).orElse(null);
    }
}