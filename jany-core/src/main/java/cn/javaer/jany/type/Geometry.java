package cn.javaer.jany.type;

/**
 * 几何类型
 *
 * @author cn-src
 */
public final class Geometry {
    private final String data;

    private Geometry(String data) {
        this.data = data;
    }

    public String data() {
        return this.data;
    }

    public static Geometry valueOf(String data) {
        return new Geometry(data);
    }
}