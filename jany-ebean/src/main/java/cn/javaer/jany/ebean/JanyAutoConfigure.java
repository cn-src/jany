package cn.javaer.jany.ebean;

import io.ebean.config.AutoConfigure;
import io.ebean.config.DatabaseConfig;

public class JanyAutoConfigure implements AutoConfigure {
    @Override
    public void preConfigure(DatabaseConfig config) {
        config.add(JanyBeanPersistController.INSTANCE);
    }

    @Override
    public void postConfigure(DatabaseConfig config) {

    }
}