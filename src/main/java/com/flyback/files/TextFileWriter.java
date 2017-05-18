package com.flyback.files;

import java.io.FileWriter;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TextFileWriter {
    public static void write(String path, String fileName, String contents){
        Path combinedPath = Paths.get(path, fileName);
        try(FileWriter fileWriter = new FileWriter(combinedPath.toString())){
            fileWriter.write(contents);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
