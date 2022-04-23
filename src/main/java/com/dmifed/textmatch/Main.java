package com.dmifed.textmatch;

import com.dmifed.textmatch.compare.StringMatchSearch;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Main {

    public static void main(String[] args){
        Properties properties = Utils.getConfigProperties();
        InputText text = new InputText(properties);
        StringMatchSearch stringMatchSearch = new StringMatchSearch(properties);

        List<String> first = text.getFirst();
        List<String> second = text.getSecond();
        List<String> result = new ArrayList<>();

        for(String s : first){
            result.add(s + properties.getProperty("stringSeparator") + stringMatchSearch.selectClosestString(s, second));
        }

        String pathOutput = properties.getProperty("outputText");
        Utils.saveData(result, pathOutput);
    }


}
