package com.flyback;

import java.util.List;
import java.util.stream.Collectors;

public interface Index extends HasDefinition{
    String getIndexName();

    String getTableName();

    boolean isUnique();

    void addColumn(String column);

    List<String> getColumns();

    @Override
    default String getDefinition(){
        String commaSeparatedColumns = getColumns().stream().collect(Collectors.joining(", "));
        return String.format("CREATE %sINDEX %s ON %s(%s);",
                isUnique() ? "UNIQUE " : "",
                getIndexName(),
                getTableName(),
                commaSeparatedColumns);
    }
}
