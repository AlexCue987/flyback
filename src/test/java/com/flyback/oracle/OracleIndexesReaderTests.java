package com.flyback.oracle;

import com.flyback.ConnectionFactory;
import com.flyback.Index;
import org.junit.Ignore;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

public class OracleIndexesReaderTests {
    @Ignore
    @Test
    public void works() throws SQLException, ClassNotFoundException {
        String owner = "****";
        String pwd = "****";
        String connectionString = "*****";
        ConnectionFactory connectionFactory = new OracleConnectionFactory(owner, pwd, connectionString);
        OracleIndexesReader reader = new OracleIndexesReader(connectionFactory);
        List<Index> actual = reader.get();
        actual.forEach(System.out::println);
    }
}
