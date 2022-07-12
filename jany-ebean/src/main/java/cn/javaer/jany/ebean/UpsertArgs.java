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
public class UpsertArgs {
    private Database db;

    private List<Map<String, Object>> rowList;

    private String tableName;

    private Set<String> insertColumns;

    private Set<String> updateColumns;

    private String upsertKey;

    private UpsertMode mode;

    public Database db() {
        return db == null ? DB.getDefault() : db;
    }

    public UpsertArgs rowList(List<Map<String, Object>> rowList) {
        Assert.notEmpty(rowList);
        this.rowList = rowList;
        final Set<String> columns = rowList.get(0).keySet();
        this.insertColumns = columns;
        this.updateColumns = columns;
        return this;
    }
}