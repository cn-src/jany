package cn.javaer.jany.spring.autoconfigure.minio.test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.OverrideAutoConfiguration;
import org.springframework.boot.test.autoconfigure.filter.TypeExcludeFilters;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.core.annotation.AliasFor;
import org.springframework.core.env.Environment;
import org.springframework.test.context.BootstrapWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author cn-src
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@BootstrapWith(MinioTestContextBootstrapper.class)
@ExtendWith(SpringExtension.class)
@OverrideAutoConfiguration(enabled = false)
@TypeExcludeFilters(MinioTypeExcludeFilter.class)
@AutoConfigureMinio
@ImportAutoConfiguration
public @interface MinioTest {

    /**
     * Properties in form {@literal key=value} that should be added to the Spring
     * {@link Environment} before the test runs.
     *
     * @return the properties to add
     */
    String[] properties() default {};

    /**
     * Determines if default filtering should be used with
     * {@link SpringBootApplication @SpringBootApplication}. By default no beans are
     * included.
     *
     * @return if default filters should be used
     *
     * @see #includeFilters()
     * @see #excludeFilters()
     */
    boolean useDefaultFilters() default true;

    /**
     * A set of include filters which can be used to add otherwise filtered beans to the
     * application context.
     *
     * @return include filters to apply
     */
    Filter[] includeFilters() default {};

    /**
     * A set of exclude filters which can be used to filter beans that would otherwise be
     * added to the application context.
     *
     * @return exclude filters to apply
     */
    Filter[] excludeFilters() default {};

    /**
     * Auto-configuration exclusions that should be applied for this test.
     *
     * @return auto-configuration exclusions to apply
     */
    @AliasFor(annotation = ImportAutoConfiguration.class, attribute = "exclude")
    Class<?>[] excludeAutoConfiguration() default {};
}