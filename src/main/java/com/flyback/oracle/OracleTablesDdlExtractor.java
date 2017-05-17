package com.flyback.oracle;

import com.flyback.Column;
import com.flyback.Constraint;
import com.flyback.SaveableAsTextFile;
import com.flyback.Table;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class OracleTablesDdlExtractor {
    private final OracleConnectionFactory connectionFactory;
    private final String owner;
    private final OracleColumnsReader oracleColumnsReader;

    public OracleTablesDdlExtractor(String owner, String pwd, String connectionString){
        connectionFactory = new OracleConnectionFactory(owner, pwd, connectionString);
        this.owner = owner;
        oracleColumnsReader = new OracleColumnsReader(connectionFactory);
    }

    public List<SaveableAsTextFile> get() throws SQLException, ClassNotFoundException {
        List<String> tableNames = getTableNames(owner);
        List<Constraint> constraints = getConstraints();
        return tableNames.stream().
                map(tableName -> getTable(tableName, constraints)).
                collect(Collectors.toList());
    }

    private Table getTable(String tableName, List<Constraint> constraints) {
        try {
            List<Column> columns = oracleColumnsReader.get(tableName);
            return new Table(tableName, columns, constraints);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    private List<Constraint> getConstraints() throws SQLException, ClassNotFoundException {
        return new OracleConstraintsReader(connectionFactory).get(owner);
    }

    private List<String> getTableNames(String owner) throws SQLException, ClassNotFoundException {
        return new OracleTableNamesReader(connectionFactory).get(owner);
    }
}
