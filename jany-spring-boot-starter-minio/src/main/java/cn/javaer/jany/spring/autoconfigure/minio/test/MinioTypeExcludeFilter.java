package cn.javaer.jany.spring.autoconfigure.minio.test;

import org.springframework.boot.context.TypeExcludeFilter;
import org.springframework.boot.test.autoconfigure.filter.StandardAnnotationCustomizableTypeExcludeFilter;

/**
 * {@link TypeExcludeFilter} for {@link MinioTest @DataJooqJdbcTest}.
 *
 * @author cn-src
 */
public final class MinioTypeExcludeFilter extends StandardAnnotationCustomizableTypeExcludeFilter<MinioTest> {

    @SuppressWarnings("unused")
    MinioTypeExcludeFilter(final Class<?> testClass) {
        super(testClass);
    }
}