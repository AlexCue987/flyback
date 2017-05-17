package com.flyback.oracle;

import com.flyback.ConnectionFactory;
import com.flyback.Constraint;
import com.flyback.ConstraintType;
import com.flyback.ConstraintsReader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OracleConstraintsReader implements ConstraintsReader {
    private final static String sql = "SELECT cons.CONSTRAINT_NAME, cons.CONSTRAINT_TYPE, cons.TABLE_NAME, \n" +
            "get_search_condition(cons.CONSTRAINT_NAME), \n" +
            "cons.R_CONSTRAINT_NAME, cols.COLUMN_NAME, cons.GENERATED\n" +
            "FROM all_constraints cons, all_cons_columns cols\n" +
            "WHERE cons.constraint_name = cols.constraint_name\n" +
            "AND cons.owner = cols.owner\n" +
            "AND cons.owner=?\n" +
            "ORDER BY \n" +
            "CASE WHEN cons.CONSTRAINT_TYPE='R' THEN 1 ELSE 0 END,\n" +
            "cons.Constraint_Name, cols.position";

    private final ConnectionFactory connectionFactory;

    public OracleConstraintsReader(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public List<Constraint> get(String owner) throws SQLException, ClassNotFoundException {
        try(Connection connection = connectionFactory.getConnection()){
            try(PreparedStatement select = connection.prepareStatement(sql)){
                select.setString(1, owner);
                try(ResultSet resultSet = select.executeQuery()){
                    return getConstraints(resultSet);
                }
            }
        }
    }

    static List<Constraint> getConstraints(ResultSet resultSet) throws SQLException {
        Map<String, Constraint> ret = new HashMap<>();
        while (resultSet.next()){
            String constraintName=resultSet.getString(1);
            String columnName=resultSet.getString(6);
            if(!ret.containsKey(constraintName)) {
                String constraintTypeStr=resultSet.getString(2);
                String tableName=resultSet.getString(3);
                String searchCondition=resultSet.getString(4);
                String rConstraintName=resultSet.getString(5);
                String generatedBy = resultSet.getString(7);
                boolean systemGeneratedName = generatedBy.equals("GENERATED NAME");
                if(isNotNullCheck(columnName, constraintTypeStr, searchCondition, systemGeneratedName)){
                    continue;
                }
                ConstraintType constraintType = ConstraintType.valueOf(constraintTypeStr);
                Constraint constraint = constraintType.get(constraintName, tableName, searchCondition,
                        rConstraintName, columnName, ret.values(), systemGeneratedName);
                ret.put(constraintName, constraint);
            } else {
                Constraint alreadyCreatedConstraint = ret.get(constraintName);
                alreadyCreatedConstraint.addColumnName(columnName);
            }
        }
        return new ArrayList<>(ret.values());
    }

    private static boolean isNotNullCheck(String columnName, String constraintTypeStr, String searchCondition, boolean systemGeneratedName) {
        return systemGeneratedName && constraintTypeStr.equals("C") &&
                searchCondition.equals("\""+columnName+"\" IS NOT NULL");
    }
}
