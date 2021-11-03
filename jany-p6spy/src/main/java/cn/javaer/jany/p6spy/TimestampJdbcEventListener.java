package cn.javaer.jany.p6spy;

import com.p6spy.engine.common.PreparedStatementInformation;
import com.p6spy.engine.common.Value;
import com.p6spy.engine.event.JdbcEventListener;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.Map;
import java.util.Objects;

/**
 * @author cn-src
 */
public class TimestampJdbcEventListener extends JdbcEventListener {
    @Override
    public void onBeforeExecuteQuery(final PreparedStatementInformation statementInformation) {
        this.onBeforeExecute(statementInformation);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onBeforeExecute(final PreparedStatementInformation statementInformation) {

        final Map<Integer, Value> params;
        try {
            final Method method = PreparedStatementInformation.class
                .getDeclaredMethod("getParameterValues");
            Objects.requireNonNull(method).setAccessible(true);
            params = (Map<Integer, Value>) method.invoke(statementInformation);
        }
        catch (final NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
        if (null == params) {
            return;
        }
        final String url = statementInformation.getConnectionInformation().getUrl();
        for (final Map.Entry<Integer, Value> entry : params.entrySet()) {
            if (entry.getValue().getValue() instanceof Timestamp) {
                params.put(entry.getKey(), new DelegateValue(entry.getValue(), url));
            }
        }
    }
}