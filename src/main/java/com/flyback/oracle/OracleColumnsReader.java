package com.flyback.oracle;

import com.flyback.Column;
import com.flyback.ColumnsReader;
import com.flyback.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OracleColumnsReader implements ColumnsReader {
    private final static String sql = "SELECT COLUMN_NAME,DATA_TYPE,DATA_LENGTH,DATA_DEFAULT," +
            "CHAR_LENGTH,VIRTUAL_COLUMN,NULLABLE\n" +
            "FROM all_tab_cols\n" +
            "WHERE table_name =? AND HIDDEN_COLUMN='NO'\n" +
            "ORDER BY COLUMN_ID";

    private final ConnectionFactory connectionFactory;

    public OracleColumnsReader(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public List<Column> get(String tableName) throws SQLException, ClassNotFoundException {
        List<Column> ret = new ArrayList<>();
        try(Connection connection = connectionFactory.getConnection()){
            try(PreparedStatement select = connection.prepareStatement(sql)){
                select.setString(1, tableName);
                try(ResultSet rs = select.executeQuery()){
                    while (rs.next()){
                        Column column = new OracleColumn(rs);
                        ret.add(column);
                    }
                }
            }
        }
        return ret;
    }
}
