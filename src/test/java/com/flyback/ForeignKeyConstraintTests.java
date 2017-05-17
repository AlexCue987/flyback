package com.flyback;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class ForeignKeyConstraintTests {
    List<Constraint> constraints = null;
    ConstraintType pkType = ConstraintType.P;
    String constraintName = "FK_BOATS";
    String tableName = "BOATS";
    String searchCondition = null;
    String rConstraintName = "PK_SHIPS";
    String columnName = "SHIP_ID";

    @Test
    public void validDefinitionWithUserDefinedName(){
        List<Constraint> constraintsToReferTo = Arrays.asList(getPkConstraint1(), getPkConstraint2());
        boolean systemGeneratedName = false;
        Constraint constraint = ConstraintType.R.get(constraintName, tableName, searchCondition,
                rConstraintName, columnName, constraintsToReferTo, systemGeneratedName);
        String actual = constraint.getDefinition();
        Assert.assertEquals("CONSTRAINT FK_BOATS FOREIGN KEY(SHIP_ID) REFERENCES SHIPS(ID)", actual);
    }

    @Test
    public void validDefinitionWithSystemGeneratedName(){
        List<Constraint> constraintsToReferTo = Arrays.asList(getPkConstraint1(), getPkConstraint2());
        boolean systemGeneratedName = true;
        Constraint constraint = ConstraintType.R.get(constraintName, tableName, searchCondition,
                rConstraintName, columnName, constraintsToReferTo, systemGeneratedName);
        String actual = constraint.getDefinition();
        Assert.assertEquals("FOREIGN KEY(SHIP_ID) REFERENCES SHIPS(ID)", actual);
    }

    public Constraint getPkConstraint1() {
        String constraintName = "PK_SHIPS";
        String tableName = "SHIPS";
        String columnName = "ID";
        return pkType.get(constraintName, tableName, searchCondition, rConstraintName, columnName, constraints, false);
    }

    public Constraint getPkConstraint2() {
        String constraintName = "PK_PORTS";
        String tableName = "PORTS";
        String columnName = "ID";
        return pkType.get(constraintName, tableName, searchCondition, rConstraintName, columnName, constraints, false);
    }
}
