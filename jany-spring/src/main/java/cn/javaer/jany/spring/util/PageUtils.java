package cn.javaer.jany.spring.util;

import cn.javaer.jany.model.PageParam;
import org.springframework.data.domain.Pageable;

/**
 * @author cn-src
 */
public interface PageUtils {

    static PageParam of(final Pageable pageable) {
        return PageParam.of(pageable.getPageNumber() + 1, pageable.getPageSize());
    }
}