package cn.javaer.jany.spring.autoconfigure.springdoc;

import com.fasterxml.jackson.databind.JavaType;
import io.swagger.v3.core.converter.AnnotatedType;
import io.swagger.v3.core.converter.ModelConverter;
import io.swagger.v3.core.converter.ModelConverterContext;
import io.swagger.v3.oas.models.media.Schema;
import org.springdoc.core.providers.ObjectMapperProvider;
import org.springframework.core.ResolvableType;

import java.util.Iterator;

/**
 * Page 对象文档生成的重写，如果使用 SpringDocUtils.getConfig().replaceWithClass() 是无法处理范型的。
 *
 * @author cn-src
 */
public class SpringPageConverter implements ModelConverter {

    private static final String SPRING_PAGE = "org.springframework.data.domain.Page";

    private final ObjectMapperProvider springDocObjectMapper;

    public SpringPageConverter(ObjectMapperProvider springDocObjectMapper) {
        this.springDocObjectMapper = springDocObjectMapper;
    }

    @Override
    public Schema<?> resolve(AnnotatedType type, ModelConverterContext context,
                             Iterator<ModelConverter> chain) {
        JavaType javaType = springDocObjectMapper.jsonMapper().constructType(type.getType());
        if (javaType != null) {
            Class<?> cls = javaType.getRawClass();
            if (SPRING_PAGE.equals(cls.getCanonicalName())) {
                // 通过重新创建 AnnotatedType 的方式达到改写目的，替换 Spring Page 对象，同时保留其范型。
                final Class<?> generic = javaType.getBindings().getBoundType(0).getRawClass();
                final ResolvableType resolvableType =
                    ResolvableType.forClassWithGenerics(SpringPage.class, generic);
                type = new AnnotatedType(resolvableType.getType())
                    .resolveAsRef(true);
            }
        }
        return (chain.hasNext()) ? chain.next().resolve(type, context, chain) : null;
    }
}