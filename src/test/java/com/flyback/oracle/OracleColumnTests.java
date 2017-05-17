package com.flyback.oracle;

import com.flyback.Column;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OracleColumnTests {
    private ResultSet resultSet;

    @Before
    public void setup(){
        resultSet = mock(ResultSet.class);
    }

    @Test
    public void getType_providesLengthForStrings(){
        long dataLength = 12L;
        Assert.assertEquals("VARCHAR2(12)", OracleColumn.getType("VARCHAR2", dataLength));
    }

    @Test
    public void getType_ignoresLengthForNonStrings(){
        long dataLength = 12L;
        Assert.assertEquals("NUMBER", OracleColumn.getType("NUMBER", dataLength));
    }

    @Test
    public void createsNullableColumn(){

    }

    @Test
    public void getDefaultDescription_handlesDefault(){
        Assert.assertEquals("DEFAULT (SYSDATE)", OracleColumn.getDefaultDescription("SYSDATE", "NO"));
    }

    @Test
    public void getDefaultDescription_handlesVirtualColumn(){
        Assert.assertEquals("AS(TO_CHAR(\"CREATED_AT\",'yyyy-mm-dd hh24:mi:ss'))",
                OracleColumn.getDefaultDescription("TO_CHAR(\"CREATED_AT\",'yyyy-mm-dd hh24:mi:ss')", "YES"));
    }

    @Test
    public void createsNullableColumnWithoutDefault() throws SQLException {
        when(resultSet.getString(1)).thenReturn("HEIGHT");
        when(resultSet.getString(2)).thenReturn("NUMBER");
        when(resultSet.getLong(3)).thenReturn(8L);
        when(resultSet.getString(4)).thenReturn("");
        when(resultSet.getLong(5)).thenReturn(0L);
        when(resultSet.getString(6)).thenReturn("NO");
        when(resultSet.getString(7)).thenReturn("Y");
        Column actual = new OracleColumn(resultSet);
        Assert.assertEquals("HEIGHT NUMBER NULL ", actual.getDefinition());
    }

    @Test
    public void createsNotNullableColumnWithoutDefault() throws SQLException {
        when(resultSet.getString(1)).thenReturn("HEIGHT");
        when(resultSet.getString(2)).thenReturn("NUMBER");
        when(resultSet.getLong(3)).thenReturn(8L);
        when(resultSet.getString(4)).thenReturn("");
        when(resultSet.getLong(5)).thenReturn(0L);
        when(resultSet.getString(6)).thenReturn("NO");
        when(resultSet.getString(7)).thenReturn("N");
        Column actual = new OracleColumn(resultSet);
        Assert.assertEquals("HEIGHT NUMBER NOT NULL ", actual.getDefinition());
    }
    @Test
    public void worksWithDefault() throws SQLException {
        when(resultSet.getString(1)).thenReturn("CREATED_AT");
        when(resultSet.getString(2)).thenReturn("DATE");
        when(resultSet.getLong(3)).thenReturn(8L);
        when(resultSet.getString(4)).thenReturn("SYSDATE");
        when(resultSet.getLong(5)).thenReturn(0L);
        when(resultSet.getString(6)).thenReturn("NO");
        when(resultSet.getString(7)).thenReturn("N");
        Column actual = new OracleColumn(resultSet);
        Assert.assertEquals("CREATED_AT DATE NOT NULL DEFAULT (SYSDATE)", actual.getDefinition());
    }

    @Test
    public void worksWithVirtualColumn() throws SQLException {
        when(resultSet.getString(1)).thenReturn("CREATED_AT_STR");
        when(resultSet.getString(2)).thenReturn("VARCHAR2");
        when(resultSet.getLong(3)).thenReturn(36L);
        when(resultSet.getString(4)).thenReturn("TO_CHAR(\"CREATED_AT\",'yyyy-mm-dd hh24:mi:ss')");
        when(resultSet.getLong(5)).thenReturn(0L);
        when(resultSet.getString(6)).thenReturn("YES");
        when(resultSet.getString(7)).thenReturn("N");
        Column actual = new OracleColumn(resultSet);
        Assert.assertEquals("CREATED_AT_STR VARCHAR2(36) NOT NULL AS(TO_CHAR(\"CREATED_AT\",'yyyy-mm-dd hh24:mi:ss'))",
                actual.getDefinition());
    }
}
