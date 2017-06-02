package com.flyback;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class TableTests {
    @Test
    public void works(){
        String tableName = "THINGS";
        Collection<Column> columnsForTable = getColumns();
        Collection<Constraint> allConstraints = getConstraints();
        List<Index> indexList = getIndexes();
        Table table = new Table(tableName, columnsForTable, allConstraints, indexList);
        String tableDefinition = table.getDefinition();
        String expected = "CREATE TABLE THINGS(\n" +
                "    ID NUMBER NOT NULL,\n" +
                "    CONSTRAINT PK_THINGS PRIMARY KEY(ID),\n" +
                "    CATEGORY_ID NUMBER NOT NULL,\n" +
                "    CONSTRAINT FK_THINGS_CATEGORIES FOREIGN KEY(CATEGORY_ID) REFERENCES CATEGORIES(ID),\n" +
                "    NAME VARCHAR2(30) NOT NULL,\n" +
                "    CONSTRAINT UNQ_THINGS_NAME UNIQUE(NAME),\n" +
                "    CREATED_AT DATE NOT NULL,\n" +
                "    DELETED_AT DATE NULL,\n" +
                "    CONSTRAINT CHK_THINGS_DATE_RANGE CHECK(CREATED_AT<DELETED_AT)\n" +
                ");\n" +
                "\n" +
                "CREATE UNIQUE INDEX IDX__THINGS__NAME ON THINGS(NAME);\n" +
                "\n" +
                "CREATE INDEX IDX__THINGS__CREATED_AT ON THINGS(CREATED_AT, DELETED_AT);";
        Assert.assertEquals(expected, tableDefinition);
    }

    private Collection<Constraint> getConstraints() {
        TestConstraint testCheckConstraint = new TestConstraint("CHK_THINGS_DATE_RANGE", "THINGS", "CREATED_AT",
                "CONSTRAINT CHK_THINGS_DATE_RANGE CHECK(CREATED_AT<DELETED_AT)", 3);
        testCheckConstraint.addColumnName("DELETED_AT");
        TestConstraint primaryKeyForAnotherTable = new TestConstraint("PK_CATEGORIES", "CATEGORIES", "ID", "CONSTRAINT PK_CATEGORIES PRIMARY KEY(ID)", 0);
        TestConstraint uniqueForAnotherTable = new TestConstraint("UNQ_CATEGORIES_NAME", "CATEGORIES", "NAME", "CONSTRAINT UNQ_CATEGORIES_NAME UNIQUE(NAME)", 1);
        return Arrays.asList(
                new TestConstraint("PK_THINGS", "THINGS", "ID", "CONSTRAINT PK_THINGS PRIMARY KEY(ID)", 0),
                new TestConstraint("FK_THINGS_CATEGORIES", "THINGS", "CATEGORY_ID",
                        "CONSTRAINT FK_THINGS_CATEGORIES FOREIGN KEY(CATEGORY_ID) REFERENCES CATEGORIES(ID)", 2),
                new TestConstraint("UNQ_THINGS_NAME", "THINGS", "NAME", "CONSTRAINT UNQ_THINGS_NAME UNIQUE(NAME)", 1),
                testCheckConstraint,
                primaryKeyForAnotherTable,
                uniqueForAnotherTable
        );
    }

    private Collection<Column> getColumns() {
        return Arrays.asList(
                    new TestColumn("ID", "ID NUMBER NOT NULL"),
                    new TestColumn("CATEGORY_ID", "CATEGORY_ID NUMBER NOT NULL"),
                    new TestColumn("NAME", "NAME VARCHAR2(30) NOT NULL"),
                    new TestColumn("CREATED_AT", "CREATED_AT DATE NOT NULL"),
                    new TestColumn("DELETED_AT", "DELETED_AT DATE NULL")
            );
    }

    private List<Index> getIndexes(){
        return Arrays.asList(
                new TestIndex("IDX__THINGS__NAME", "THINGS", true, Collections.singletonList("NAME")),
                new TestIndex("IDX__THINGS__CREATED_AT", "THINGS", false, Arrays.asList("CREATED_AT", "DELETED_AT"))
        );
    }

    private class TestColumn implements Column{
        private final String name;
        private final String definition;

        private TestColumn(String name, String definition) {
            this.name = name;
            this.definition = definition;
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

    private class TestConstraint extends Constraint{
        private final String definition;
        private final int outputOrder;

        protected TestConstraint(String constraintName, String tableName, String columnName, String definition, int outputOrder) {
            super(constraintName, tableName, columnName, false);
            this.definition = definition;
            this.outputOrder = outputOrder;
        }

        @Override
        public String getDefinition() {
            return definition;
        }

        @Override
        public int getOutputOrder() {
            return outputOrder;
        }

    }

    private class TestIndex implements Index{
        private final String indexName;
        private final String tableName;
        private final boolean isUnique;
        private final List<String> columns;

        private TestIndex(String indexName, String tableName, boolean isUnique, List<String> columns) {
            this.indexName = indexName;
            this.tableName = tableName;
            this.isUnique = isUnique;
            this.columns = columns;
        }

        @Override
        public String getIndexName() {
            return indexName;
        }

        @Override
        public String getTableName() {
            return tableName;
        }

        @Override
        public boolean isUnique() {
            return isUnique;
        }

        @Override
        public void addColumn(String column) {

        }

        @Override
        public List<String> getColumns() {
            return columns;
        }
    }
}
