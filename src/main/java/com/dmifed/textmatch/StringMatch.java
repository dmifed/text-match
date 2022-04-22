package com.dmifed.textmatch;

import java.util.List;
import java.util.Properties;

/**
 * Created by DIMA, on 21.04.2022
 */
public class StringMatch {
    private String defaultMatchString;
    private WordCompare wordCompare;
    private Associations associations;
    private ScoreOfSimilarity scoreOfSimilarity;

    public StringMatch(Properties properties) {
        init(properties);
    }

    public String selectClosestString(String source, List<String> stringList){
        StringCompare stringCompare = new StringCompare(wordCompare, associations);
        int maxScore = 0;
        String bestMatch = defaultMatchString;

        for(String s : stringList){
            List<LevelSimilarity> levels = stringCompare.compare(source, s);
            //System.out.println(s + " simsilarity weight " + levels);
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

    private void init(Properties properties){
        defaultMatchString = properties.getProperty("defaultMatchString");
        wordCompare = new WordCompare(properties);
        associations = new Associations(properties);
        scoreOfSimilarity = new ScoreOfSimilarity(properties);
    }
}
