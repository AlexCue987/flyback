package com.flyback.oracle;

import com.flyback.Index;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString
public class OracleIndex implements Index {
    @NonNull
    private final String indexName;
    @NonNull
    private final String indexType;
    @NonNull
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
    public void addColumn(String column){
        columns.add(column);
    }
}
