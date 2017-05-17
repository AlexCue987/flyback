package com.flyback;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Constraint implements HasDefinition, Comparable<Constraint>{
    protected final String constraintName;
    protected final String tableName;
    private final List<String> columnNames;
    protected final boolean systemGeneratedName;

    protected Constraint(String constraintName, String tableName, String columnName, boolean systemGeneratedName) {
        this.constraintName = constraintName;
        this.tableName = tableName;
        this.systemGeneratedName = systemGeneratedName;
        this.columnNames = new ArrayList<>();
        this.columnNames.add(columnName);
    }

    @Override
    public abstract String getDefinition();

    protected String getOptionalName(){
        return systemGeneratedName ? "" : "CONSTRAINT " + constraintName + " ";
    }

    public abstract int getOutputOrder();

    public void addColumnName(String columnName){columnNames.add(columnName);}

    public String getConstraintName() {
        return constraintName;
    }

    public String getTableName() {
        return tableName;
    }

    public List<String> getColumnNames() {
        return columnNames;
    }

    protected String getCommaSeparatedColumnsList(){
        return getColumnNames().stream().collect(Collectors.joining(","));
    }

    private String getComparisonKey(){
        return String.valueOf(getOutputOrder()) + " " + getConstraintName();
    }

    @Override
    public int compareTo(Constraint o){
        return getComparisonKey().compareTo(o.getComparisonKey());
    }
}
