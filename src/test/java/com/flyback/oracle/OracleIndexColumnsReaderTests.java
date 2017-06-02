package com.flyback.oracle;

import com.flyback.ConnectionFactory;
import com.flyback.IndexColumn;
import org.junit.Ignore;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

public class OracleIndexColumnsReaderTests {
    @Ignore
    @Test
    public void works() throws SQLException, ClassNotFoundException {
        String owner = "****";
        String pwd = "****";
        String connectionString = "****";
        ConnectionFactory connectionFactory = new OracleConnectionFactory(owner, pwd, connectionString);
        OracleIndexColumnsReader reader = new OracleIndexColumnsReader(connectionFactory);
        List<IndexColumn> actual = reader.get();
        actual.forEach(System.out::println);
    }
}
