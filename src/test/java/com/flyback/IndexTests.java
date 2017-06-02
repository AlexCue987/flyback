package com.flyback;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class IndexTests {
    @Test
    public void worksForOneColumn(){
        Index index = new TestIndex("IDX__THINGS__NAME", "THINGS", true, Collections.singletonList("NAME"));
        String actual = index.getDefinition();
        Assert.assertEquals("CREATE UNIQUE INDEX IDX__THINGS__NAME ON THINGS(NAME);", actual);
    }

    @Test
    public void worksForTwoColumns(){
        Index index = new TestIndex("IDX__THINGS__CREATED_AT", "THINGS", false, Arrays.asList("CREATED_AT", "DELETED_AT"));
        String actual = index.getDefinition();
        Assert.assertEquals("CREATE INDEX IDX__THINGS__CREATED_AT ON THINGS(CREATED_AT, DELETED_AT);", actual);
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
