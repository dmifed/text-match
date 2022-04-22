package com.dmifed.textmatch;

import lombok.Getter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/**
 * Created by DIMA, on 22.04.2022
 */
public class Dictionary {
    public static final Dictionary getInstance = new Dictionary();
    @Getter
    public static Map<String, List<String>> mapOfAssociations;

    private Dictionary(){
        this.mapOfAssociations = getAssociationFromProperties();
    }

    //if nothing helped
    private static Map<String, List<String>> getAssociationFromProperties(){
        Properties properties = new Properties();
        Map<String, List<String>> assocMap = new HashMap<>();
        try {
            properties.load(new FileInputStream(new File("src\\main\\resources\\dictionary.properties")));
            Set<Object> keys = properties.keySet();
            for(Object k : keys){
                String key = (String)k;
                List<String> words = Arrays.asList(properties.getProperty(key).split("&"));
                assocMap.replace(key, words);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return assocMap;
    }

}
