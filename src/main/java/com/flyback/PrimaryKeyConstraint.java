package com.flyback;

public class PrimaryKeyConstraint extends Constraint {
    protected PrimaryKeyConstraint(String constraintName, String tableName, String columnName, boolean systemGeneratedName) {
        super(constraintName, tableName, columnName, systemGeneratedName);
    }

    @Override
    public String getDefinition() {
        return String.format("%sPRIMARY KEY(%s)", getOptionalName(), getCommaSeparatedColumnsList());
    }

    @Override
    public int getOutputOrder() {
        return 0;
    }

}
