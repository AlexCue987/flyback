package com.flyback.oracle;

import com.flyback.SaveableAsTextFile;
import org.junit.Ignore;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

public class OracleTablesDdlExtractorTests {
    @Ignore
    @Test
    public void worksAgainstRealSchema() throws SQLException, ClassNotFoundException {
        String owner = "***";
        String pwd = "***";
        String connectionString = "***";
        OracleTablesDdlExtractor extractor = new OracleTablesDdlExtractor(owner, pwd, connectionString);
        List<SaveableAsTextFile> actual = extractor.get();
        actual.forEach(t -> System.out.println(t.getFileName() + "\n" + t.getFileContents()));
    }
}
