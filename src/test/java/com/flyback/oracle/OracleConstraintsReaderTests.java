package com.flyback.oracle;

import com.flyback.Constraint;
import org.junit.Assert;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OracleConstraintsReaderTests {
    @Test
    public void getConstraints_worksForTwoColumnCheck() throws SQLException {
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        String constraintName = "CHK_TASKS";
        when(resultSet.getString(1)).thenReturn(constraintName).thenReturn(constraintName);
        when(resultSet.getString(6)).thenReturn("STARTED_AT").thenReturn("FINISHED_AT");
        when(resultSet.getString(7)).thenReturn("USER NAME").thenReturn("USER NAME");
        when(resultSet.getString(2)).thenReturn("C");
        when(resultSet.getString(3)).thenReturn("TASKS");
        when(resultSet.getString(4)).thenReturn("STARTED_AT<FINISHED_AT");
        when(resultSet.getString(5)).thenReturn(null);
        List<Constraint> actual = OracleConstraintsReader.getConstraints(resultSet);
        String actualStr = actual.stream().map(Constraint::getDefinition).collect(Collectors.joining("\n"));
//        System.out.println(actualStr);
        String expectedStr = "CONSTRAINT CHK_TASKS CHECK(STARTED_AT<FINISHED_AT)";
        Assert.assertEquals(expectedStr, actualStr);
    }

    @Test
    public void getConstraints_linksForeignKeyToReferredConstraint() throws SQLException {
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        String primaryKeyName = "PK_THINGS";
        when(resultSet.getString(1)).thenReturn(primaryKeyName).thenReturn("FK_ITEMS_THINGS");
        when(resultSet.getString(6)).thenReturn("ID").thenReturn("THING_ID");
        when(resultSet.getString(7)).thenReturn("USER NAME").thenReturn("USER NAME");
        when(resultSet.getString(2)).thenReturn("P").thenReturn("R");
        when(resultSet.getString(3)).thenReturn("THINGS").thenReturn("PARTS");
        when(resultSet.getString(4)).thenReturn(null).thenReturn(null);
        when(resultSet.getString(5)).thenReturn(null).thenReturn(primaryKeyName);
        List<Constraint> actual = OracleConstraintsReader.getConstraints(resultSet);
        String actualStr = actual.stream().map(Constraint::getDefinition).collect(Collectors.joining("\n"));
//        System.out.println(actualStr);
        String expectedStr = "CONSTRAINT PK_THINGS PRIMARY KEY(ID)\n" +
                "CONSTRAINT FK_ITEMS_THINGS FOREIGN KEY(THING_ID) REFERENCES THINGS(ID)";
        Assert.assertEquals(expectedStr, actualStr);
    }
}
