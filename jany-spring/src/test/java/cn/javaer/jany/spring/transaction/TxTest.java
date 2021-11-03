package cn.javaer.jany.spring.transaction;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author cn-src
 */
@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@ComponentScan
class TxTest {
    @Autowired DemoService demoService;
    @Autowired DemoReadService demoReadService;

    @Test
    void testCustomTransactionAnnotations() {
        this.demoService.demo1();
        this.demoService.demo2();
        this.demoService.demo3();

        this.demoReadService.demo1();
        this.demoReadService.demo2();
        this.demoReadService.demo3();
    }
}