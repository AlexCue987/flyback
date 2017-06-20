package com.flyback.oracle;

import com.flyback.ConnectionFactory;
import com.flyback.SaveableAsTextFile;
import com.flyback.View;
import com.flyback.ViewsReader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OracleViewsReader implements ViewsReader {
    private final static String sql = "SELECT view_name, get_view_definition(view_name) FROM USER_VIEWS";

    private final ConnectionFactory connectionFactory;

    public OracleViewsReader(String owner, String pwd, String connectionString) {
        this.connectionFactory = new OracleConnectionFactory(owner, pwd, connectionString);
    }

    @Override
    public List<SaveableAsTextFile> get() throws SQLException, ClassNotFoundException {
        try(Connection connection = connectionFactory.getConnection()){
            try(PreparedStatement select = connection.prepareStatement(sql)){
                try(ResultSet resultSet = select.executeQuery()){
                    return getViews(resultSet);
                }
            }
        }
    }

    List<SaveableAsTextFile> getViews(ResultSet resultSet) throws SQLException{
        List<SaveableAsTextFile> ret = new ArrayList<>();
        while(resultSet.next()){
            String name = resultSet.getString(1);
            String definition = resultSet.getString(2);
            View view = new View(name, definition);
            ret.add(view);
        }
        return ret;
    }
}
