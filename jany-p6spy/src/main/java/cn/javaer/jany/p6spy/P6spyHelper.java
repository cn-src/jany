package cn.javaer.jany.p6spy;

import com.p6spy.engine.logging.P6LogFactory;
import com.p6spy.engine.spy.P6ModuleManager;
import com.p6spy.engine.spy.P6SpyFactory;
import com.p6spy.engine.spy.option.EnvironmentVariables;
import com.p6spy.engine.spy.option.P6OptionsSource;
import com.p6spy.engine.spy.option.SpyDotProperties;
import com.p6spy.engine.spy.option.SystemProperties;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author cn-src
 */
public class P6spyHelper {
    private static boolean initialized = false;

    private static boolean enabled = false;

    public static void initConfig() {
        if (!enabled || initialized) {
            return;
        }
        SpyDotProperties spyDotProperties = null;
        try {
            spyDotProperties = new SpyDotProperties();
        }
        catch (final IOException ignored) {
        }
        final Map<String, String> props = Stream.of(spyDotProperties,
                new EnvironmentVariables(), new SystemProperties())
            .filter(Objects::nonNull)
            .map(P6OptionsSource::getOptions)
            .filter(Objects::nonNull)
            .flatMap(options -> options.entrySet().stream())
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                // always using value from the first P6OptionsSource
                (value1, value2) -> value1));
        String customModuleList = props.get("modulelist");
        if (customModuleList == null) {
            List<String> moduleList = new ArrayList<>();
            moduleList.add(P6SpyFactory.class.getName());
            moduleList.add(P6LogFactory.class.getName());
            System.setProperty("p6spy.config.modulelist", String.join(",", moduleList));
        }
        //noinspection AlibabaUndefineMagicConstant
        if (!props.containsKey("logMessageFormat")) {
            System.setProperty("p6spy.config.logMessageFormat",
                BeautifulFormat.class.getName());
            System.setProperty("p6spy.config.customLogMessageFormat",
                "time %(executionTime) ms | url %(url)\n%(sql)");
        }
        //noinspection AlibabaUndefineMagicConstant
        if (!props.containsKey("appender")) {
            System.setProperty("p6spy.config.appender",
                "com.p6spy.engine.spy.appender.Slf4JLogger");
        }
        P6ModuleManager.getInstance().reload();
        initialized = true;
    }

    public static void enable() {
        enabled = true;
    }

    public static boolean isEnabled() {
        return enabled;
    }
}