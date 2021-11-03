package cn.javaer.jany.test;

import cn.javaer.jany.jackson.Json;

/**
 * The interface Log.
 *
 * @author cn -src
 */
public interface Log {

    /**
     * logging json.
     *
     * @param obj the obj
     *
     * @return the json
     */
    static String json(Object obj) {
        final String json = Json.DEFAULT.write(obj);
        System.out.println(json);
        return json;
    }
}