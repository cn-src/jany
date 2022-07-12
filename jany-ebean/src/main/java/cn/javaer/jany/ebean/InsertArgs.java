package cn.javaer.jany.ebean;

import cn.hutool.core.lang.Assert;
import io.ebean.DB;
import io.ebean.Database;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author cn-src
 */
@Data
@Accessors(fluent = true)
public class InsertArgs {
    private Database db;

    private List<Map<String, Object>> rowList;

    private String tableName;

    private Set<String> columns;

    public Database db() {
        return db == null ? DB.getDefault() : db;
    }

    public InsertArgs rowList(List<Map<String, Object>> rowList) {
        Assert.notEmpty(rowList);
        this.rowList = rowList;
        final Set<String> columns = rowList.get(0).keySet();
        this.columns = columns;
        return this;
    }
}