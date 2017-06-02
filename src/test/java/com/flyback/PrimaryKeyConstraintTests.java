package com.flyback;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class PrimaryKeyConstraintTests {
    ConstraintType type = ConstraintType.P;
    String constraintName = "PK_WHARFS";
    String tableName = "WHARFS";
    String searchCondition = null;
    String rConstraintName = null;
    String columnName = "PORT_ID";
    List<Constraint> constraints = null;

    @Test
    public void validDefinitionWithUserDefinedName(){
        boolean systemGeneratedName = false;
        Constraint constraint = type.get(constraintName, tableName, searchCondition, rConstraintName, columnName,
                constraints, systemGeneratedName);
        constraint.addColumnName("WHARF_NUMBER");
        String actual = constraint.getDefinition();
        Assert.assertEquals("CONSTRAINT PK_WHARFS PRIMARY KEY(PORT_ID, WHARF_NUMBER)", actual);
    }

    @Test
    public void validDefinitionWithSystemGeneratedName(){
        boolean systemGeneratedName = true;
        Constraint constraint = type.get(constraintName, tableName, searchCondition, rConstraintName, columnName,
                constraints, systemGeneratedName);
        constraint.addColumnName("WHARF_NUMBER");
        String actual = constraint.getDefinition();
        Assert.assertEquals("PRIMARY KEY(PORT_ID, WHARF_NUMBER)", actual);
    }
}
