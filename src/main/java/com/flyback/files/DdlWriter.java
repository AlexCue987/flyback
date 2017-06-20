package com.flyback.files;

import com.flyback.SaveableAsTextFile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class DdlWriter {
    public void save(String path, List<SaveableAsTextFile> tables, String objectType){
        Path pathToTables = Paths.get(path, objectType);
        SqlFilesDeleter.deleteAllSqlFilesInFolder(pathToTables.toString());
        tables.forEach(table -> TextFileWriter.write(pathToTables.toString(),
                table.getFileName(),
                table.getFileContents()));
    }
}
