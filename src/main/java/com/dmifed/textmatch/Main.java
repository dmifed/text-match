package com.dmifed.textmatch;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Main {

    public static void main(String[] args){
        Main main = new Main();
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(new File("src\\main\\resources\\config.properties")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        InputText text = new InputText(properties);
        StringMatch stringMatch = new StringMatch(properties);
        List<String> first = text.getFirst();
        List<String> second = text.getSecond();
        List<String> result = new ArrayList<>();
        for(String s : first){
            result.add(s + properties.getProperty("stringSeparator") + stringMatch.selectClosestString(s, second));
        }
        String pathOutput = properties.getProperty("outputText");
        main.saveData(result, pathOutput);

    }

    public void saveData(List<String> list, String pathOutput){
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
