package com.flyback.files;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class SqlFilesDeleter {
    public static void deleteAllSqlFilesInFolder(String path){
        File folder = new File(path);
        File[] listFiles = folder.listFiles();
        if(listFiles == null){
            return;
        }
        List<File> files = Arrays.asList(listFiles);
        for (File file : files) {
            if(file.isDirectory() || !file.getName().endsWith(".sql")){
                continue;
            }
            file.delete();
        }
    }
}
