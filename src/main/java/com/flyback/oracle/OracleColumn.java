package com.flyback.oracle;

import com.flyback.Column;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OracleColumn implements Column {

    private final String name;
    private final String definition;

    public OracleColumn(ResultSet resultSet) throws SQLException {
        name=resultSet.getString(1);
        String dataType=resultSet.getString(2);
        Long dataLength=resultSet.getLong(3);
        String dataDefault=resultSet.getString(4);
        Long charLength=resultSet.getLong(5);
        String virtualColumn=resultSet.getString(6);
        String nullable=resultSet.getString(7);
        String nullDescription = nullable.equals("N") ? "NOT " : "";
        String type = getType(dataType, dataLength);
        String defaultDescription = getDefaultDescription(dataDefault, virtualColumn);
        definition = String.format("%s %s %sNULL %s", name, type, nullDescription, defaultDescription);
    }

    static String getDefaultDescription(String dataDefault, String virtualColumn) {
        String defaultDescription = "";
        if(virtualColumn.equals("YES")){
            defaultDescription = String.format("AS(%s)", dataDefault);
            //there is a method in StringUtils which does that,
            //but we do not want extra dependencies
        }else if(!(dataDefault == null || dataDefault.equals(""))){
            defaultDescription = String.format("DEFAULT (%s)", dataDefault);
        }
        return defaultDescription;
    }

    static String getType(String dataType, Long dataLength) {
        String type;
        if(dataType.contains("VARCHAR")){
            type = String.format("%s(%s)", dataType, dataLength);
        }else{
            type = dataType;
        }
        return type;
    }

    @Override
    public String toString() {
        return "Column{" +
                "name='" + name + '\'' +
                ", definition='" + definition + '\'' +
                '}';
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDefinition() {
        return definition;
    }

}
