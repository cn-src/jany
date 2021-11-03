package cn.javaer.jany.ebean;

import cn.javaer.jany.model.Page;
import cn.javaer.jany.model.PageParam;
import cn.javaer.jany.util.ReflectionUtils;
import io.ebean.Finder;
import io.ebean.PagedList;
import io.ebean.Query;
import io.ebean.annotation.WhenCreated;
import io.ebean.annotation.WhenModified;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

/**
 * @author cn-src
 */
public class BaseFinder<I, T> extends Finder<I, T> {

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    private final Optional<String> whenCreatedOpt;

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    private final Optional<String> whenModifiedOpt;

    public BaseFinder(Class<T> type) {
        this(type, null);
    }

    public BaseFinder(Class<T> type, String databaseName) {
        super(type, databaseName);
        this.whenCreatedOpt = ReflectionUtils.fieldNameByAnnotation(type, WhenCreated.class);
        this.whenModifiedOpt = ReflectionUtils.fieldNameByAnnotation(type, WhenModified.class);
    }

    public Query<T> sortedQuery() {
        final Query<T> query = query();
        whenModifiedOpt.ifPresent(it -> query.orderBy().desc(it));
        whenCreatedOpt.ifPresent(it -> query.orderBy().desc(it));
        return query;
    }

    public Query<T> pagedQuery(PageParam pageParam) {
        return query()
            .setMaxRows(pageParam.getSize())
            .setFirstRow(pageParam.getOffset());
    }

    public Query<T> pagedSortedQuery(PageParam pageParam) {
        final Query<T> query = query()
            .setMaxRows(pageParam.getSize())
            .setFirstRow(pageParam.getOffset());
        whenModifiedOpt.ifPresent(it -> query.orderBy().desc(it));
        whenCreatedOpt.ifPresent(it -> query.orderBy().desc(it));
        return query;
    }

    public @NotNull List<T> allSorted() {
        return sortedQuery().findList();
    }

    public List<T> list(PageParam pageParam) {
        return query().setMaxRows(pageParam.getSize())
            .setFirstRow(pageParam.getOffset())
            .findList();
    }

    public List<T> listSorted(PageParam pageParam) {
        return sortedQuery().setMaxRows(pageParam.getSize())
            .setFirstRow(pageParam.getOffset())
            .findList();
    }

    public int count() {
        return query().findCount();
    }

    public Page<T> paged(PageParam pageParam) {
        final PagedList<T> pagedList = pagedQuery(pageParam).findPagedList();
        return Page.of(pagedList.getList(), pagedList.getTotalCount());
    }

    public Page<T> pagedSorted(PageParam pageParam) {
        final PagedList<T> pagedList = pagedSortedQuery(pageParam).findPagedList();
        return Page.of(pagedList.getList(), pagedList.getTotalCount());
    }
}