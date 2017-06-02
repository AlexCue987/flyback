package com.flyback.oracle;

import com.flyback.IndexColumn;

public class OracleIndexColumn implements IndexColumn {
    private final String indexName;
    private final String columnName;
    private final Long columnPosition;

    public OracleIndexColumn(String indexName, String columnName, Long columnPosition) {
        this.indexName = indexName;
        this.columnName = columnName;
        this.columnPosition = columnPosition;
    }

    @Override
    public String toString() {
        return "OracleIndexColumn{" +
                "indexName='" + indexName + '\'' +
                ", columnName='" + columnName + '\'' +
                ", columnPosition=" + columnPosition +
                '}';
    }

    @Override
    public String getIndexName() {
        return indexName;
    }

    @Override
    public String getColumnName() {
        return columnName;
    }

    @Override
    public Long getColumnPosition() {
        return columnPosition;
    }


}
