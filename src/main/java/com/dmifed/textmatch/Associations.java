package com.dmifed.textmatch;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

/**
 * Created by DIMA, on 19.04.2022
 */
public class Associations {
    private String siteOfAssociations;
    private String pathToTagWithWordAssociation;
    private int limitAssociations;
    private final Logger log;

    public Associations(Properties config) {
        log = Logger.getAnonymousLogger();
        init(config);
    }

    List<String> getAssociations(String word){
        String url = siteOfAssociations + word;
        List<String> assoc = new ArrayList<>();
        Document doc;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            //log.warning("cant take connection with " + url + " .Try to connect with default site");
            url = ConfigDefaults.SITE_OF_ASSOSIATIONS + word;
            pathToTagWithWordAssociation = ConfigDefaults.PATH_TO_TAG_WITH_WORD_ASSOCIATION;
            try{
                doc = Jsoup.connect(url).get();
            }catch (IOException e1){
                Dictionary dictionary = Dictionary.getInstance()
                //log.warning("cant take connection with" + url + ". Association will not be produced");
                return assoc;
            }
        }

        Elements elements = doc.select(pathToTagWithWordAssociation);
        int count = 0;
        for(Element element : elements) {
            if(count < limitAssociations){
                assoc.add(element.text().toLowerCase());
            }
            ++count;
        }
        return assoc;
    }

    private void init(Properties properties){
        siteOfAssociations = properties.getProperty("siteOfAssosiations");
        pathToTagWithWordAssociation = properties.getProperty("pathToTagWithWordAssociation");

        try {
            limitAssociations = Integer.parseInt(properties.getProperty("limitAssociations"));
        }catch (NumberFormatException e){
            log.warning("limitAssociations must be int. Use defaults");
            limitAssociations = ConfigDefaults.LIMIT_ASSOCIATIONS;
        }
    }

    //if nothing helped
    private Map<String, List<String>> getAssociationFromProperties(){
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
