package cn.javaer.jany.jooq.codegen;

/**
 * @author cn-src
 */
public class TablesGenerator extends OverrideGenerator {
    @Override
    public boolean generateUDTs() {
        return false;
    }

    @Override
    public boolean generateRoutines() {
        return false;
    }

    @Override
    public boolean generateTableValuedFunctions() {
        return false;
    }

    @Override
    public boolean generateRecords() {
        return false;
    }

    @Override
    public boolean generateSequences() {
        return false;
    }

    @Override
    public boolean generateIndexes() {
        return false;
    }
}