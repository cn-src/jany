package cn.javaer.jany.spring.autoconfigure.springdoc;

import cn.javaer.jany.spring.security.PrincipalId;
import org.springdoc.core.SpringDocUtils;
import org.springdoc.data.rest.SpringDocDataRestConfiguration;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;

/**
 * SpringDoc 支持.
 *
 * @author cn-src
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({org.springframework.data.domain.Pageable.class,
    SpringDocDataRestConfiguration.class})
@AutoConfigureAfter(SpringDocDataRestConfiguration.class)
public class SpringDocPlusConfiguration implements InitializingBean {

    @Override
    public void afterPropertiesSet() {
        SpringDocUtils.getConfig().replaceParameterObjectWithClass(
            org.springframework.data.domain.Pageable.class, PageableDoc.class);
        SpringDocUtils.getConfig().replaceParameterObjectWithClass(
            org.springframework.data.domain.PageRequest.class, PageableDoc.class);
        SpringDocUtils.getConfig().replaceWithClass(Page.class, PageDoc.class);
        SpringDocUtils.getConfig().addAnnotationsToIgnore(PrincipalId.class);
    }
}