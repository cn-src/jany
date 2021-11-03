package cn.javaer.jany.jooq.codegen;

import org.junit.jupiter.api.Test;

/**
 * @author cn-src
 */
class OverrideGeneratorTest {

    @Test
    void replaceGeometry() {
        final String str = "    /**\n" +
            "     * @deprecated Unknown data type. Please define an explicit {@link org.jooq" +
            ".Binding} to specify how this type should be handled. Deprecation can be turned off " +
            "using {@literal <deprecationOnUnknownTypes/>} in your code generator configuration" +
            ".\n" +
            "     */\n" +
            "    @java.lang.Deprecated\n" +
            "    public final TableField<Record, Object> GEOM = createField(DSL.name(\"geom\"), " +
            "org.jooq.impl.DefaultDataType.getDefaultDataType(\"\\\"public\\\"" +
            ".\\\"geometry\\\"\"), this, \"\");\n" +
            "\n" +
            "    /**\n" +
            "     * @deprecated Unknown data type. Please define an explicit {@link org.jooq" +
            ".Binding} to specify how this type should be handled. Deprecation can be turned off " +
            "using {@literal <deprecationOnUnknownTypes/>} in your code generator configuration" +
            ".\n" +
            "     */\n" +
            "    @java.lang.Deprecated\n" +
            "    public final TableField<Record, Object> GEOM = createField(DSL.name(\"geom\"), " +
            "org.jooq.impl.DefaultDataType.getDefaultDataType(\"\\\"public\\\"" +
            ".\\\"geometry\\\"\"), this, \"\");\n";

        String replaceGeometry = new OverrideGenerator().replaceGeometry(str);
        System.out.println(replaceGeometry);
    }
}