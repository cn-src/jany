package cn.javaer.jany.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author cn-src
 */
class SortTest {

    @Test
    void parse() {
        final Sort sort = Sort.parse("id,desc");
        assertEquals(1, sort.getOrders().size());
        assertEquals(Sort.Direction.DESC, sort.getOrders().get(0).getDirection());
    }
}