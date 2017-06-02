package com.flyback;

import com.flyback.oracle.OracleIndex;
import com.flyback.oracle.OracleIndexColumn;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class IndexesForTableFactoryTests {

    public static final String FORMS = "FORMS";
    public static final String SHAPES = "SHAPES";

    List<IndexColumn> allIndexColumns = Arrays.asList(
            new OracleIndexColumn("IDX_SHAPES1", "HEIGHT", 3L),
            new OracleIndexColumn("IDX_SHAPES2", "BARCODE", 1L),
            new OracleIndexColumn("IDX_SHAPES1", "WIDTH", 2L),
            new OracleIndexColumn("IDX_SHAPES1", "LENGTH", 1L),
            new OracleIndexColumn("IDX_FORMS1", "COLOR", 1L)
    );

    @Test
    public void addColumnsToIndex_addsThreeColumns(){
        List<Index> allIndexes = getAllIndexes();
        IndexesForTableFactory factory = new IndexesForTableFactory(allIndexes, allIndexColumns);
        Index indexWithThreeColumns = allIndexes.get(1);
        Assert.assertEquals(0, indexWithThreeColumns.getColumns().size());
        factory.addColumnsToIndex(indexWithThreeColumns);
        List<String> expected = Arrays.asList("LENGTH", "WIDTH", "HEIGHT");
        Assert.assertEquals(expected, indexWithThreeColumns.getColumns());
    }

    @Test
    public void addColumnsToIndex_addsOneColumn(){
        List<Index> allIndexes = getAllIndexes();
        IndexesForTableFactory factory = new IndexesForTableFactory(allIndexes, allIndexColumns);
        Index indexWithOneColumn = allIndexes.get(2);
        Assert.assertEquals(0, indexWithOneColumn.getColumns().size());
        factory.addColumnsToIndex(indexWithOneColumn);
        List<String> expected = Collections.singletonList("COLOR");
        Assert.assertEquals(expected, indexWithOneColumn.getColumns());
    }

    @Test
    public void get_getsOneIndex(){
        IndexesForTableFactory factory = new IndexesForTableFactory(getAllIndexes(), allIndexColumns);
        List<Index> actual = factory.get(FORMS);
        List<String> actualNames = actual.stream().map(Index::getIndexName).collect(Collectors.toList());
        List<String> expected = Arrays.asList("IDX_FORMS1");
        Assert.assertEquals(expected, actualNames);
    }

    @Test
    public void get_getsTwoIndexes(){
        IndexesForTableFactory factory = new IndexesForTableFactory(getAllIndexes(), allIndexColumns);
        List<Index> actual = factory.get(SHAPES);
        List<String> actualNames = actual.stream().map(Index::getIndexName).collect(Collectors.toList());
        List<String> expected = Arrays.asList("IDX_SHAPES1", "IDX_SHAPES2");
        Assert.assertEquals(expected, actualNames);
    }

    private List<Index> getAllIndexes() {
        return Arrays.asList(
                new OracleIndex("IDX_SHAPES2", "NORMAL", SHAPES, "NONUNIQUE"),
                new OracleIndex("IDX_SHAPES1", "NORMAL", SHAPES, "NONUNIQUE"),
                new OracleIndex("IDX_FORMS1", "NORMAL", FORMS, "UNIQUE")
        );
    }
}
