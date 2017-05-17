package com.flyback;

public class UniqueConstraint extends Constraint {

    protected UniqueConstraint(String constraintName, String tableName, String columnName, boolean systemGeneratedName) {
        super(constraintName, tableName, columnName, systemGeneratedName);
    }

    @Override
    public String getDefinition() {
        return String.format("%sUNIQUE(%s)", getOptionalName(), getCommaSeparatedColumnsList());
    }

    @Override
    public int getOutputOrder() {
        return 1;
    }

}
