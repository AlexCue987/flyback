package com.flyback;

import java.util.List;
import java.util.stream.Collectors;

public class IndexesForTableFactory {
    private final List<Index> allIndexes;
    private final List<IndexColumn> allIndexColumns;

    public IndexesForTableFactory(List<Index> allIndexes, List<IndexColumn> allIndexColumns) {
        this.allIndexes = allIndexes;
        this.allIndexColumns = allIndexColumns;
    }

    public List<Index> get(String tableName){
        List<Index> indexesForTable = allIndexes.stream().
                filter(index -> index.getTableName().equals(tableName)).
                sorted((a, b) -> a.getIndexName().compareTo(b.getIndexName())).
                collect(Collectors.toList());
        for(Index index: indexesForTable){
            addColumnsToIndex(index);
        }
        return indexesForTable;
    }

    void addColumnsToIndex(Index index) {
        List<IndexColumn> columnsForIndex = allIndexColumns.stream().
                filter(column -> column.getIndexName().equals(index.getIndexName())).
                collect(Collectors.toList());
        columnsForIndex.sort((a, b) -> a.getColumnPosition().compareTo(b.getColumnPosition()));
        columnsForIndex.forEach(column -> index.addColumn(column.getColumnName()));
    }
}
