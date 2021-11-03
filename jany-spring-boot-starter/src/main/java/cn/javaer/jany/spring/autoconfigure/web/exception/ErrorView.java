package cn.javaer.jany.spring.autoconfigure.web.exception;

import cn.javaer.jany.spring.web.WebAppContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.View;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @author cn-src
 */
class ErrorView implements View {

    private static final MediaType TEXT_HTML_UTF8 =
        new MediaType("text", "html", StandardCharsets.UTF_8);

    private static final Log logger = LogFactory.getLog(ErrorView.class);

    @Override
    public void render(final Map<String, ?> model, final @NotNull HttpServletRequest request,
                       final HttpServletResponse response)
        throws Exception {
        if (response.isCommitted()) {
            final String message = this.getMessage(model);
            logger.error(message);
            return;
        }
        response.setContentType(TEXT_HTML_UTF8.toString());
        final StringBuilder builder = new StringBuilder();
        final Object timestamp = model.get("timestamp");
        final Object message = model.get("message");
        final Object trace = model.get("trace");
        final Object requestId = model.get(WebAppContext.REQUEST_ID_PARAM);
        if (response.getContentType() == null) {
            response.setContentType(this.getContentType());
        }
        builder.append("<!DOCTYPE html><html lang=\"zh\"><head><meta charset=\"UTF-8\"><title>")
            .append(this.htmlEscape(model.get("status"))).append(' ')
            .append(this.htmlEscape(message))
            .append("</title><style> ul li {line-height: 30px;}</style></head>" +
                "<body style=\"padding-left: 10px\"><h1>错误页面 ")
            .append(this.htmlEscape(model.get("status"))).append(' ')
            .append(this.htmlEscape(message))
            .append("</h1><ul style=\"list-style: none;padding: 0;\"><li>请求路径：")
            .append(this.htmlEscape(model.get("path")))
            .append("</li><li>请求时间：")
            .append(this.htmlEscape(timestamp))
            .append("</li><li>错误代码：")
            .append(this.htmlEscape(model.get("error")));

        if (requestId != null) {
            builder.append("</li><li>唯一标识：")
                .append(this.htmlEscape(requestId));
        }
        builder.append("</li></ul>");
        if (trace != null) {
            builder.append("<div style='white-space:pre-wrap;'>").append(this.htmlEscape(trace)).append("</div>");
        }
        builder.append("</body></html>");
        response.getWriter().append(builder.toString());
    }

    private String htmlEscape(final Object input) {
        return (input != null) ? HtmlUtils.htmlEscape(input.toString()) : null;
    }

    private String getMessage(final Map<String, ?> model) {
        final Object path = model.get("path");
        String message = "Cannot render error page for request [" + path + "]";
        if (model.get("message") != null) {
            message += " and exception [" + model.get("message") + "]";
        }
        message += " as the response has already been committed.";
        message += " As a result, the response may have the wrong status code.";
        return message;
    }

    @Override
    public String getContentType() {
        return "text/html";
    }
}