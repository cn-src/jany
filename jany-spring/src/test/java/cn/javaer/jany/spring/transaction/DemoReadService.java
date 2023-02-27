/*
 * Copyright 2020-2023 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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