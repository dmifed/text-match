package com.dmifed.textmatch.compare;

import java.util.List;
import java.util.Properties;

/**
 * Created by DIMA, on 21.04.2022
 */
public class StringMatchSearch {
    private final String defaultMatchString;
    private final WordCompare wordCompare;
    private final Associations associations;
    private final ScoreOfSimilarity scoreOfSimilarity;

    public StringMatchSearch(Properties properties) {
        defaultMatchString = properties.getProperty("defaultMatchString");
        wordCompare = new WordCompare(properties);
        associations = new Associations(properties);
        scoreOfSimilarity = new ScoreOfSimilarity(properties);
    }

    public String selectClosestString(String source, List<String> stringList){
        StringCompare stringCompare = new StringCompare(wordCompare, associations);
        int maxScore = 0;
        String bestMatch = defaultMatchString;

        for(String s : stringList){
            List<LevelSimilarity> levels = stringCompare.compare(source, s);
            if(levels.size() == 1 && levels.get(0) == LevelSimilarity.FULL_MATCH){
                return s;
            }
            int score = scoreOfSimilarity.getScore(levels);
            if(score > maxScore){
                maxScore = score;
                bestMatch = s;
            }
        }
        return bestMatch;
    }
}
