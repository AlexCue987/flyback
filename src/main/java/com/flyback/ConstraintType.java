package com.flyback;

import java.util.Collection;

public enum ConstraintType {
    C{
        @Override
        public Constraint get(String constraintName,
                              String tableName,
                              String searchCondition,
                              String rConstraintName,
                              String columnName,
                              Collection<Constraint> possibleConstraintsToReferTo,
                              boolean systemGeneratedName){
            return new CheckConstraint(constraintName, tableName, searchCondition, columnName, systemGeneratedName);
        }

        @Override
        public int getOutputOrder(){return 3;}
    },

    U{
        @Override
        public Constraint get(String constraintName,
                              String tableName,
                              String searchCondition,
                              String rConstraintName,
                              String columnName,
                              Collection<Constraint> possibleConstraintsToReferTo,
                              boolean systemGeneratedName){
            return new UniqueConstraint(constraintName, tableName, columnName, systemGeneratedName);
        }

        @Override
        public int getOutputOrder(){return 1;}
    },

    R{
        @Override
        public Constraint get(String constraintName,
                              String tableName,
                              String searchCondition,
                              String rConstraintName,
                              String columnName,
                              Collection<Constraint> possibleConstraintsToReferTo,
                              boolean systemGeneratedName){
            return new ForeignKeyConstraint(constraintName, tableName, rConstraintName, columnName,
                    possibleConstraintsToReferTo, systemGeneratedName);        }

        @Override
        public int getOutputOrder(){return 2;}
    },

    P{
        @Override
        public Constraint get(String constraintName,
                              String tableName,
                              String searchCondition,
                              String rConstraintName,
                              String columnName,
                              Collection<Constraint> possibleConstraintsToReferTo,
                              boolean systemGeneratedName){
            return new PrimaryKeyConstraint(constraintName, tableName, columnName, systemGeneratedName);
        }

        @Override
        public int getOutputOrder(){return 0;}
    };

    public abstract Constraint get(String constraintName,
                                   String tableName,
                                   String searchCondition,
                                   String rConstraintName,
                                   String columnName,
                                   Collection<Constraint> possibleConstraintsToReferTo,
                                   boolean systemGeneratedName);

    public abstract int getOutputOrder();
}

