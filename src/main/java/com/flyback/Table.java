package com.flyback;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Table implements SaveableAsTextFile {
    private final List<HasDefinition> columnsAndConstraints;
    private final String tableName;
    private final List<Index> indexes;

    public Table(String tableName,
                 Collection<Column> columnsForTable,
                 Collection<Constraint> allConstraints,
                 List<Index> indexes) {
        this.tableName = tableName;
        this.indexes = indexes;
        Map<String, Constraint> constraintsForTable = allConstraints.stream().
                filter(c -> c.getTableName().equals(tableName)).
                collect(Collectors.toMap(Constraint::getConstraintName, Function.identity()));
        List<String> processedColumnNames = new ArrayList<>();
        columnsAndConstraints = new ArrayList<>();
        for(Column column: columnsForTable){
            addColumn(processedColumnNames, column);
            addConstraintsWithAllColumnsAdded(constraintsForTable, processedColumnNames);
        }
    }

    private void addConstraintsWithAllColumnsAdded(Map<String, Constraint> constraintsForTable, List<String> processedColumnNames) {
        List<Constraint> constraintsToProcess = getConstraintsToProcess(constraintsForTable, processedColumnNames);
        columnsAndConstraints.addAll(constraintsToProcess);
        constraintsToProcess.forEach(constraint -> constraintsForTable.remove(constraint.getConstraintName()));
    }

    private void addColumn(List<String> processedColumnNames, Column column) {
        columnsAndConstraints.add(column);
        processedColumnNames.add(column.getName());
    }

    static List<Constraint> getConstraintsToProcess(Map<String, Constraint> constraintsForTable, List<String> processedColumnNames) {
        List<Constraint> constraintsToProcess = constraintsForTable.values().stream().
                filter(constraint -> allColumnsForConstraintProcessed(constraint, processedColumnNames)).
                collect(Collectors.toList());
        Collections.sort(constraintsToProcess);
        return constraintsToProcess;
    }

    static boolean allColumnsForConstraintProcessed(Constraint constraint, List<String> processedColumnNames){
        return constraint.getColumnNames().stream().allMatch(processedColumnNames::contains);
    }

    public String getDefinition(){
        final String tableDefinitionFormat = "CREATE TABLE %s(\n    %s\n);\n\n%s";
        String commaSeparatedColumnsAndConstraints = columnsAndConstraints.stream().
                map(HasDefinition::getDefinition).
                collect(Collectors.joining(",\n    "));
        String lineSeparatedIndexes = indexes.stream().
                map(HasDefinition::getDefinition).
                collect(Collectors.joining("\n\n"));
        return String.format(tableDefinitionFormat, tableName, commaSeparatedColumnsAndConstraints, lineSeparatedIndexes);
    }

    public String getFileName(){
        return tableName+".sql";
    }

    public String getFileContents(){
        return getDefinition();
    }
}
