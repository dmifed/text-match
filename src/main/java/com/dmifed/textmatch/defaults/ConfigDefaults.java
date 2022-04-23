package com.dmifed.textmatch.defaults;

import java.util.Map;

/**
 * Created by DIMA, on 21.04.2022
 */
public class ConfigDefaults {
    public final static String SITE_OF_ASSOSIATIONS = "https://sinonim.org/as/";
    public final static String PATH_TO_TAG_WITH_WORD_ASSOCIATION = "div.main > ul.assocPodryad > li > a";
    public final static String INPUT_TEXT = "input.txt";
    public final static int LIMIT_ASSOCIATIONS =10;


    public final static Map<String, Integer> weightMap = Map.of(
            "weightWhole", 10,
            "weightSimilar", 5,
            "weightAssociation", 3);

    public final static Map<String, Integer> wordMap = Map.of(
            "percentLengthWordMatch", 75,
            "minSymbolsInInspectWord", 3,
            "lenOfWordToMathWhole", 5);
}
