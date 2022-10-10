package cn.javaer.jany.jackson;

import com.fasterxml.jackson.databind.module.SimpleModule;

public final class JanyModule extends SimpleModule {
   
    @Override
    public void setupModule(SetupContext context) {
        context.appendAnnotationIntrospector(JanyJacksonAnnotationIntrospector.INSTANCE);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return this == o;
    }

    @Override
    public String getModuleName() {
        return "JanyModule";
    }
}