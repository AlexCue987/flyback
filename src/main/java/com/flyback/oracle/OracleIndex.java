package com.flyback.oracle;

import com.flyback.Index;

import java.util.ArrayList;
import java.util.List;

public class OracleIndex implements Index {
    private final String indexName;
    private final String indexType;
    private final String tableName;
    private final boolean unique;
    private final List<String> columns;

    public OracleIndex(String indexName, String indexType, String tableName, String uniqueness) {
        this.indexName = indexName;
        this.indexType = indexType;
        this.tableName = tableName;
        this.unique = uniqueness.equals("UNIQUE");
        columns = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "OracleIndex{" +
                "indexName='" + indexName + '\'' +
                ", indexType='" + indexType + '\'' +
                ", tableName='" + tableName + '\'' +
                ", unique=" + unique +
                '}';
    }

    @Override
    public String getIndexName() {
        return indexName;
    }

    public String getIndexType() {
        return indexType;
    }

    @Override
    public String getTableName() {
        return tableName;
    }

    @Override
    public boolean isUnique() {
        return unique;
    }

    @Override
    public void addColumn(String column){
        columns.add(column);
    }

    @Override
    public List<String> getColumns() {
        return columns;
    }
}
