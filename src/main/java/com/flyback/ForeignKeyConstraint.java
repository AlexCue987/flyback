package com.flyback;

import java.util.Collection;
import java.util.Optional;

public class ForeignKeyConstraint extends Constraint{
    private final Constraint referencedConstraint;

    protected ForeignKeyConstraint(String constraintName,
                                   String tableName,
                                   String rConstraintName,
                                   String columnName,
                                   Collection<Constraint> constraints,
                                   boolean systemGeneratedName) {
        super(constraintName, tableName, columnName, systemGeneratedName);
        Optional<Constraint> referencedSearch = constraints.stream().
                filter(constraint -> constraint.getConstraintName().equals(rConstraintName)).
                findAny();
        if(!referencedSearch.isPresent()){
            throw new RuntimeException("Cannot find constraint " + rConstraintName);
        }
        referencedConstraint = referencedSearch.get();
    }

    @Override
    public String getDefinition() {
        return String.format("%sFOREIGN KEY(%s) REFERENCES %s(%s)",
                getOptionalName(),
                getCommaSeparatedColumnsList(),
                referencedConstraint.getTableName(),
                referencedConstraint.getCommaSeparatedColumnsList());
    }

    @Override
    public int getOutputOrder() {
        return 2;
    }

}
