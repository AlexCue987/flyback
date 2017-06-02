package com.flyback.oracle;

import com.flyback.ConnectionFactory;
import com.flyback.Index;
import com.flyback.IndexesReader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OracleIndexesReader implements IndexesReader {
    private final static String sql = "SELECT INDEX_NAME, INDEX_TYPE, TABLE_NAME,UNIQUENESS \n" +
            "FROM USER_INDEXES\n" +
            "WHERE INDEX_NAME NOT IN(SELECT CONSTRAINT_NAME FROM USER_CONSTRAINTS WHERE CONSTRAINT_TYPE IN('P','U'))\n" +
            "AND INDEX_TYPE IN('FUNCTION-BASED NORMAL', 'NORMAL')\n";

    private final ConnectionFactory connectionFactory;

    public OracleIndexesReader(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public List<Index> get() throws SQLException, ClassNotFoundException {
        try(Connection connection = connectionFactory.getConnection()){
            try(PreparedStatement select = connection.prepareStatement(sql)){
                try(ResultSet resultSet = select.executeQuery()){
                    return getIndexes(resultSet);
                }
            }
        }
    }

    static List<Index> getIndexes(ResultSet resultSet) throws SQLException {
        List<Index> ret = new ArrayList<>();
        while (resultSet.next()){
            String indexName=resultSet.getString(1);
            String indexType=resultSet.getString(2);
            String tableName=resultSet.getString(3);
            String uniqueness=resultSet.getString(4);
            Index index = new OracleIndex(indexName, indexType, tableName, uniqueness);
            ret.add(index);
        }
        return ret;
    }
}
