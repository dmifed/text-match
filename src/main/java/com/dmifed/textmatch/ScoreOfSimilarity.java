package com.dmifed.textmatch;

import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Created by DIMA, on 21.04.2022
 */
public class ScoreOfSimilarity {
    private int weightWhole;
    private int weightSimilar;
    private int weightAssociation;
    Logger log;

    public ScoreOfSimilarity(Properties properties) {
        log = Logger.getAnonymousLogger();
        init(properties);
    }

    private void init(Properties properties){
        weightWhole = initIntParam("weightWhole", properties);
        weightSimilar = initIntParam("weightSimilar", properties);
        weightAssociation = initIntParam("weightAssociation", properties);
    }

    private int initIntParam(String param, Properties properties){
        try {
            return Integer.parseInt(properties.getProperty(param));
        }catch (NumberFormatException e){
            log.warning("param must be int. Use defaults");
            return ConfigDefaults.weightMap.get(param);
        }
    }

    public int getScore(List<LevelSimilarity> similarities){
        int score = 0;
        for(LevelSimilarity level : similarities){
            switch (level){
                case WHOLE: score += weightWhole; break;
                case SIMILAR: score += weightSimilar; break;
                case ASSOCIATION: score += weightAssociation; break;
                default: //do nothing
            }
        }
        return score;
    }
}
