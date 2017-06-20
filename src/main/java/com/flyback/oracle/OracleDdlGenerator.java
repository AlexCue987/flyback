package com.flyback.oracle;

import com.flyback.SaveableAsTextFile;
import com.flyback.files.DdlWriter;

import java.sql.SQLException;
import java.util.List;

public class OracleDdlGenerator {
    public static void generate(String owner, String pwd, String connectionString, String outputPath){
        try {
            outputTables(owner, pwd, connectionString, outputPath);
            outputViews(owner, pwd, connectionString, outputPath);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void outputTables(String owner, String pwd, String connectionString, String outputPath) throws SQLException, ClassNotFoundException {
        OracleTablesDdlExtractor tablesDdlExtractor = new OracleTablesDdlExtractor(owner, pwd, connectionString);
        List<SaveableAsTextFile> tablesDdl = tablesDdlExtractor.get();
        String objectType = "tables";
        new DdlWriter().save(outputPath, tablesDdl, objectType);
    }

    private static void outputViews(String owner, String pwd, String connectionString, String outputPath) throws SQLException, ClassNotFoundException {
        OracleViewsReader oracleViewsReader = new OracleViewsReader(owner, pwd, connectionString);
        List<SaveableAsTextFile> viewsDdl = oracleViewsReader.get();
        String objectType = "views";
        new DdlWriter().save(outputPath, viewsDdl, objectType);
    }
}
