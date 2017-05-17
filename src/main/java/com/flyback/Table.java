package com.flyback;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Table implements SaveableAsTextFile {
    private final List<HasDefinition> itemDefinitions;
    private final String tableName;

    public Table(String tableName,
                 Collection<Column> columnsForTable,
                 Collection<Constraint> allConstraints) {
        this.tableName = tableName;
        Map<String, Constraint> constraintsForTable = allConstraints.stream().
                filter(c -> c.getTableName().equals(tableName)).
                collect(Collectors.toMap(Constraint::getConstraintName, Function.identity()));
        List<String> processedColumnNames = new ArrayList<>();
        itemDefinitions = new ArrayList<>();
        for(Column column: columnsForTable){
            addColumn(processedColumnNames, column);
            addConstraintsWithAllColumnsAdded(constraintsForTable, processedColumnNames);
        }
    }

    private void addConstraintsWithAllColumnsAdded(Map<String, Constraint> constraintsForTable, List<String> processedColumnNames) {
        List<Constraint> constraintsToProcess = getConstraintsToProcess(constraintsForTable, processedColumnNames);
        itemDefinitions.addAll(constraintsToProcess);
        constraintsToProcess.forEach(constraint -> constraintsForTable.remove(constraint.getConstraintName()));
    }

    private void addColumn(List<String> processedColumnNames, Column column) {
        itemDefinitions.add(column);
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

    List<HasDefinition> getItemDefinitions() {
        return itemDefinitions;
    }

    public String getDefinition(){
        final String tableDefinitionFormat = "CREATE TABLE %s(\n    %s\n);";
        String commaSeparatedItems = itemDefinitions.stream().
                map(HasDefinition::getDefinition).
                collect(Collectors.joining(",\n    "));
        return String.format(tableDefinitionFormat, tableName, commaSeparatedItems);
    }

    public String getFileName(){
        return tableName+".sql";
    }

    public String getFileContents(){
        return getDefinition();
    }
}
