package com.flyback.oracle;

import com.flyback.Column;
import com.flyback.Constraint;
import com.flyback.Index;
import com.flyback.IndexColumn;
import com.flyback.IndexesForTableFactory;
import com.flyback.SaveableAsTextFile;
import com.flyback.Table;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class OracleTablesDdlExtractor {
    private final OracleConnectionFactory connectionFactory;
    private final String owner;
    private final OracleColumnsReader oracleColumnsReader;
    private final IndexesForTableFactory indexesForTableFactory;

    public OracleTablesDdlExtractor(String owner, String pwd, String connectionString) throws SQLException, ClassNotFoundException {
        connectionFactory = new OracleConnectionFactory(owner, pwd, connectionString);
        this.owner = owner;
        oracleColumnsReader = new OracleColumnsReader(connectionFactory);
        List<Index> allIndexes = getAllIndexes(connectionFactory);
        List<IndexColumn> allIndexColumns = getAllIndexColumnss(connectionFactory);
        indexesForTableFactory = new IndexesForTableFactory(allIndexes, allIndexColumns);
    }

    public List<SaveableAsTextFile> get() throws SQLException, ClassNotFoundException {
        List<String> tableNames = getTableNames();
        List<Constraint> constraints = getConstraints();
        return tableNames.stream().
                map(tableName -> getTable(tableName, constraints)).
                collect(Collectors.toList());
    }

    private Table getTable(String tableName, List<Constraint> constraints) {
        try {
            List<Column> columns = oracleColumnsReader.get(tableName);
            List<Index> indexes = indexesForTableFactory.get(tableName);
            return new Table(tableName, columns, constraints, indexes);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    private List<Constraint> getConstraints() throws SQLException, ClassNotFoundException {
        return new OracleConstraintsReader(connectionFactory).get(owner);
    }

    private List<String> getTableNames() throws SQLException, ClassNotFoundException {
        return new OracleTableNamesReader(connectionFactory).get(owner);
    }

    private List<Index> getAllIndexes(OracleConnectionFactory connectionFactory) throws SQLException, ClassNotFoundException {
        OracleIndexesReader oracleIndexesReader = new OracleIndexesReader(connectionFactory);
        return oracleIndexesReader.get();
    }

    private List<IndexColumn> getAllIndexColumnss(OracleConnectionFactory connectionFactory) throws SQLException, ClassNotFoundException {
        OracleIndexColumnsReader oracleIndexColumnsReader = new OracleIndexColumnsReader(connectionFactory);
        return oracleIndexColumnsReader.get();
    }
}
