/*
 * This file is generated by jOOQ.
 */
package cn.javaer.jany.spring.jooq.gen.tables;

import cn.javaer.jany.spring.jooq.gen.Indexes;
import cn.javaer.jany.spring.jooq.gen.Keys;
import cn.javaer.jany.spring.jooq.gen.Public;
import cn.javaer.jany.spring.jooq.gen.tables.records.CityRecord;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row2;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;

import javax.annotation.Generated;
import java.util.Arrays;
import java.util.List;

/**
 * This class is generated by jOOQ.
 */
@Generated(
        value = {
                "http://www.jooq.org",
                "jOOQ version:3.12.1"
        },
        comments = "This class is generated by jOOQ"
)
public class City extends TableImpl<CityRecord> {

    private static final long serialVersionUID = -484834304;

    /**
     * The reference instance of <code>PUBLIC.CITY</code>
     */
    public static final City CITY = new City();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<CityRecord> getRecordType() {
        return CityRecord.class;
    }

    /**
     * The column <code>PUBLIC.CITY.ID</code>.
     */
    public final TableField<CityRecord, Integer> ID = createField(DSL.name("ID"), org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>PUBLIC.CITY.NAME</code>.
     */
    public final TableField<CityRecord, String> NAME = createField(DSL.name("NAME"), org.jooq.impl.SQLDataType.VARCHAR(30), this, "");

    /**
     * Create a <code>PUBLIC.CITY</code> table reference
     */
    public City() {
        this(DSL.name("CITY"), null);
    }

    /**
     * Create an aliased <code>PUBLIC.CITY</code> table reference
     */
    public City(String alias) {
        this(DSL.name(alias), CITY);
    }

    /**
     * Create an aliased <code>PUBLIC.CITY</code> table reference
     */
    public City(Name alias) {
        this(alias, CITY);
    }

    private City(Name alias, Table<CityRecord> aliased) {
        this(alias, aliased, null);
    }

    private City(Name alias, Table<CityRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> City(Table<O> child, ForeignKey<O, CityRecord> key) {
        super(child, key, CITY);
    }

    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.PRIMARY_KEY_1);
    }

    @Override
    public Identity<CityRecord, Integer> getIdentity() {
        return Keys.IDENTITY_CITY;
    }

    @Override
    public UniqueKey<CityRecord> getPrimaryKey() {
        return Keys.CONSTRAINT_1;
    }

    @Override
    public List<UniqueKey<CityRecord>> getKeys() {
        return Arrays.<UniqueKey<CityRecord>>asList(Keys.CONSTRAINT_1);
    }

    @Override
    public City as(String alias) {
        return new City(DSL.name(alias), this);
    }

    @Override
    public City as(Name alias) {
        return new City(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public City rename(String name) {
        return new City(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public City rename(Name name) {
        return new City(name, null);
    }

    // -------------------------------------------------------------------------
    // Row2 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row2<Integer, String> fieldsRow() {
        return (Row2) super.fieldsRow();
    }
}