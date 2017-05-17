package com.flyback;

public class CheckConstraint extends Constraint {
    private final String searchCondition;

    public CheckConstraint(String constraintName,
                           String tableName,
                           String searchCondition,
                           String columnName,
                           boolean systemGeneratedName){
        super(constraintName, tableName, columnName, systemGeneratedName);
        this.searchCondition = searchCondition;
    }

    @Override
    public String getDefinition() {
        return String.format("%sCHECK(%s)", getOptionalName(), searchCondition);
    }

    @Override
    public int getOutputOrder() {
        return 3;
    }

    @Override
    public String toString() {
        return "CheckConstraint{" +
                "searchCondition='" + searchCondition + '\'' +
                '}';
    }
}
