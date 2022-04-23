package com.dmifed.textmatch.defaults;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by DIMA, on 22.04.2022
 */
public class Dictionary {
    public static Map<String, List<String>> getAssociationFromProperties(){
        Map<String, List<String>> assocMap = new HashMap<>();
        try {
            Scanner scanner = new Scanner(new File("src\\main\\resources\\dictionary.txt"));
            while (scanner.hasNext()){
                String line = scanner.nextLine();
                String key = line.split("=")[0];
                List<String> words = Arrays.asList(line.split("=")[1].split("&"));
                assocMap.put(key, words);
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return assocMap;
    }

}
