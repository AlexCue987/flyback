package com.flyback;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class CheckConstraintTests {
    ConstraintType type = ConstraintType.C;
    String constraintName = "CHK_TASKS";
    String tableName = "TASKS";
    String searchCondition = "STARTED_AT <= ENDED_AT";
    String rConstraintName = null;
    String columnName = "STARTED_AT";
    List<Constraint> constraints = null;

    @Test
    public void validDefinitionWithUserDefinedName() {
        boolean systemGeneratedName = false;
        Constraint constraint = type.get(constraintName, tableName, searchCondition, rConstraintName, columnName,
                constraints, systemGeneratedName);
        String actual = constraint.getDefinition();
        Assert.assertEquals("CONSTRAINT CHK_TASKS CHECK(STARTED_AT <= ENDED_AT)", actual);
    }

    @Test
    public void validDefinitionWithSystemGeneratedName() {
        boolean systemGeneratedName = true;
        Constraint constraint = type.get(constraintName, tableName, searchCondition, rConstraintName, columnName,
                constraints, systemGeneratedName);
        String actual = constraint.getDefinition();
        Assert.assertEquals("CHECK(STARTED_AT <= ENDED_AT)", actual);
    }
}
