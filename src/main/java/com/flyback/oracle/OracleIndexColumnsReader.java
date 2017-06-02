package com.flyback.oracle;

import com.flyback.ConnectionFactory;
import com.flyback.IndexColumn;
import com.flyback.IndexColumnsReader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OracleIndexColumnsReader implements IndexColumnsReader {
    private final static String sql = "SELECT UIC.INDEX_NAME,UIC.COLUMN_NAME,UIC.COLUMN_POSITION \n" +
            "FROM USER_IND_COLUMNS UIC \n" +
            "  JOIN USER_INDEXES UI ON UI.INDEX_NAME=UIC.INDEX_NAME\n" +
            "WHERE UI.INDEX_TYPE='NORMAL'\n" +
            "UNION ALL\n" +
            "SELECT UIE.INDEX_NAME,get_column_expression(index_name, column_position) COLUMN_NAME,UIE.COLUMN_POSITION FROM USER_IND_EXPRESSIONS UIE";

    private final ConnectionFactory connectionFactory;

    public OracleIndexColumnsReader(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public List<IndexColumn> get() throws SQLException, ClassNotFoundException {
        try(Connection connection = connectionFactory.getConnection()){
            try(PreparedStatement select = connection.prepareStatement(sql)){
                try(ResultSet resultSet = select.executeQuery()){
                    return getIndexColumns(resultSet);
                }
            }
        }
    }

    static List<IndexColumn> getIndexColumns(ResultSet resultSet) throws SQLException {
        List<IndexColumn> ret = new ArrayList<>();
        while (resultSet.next()){
            String indexName=resultSet.getString(1);
            String columnName=resultSet.getString(2);
            Long columnPosition=resultSet.getLong(3);
            IndexColumn indexColumn = new OracleIndexColumn(indexName, columnName, columnPosition);
            ret.add(indexColumn);
        }
        return ret;
    }
}
