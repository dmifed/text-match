package com.dmifed.textmatch.compare;

import com.dmifed.textmatch.defaults.ConfigDefaults;
import com.dmifed.textmatch.defaults.Dictionary;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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
    private boolean useDictionary = false;

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
            url = ConfigDefaults.SITE_OF_ASSOSIATIONS + word;
            pathToTagWithWordAssociation = ConfigDefaults.PATH_TO_TAG_WITH_WORD_ASSOCIATION;
            try{
                doc = Jsoup.connect(url).get();
            }catch (IOException e1){
                if(!useDictionary){
                    log.warning("cant take connection to web sites with associations . Using default dictionary further");
                    useDictionary = true;
                }

                if(Dictionary.getAssociationFromProperties().containsKey(word)){
                    assoc = Dictionary.getAssociationFromProperties().get(word);
                }
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
}
