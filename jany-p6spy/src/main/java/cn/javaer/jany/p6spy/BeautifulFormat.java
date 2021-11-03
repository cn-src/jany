package cn.javaer.jany.p6spy;

import com.p6spy.engine.spy.appender.CustomLineFormat;

/**
 * 带缩进的 SQL 格式化.
 *
 * @author cn-src
 */
public class BeautifulFormat extends CustomLineFormat {
    private final BasicFormatter formatter = new BasicFormatter();

    @Override
    public String formatMessage(final int connectionId, final String now, final long elapsed,
                                final String category,
                                final String prepared, final String sql, final String url) {

        return super.formatMessage(connectionId, now, elapsed, category, prepared,
                this.formatter.format(sql), url);
    }
}