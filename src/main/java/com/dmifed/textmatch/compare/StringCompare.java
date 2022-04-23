package com.dmifed.textmatch.compare;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by DIMA, on 20.04.2022
 */
public class StringCompare {
    private final WordCompare wordCompare;
    private final Associations associations;

    public StringCompare(WordCompare wordCompare, Associations associations) {
        this.wordCompare = wordCompare;
        this.associations = associations;
    }


    List<LevelSimilarity> compare(String originalString, String comparingString){
        List<LevelSimilarity> similarities = new ArrayList<>();
        originalString = deletePunctuation(originalString);
        comparingString = deletePunctuation(comparingString);

        if (originalString.equals(comparingString)) {
            similarities.add(LevelSimilarity.FULL_MATCH);
            return similarities;
        }

        String[] originalWords = originalString.split(" ");
        List<String> comparingWords = Arrays.asList(comparingString.split(" "));

        for(String originalWord : originalWords){
            LevelSimilarity level = LevelSimilarity.NONE;
            boolean hasSimilar = false;
            for(String comparingWord : comparingWords){
                level = wordCompare.wordsSimilarityCompare(originalWord, comparingWord);
                similarities.add(level);
                if(level != LevelSimilarity.NONE) hasSimilar = true;
            }
            if(!hasSimilar){
                level = checkAssociations(originalWord, comparingWords);
            }
            similarities.add(level);
        }
        return similarities;
    }

    private List<String> getAssociation(String word){
        List<String> associationsList = new ArrayList<>();
        if(associations == null) return associationsList;
        return associations.getAssociations(word);
    }

    private LevelSimilarity checkAssociations(String originalWord, List<String> comparingWords){
        List<String> association = getAssociation(originalWord);
        for (String assoc : association)        {
            for(String comparingWord : comparingWords){
                if(assoc.equals(comparingWord)){
                    return LevelSimilarity.ASSOCIATION;
                }
            }
        }
        return LevelSimilarity.NONE;
    }

    private String deletePunctuation(String s){
        return s.trim().toLowerCase().replaceAll("\\p{Punct}", "");
    }

}
