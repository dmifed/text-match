package com.dmifed.textmatch;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;

/**
 * Created by DIMA, on 23.04.2022
 */
public class Utils {
    public static Properties getConfigProperties(){
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(new File("src\\main\\resources\\config.properties")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    public static void saveData(List<String> list, String pathOutput){
        Path path = Paths.get(pathOutput);
        if(path.toFile().exists()){
            path.toFile().delete();
        }
        try {
            Files.createFile(path);
            PrintWriter printWriter = new PrintWriter(path.toFile().getAbsolutePath());
            for(String str : list){
                printWriter.print(str);
                printWriter.print("\r\n");
            }
            printWriter.flush();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
