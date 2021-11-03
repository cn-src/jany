package cn.javaer.jany.spring.transaction;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author cn-src
 */
@TxReadService(isolation = Isolation.SERIALIZABLE)
public class DemoReadService {
    private final JdbcTemplate jdbcTemplate;

    public DemoReadService(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void demo1() {
        this.jdbcTemplate.execute("SELECT 'x'");

        assertThat(TransactionSynchronizationManager.isActualTransactionActive())
                .isTrue();

        assertThat(TransactionSynchronizationManager.isCurrentTransactionReadOnly())
                .isTrue();

        assertThat(TransactionSynchronizationManager.getCurrentTransactionIsolationLevel())
                .isEqualTo(TransactionDefinition.ISOLATION_SERIALIZABLE);
    }

    @TxRead(isolation = Isolation.READ_COMMITTED)
    public void demo2() {
        this.jdbcTemplate.execute("SELECT 'x'");
        assertThat(TransactionSynchronizationManager.isActualTransactionActive())
                .isTrue();

        assertThat(TransactionSynchronizationManager.isCurrentTransactionReadOnly())
                .isTrue();

        assertThat(TransactionSynchronizationManager.getCurrentTransactionIsolationLevel())
                .isEqualTo(TransactionDefinition.ISOLATION_READ_COMMITTED);
    }

    @Tx(isolation = Isolation.READ_COMMITTED)
    public void demo3() {
        this.jdbcTemplate.execute("SELECT 'x'");

        assertThat(TransactionSynchronizationManager.isActualTransactionActive())
                .isTrue();

        assertThat(TransactionSynchronizationManager.isCurrentTransactionReadOnly())
                .isFalse();

        assertThat(TransactionSynchronizationManager.getCurrentTransactionIsolationLevel())
                .isEqualTo(TransactionDefinition.ISOLATION_READ_COMMITTED);
    }
}