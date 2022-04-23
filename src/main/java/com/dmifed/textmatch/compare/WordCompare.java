package com.dmifed.textmatch.compare;

import com.dmifed.textmatch.defaults.ConfigDefaults;

import java.util.Properties;
import java.util.logging.Logger;

/**
 * Created by DIMA, on 20.04.2022
 */
public class WordCompare {
    private int percentLengthWordMatch;
    private int minSymbolsInInspectWord;
    private int lenOfWordToMathWhole;

    Logger log;

    public WordCompare(Properties properties) {
        log = Logger.getAnonymousLogger();
        init(properties);
    }

    LevelSimilarity wordsSimilarityCompare(String originWord, String inspectWord) {

        originWord = originWord.toLowerCase();
        inspectWord = inspectWord.toLowerCase();
        if (originWord.equals(inspectWord)) {
            //System.out.println("whole " + originWord + "--" + inspectWord);
            return LevelSimilarity.WHOLE;
        }
        if (inspectWord.length() < minSymbolsInInspectWord) {
            return LevelSimilarity.NONE;
        }

        if (originWord.length() > lenOfWordToMathWhole) {
            //System.out.println("checking word base");
            double percent = (double) percentLengthWordMatch / 100;
            int minLengthOfSamePart = (int) (originWord.length() * percent);
            if (minLengthOfSamePart > inspectWord.length()) {
                //System.out.println("minLengthOfSamePart > inspectWord.length");
                return LevelSimilarity.NONE;
            }
            String originBase = originWord.substring(0, minLengthOfSamePart);
            //System.out.println("comparing " + originBase + " from " + originWord + " with " + inspectWord);
            if (inspectWord.contains(originBase)) {
                //System.out.println(originWord + " -> " + originBase + " contains in " + inspectWord);
                //System.out.println("similar " + originWord + " " + inspectWord);
                return LevelSimilarity.SIMILAR;
            }
        }
        //System.out.println();
        return LevelSimilarity.NONE;
    }

    private void init(Properties properties){
        percentLengthWordMatch = initIntParam("percentLengthWordMatch", properties);
        minSymbolsInInspectWord = initIntParam("minSymbolsInInspectWord", properties);
        lenOfWordToMathWhole = initIntParam("lenOfWordToMathWhole", properties);
    }

    private int initIntParam(String param, Properties properties){
        try {
            return Integer.parseInt(properties.getProperty(param));
        }catch (NumberFormatException e){
            log.warning("param must be int. Use defaults");
            return ConfigDefaults.wordMap.get(param);
        }
    }
}
