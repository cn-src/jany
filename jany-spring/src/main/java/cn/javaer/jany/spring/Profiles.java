package cn.javaer.jany.spring;

/**
 * Spring Profile 定义。
 *
 * @author cn-src
 */
public interface Profiles {

    /**
     * 默认环境。
     */
    String DEFAULT = "default";

    /**
     * 开发环境。
     */
    String DEV = "dev";

    /**
     * 测试环境。
     */
    String TEST = "test";

    /**
     * 生产环境。
     */
    String PROD = "prod";

    /**
     * Mock 环境。
     */
    String MOCK = "mock";
}