package com.flyback.oracle;

import com.flyback.ConnectionFactory;
import com.flyback.TableNamesReader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OracleTableNamesReader implements TableNamesReader {
    private final static String sql = "select table_name from all_tables where owner=?\n" +
            "and table_name <> 'schema_version'";

    private final ConnectionFactory connectionFactory;

    public OracleTableNamesReader(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public List<String> get(String owner) throws SQLException, ClassNotFoundException {
        List<String> ret = new ArrayList<>();
        try(Connection connection = connectionFactory.getConnection()){
            try(PreparedStatement select = connection.prepareStatement(sql)){
                select.setString(1, owner);
                try(ResultSet rs = select.executeQuery()){
                    while (rs.next()){
                        ret.add(rs.getString(1));
                    }
                }
            }
        }
        return ret;
    }
}
