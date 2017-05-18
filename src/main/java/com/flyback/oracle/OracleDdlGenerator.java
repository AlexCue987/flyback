package com.flyback.oracle;

import com.flyback.SaveableAsTextFile;
import com.flyback.files.DdlWriter;

import java.util.List;

public class OracleDdlGenerator {
    public static void generate(String owner, String pwd, String connectionString, String outputPath){
        try {
            OracleTablesDdlExtractor tablesDdlExtractor = new OracleTablesDdlExtractor(owner, pwd, connectionString);
            List<SaveableAsTextFile> tablesDdl = tablesDdlExtractor.get();
            new DdlWriter().save(outputPath, tablesDdl);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
