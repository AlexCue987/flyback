package com.flyback;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class UniqueConstraintTests {
    ConstraintType type = ConstraintType.U;
    String constraintName = "UNQ_TASKS";
    String tableName = "TASKS";
    String searchCondition = null;
    String rConstraintName = null;
    String columnName = "STARTED_AT";
    List<Constraint> constraints = null;

    @Test
    public void validDefinitionWithUserDefinedName(){
        boolean systemGeneratedName = false;
        Constraint constraint = type.get(constraintName, tableName, searchCondition, rConstraintName, columnName,
                constraints, systemGeneratedName);
        constraint.addColumnName("REASON");
        String actual = constraint.getDefinition();
        Assert.assertEquals("CONSTRAINT UNQ_TASKS UNIQUE(STARTED_AT, REASON)", actual);
    }

    @Test
    public void validDefinitionWithSystemGeneratedName(){
        boolean systemGeneratedName = true;
        Constraint constraint = type.get(constraintName, tableName, searchCondition, rConstraintName, columnName,
                constraints, systemGeneratedName);
        constraint.addColumnName("REASON");
        String actual = constraint.getDefinition();
        Assert.assertEquals("UNIQUE(STARTED_AT, REASON)", actual);
    }
}
