package com.dmifed.textmatch;

import com.dmifed.textmatch.defaults.ConfigDefaults;
import lombok.Getter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * Created by DIMA, on 21.04.2022
 */
public class InputText {
    private Scanner scanner;
    @Getter
    private List<String> first;
    @Getter
    private List<String> second;

    public InputText(Properties properties){
        init(properties);
        getStringList();
    }

    private void init(Properties properties){
        String pathToInput = properties.getProperty("inputText");
        File file = new File(pathToInput);
        Logger log = Logger.getAnonymousLogger();
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            log.warning("Cant find input file. Use default path");
            try{
                scanner = new Scanner(new File(ConfigDefaults.INPUT_TEXT));
            }catch (FileNotFoundException e2){
                log.severe("Cant find input file");
                e.printStackTrace();
            }
        }
    }

    private void getStringList(){
        int countLine = Integer.parseInt(scanner.nextLine());
        List<String> list = new ArrayList<>();
        for(int i = 0; i < countLine; i++){
            list.add(scanner.nextLine());
        }
        first = list;
        System.out.println(first);
        list = new ArrayList<>();
        countLine = Integer.parseInt(scanner.nextLine());
        for(int i = 0; i < countLine; i++){
            list.add(scanner.nextLine());
        }
        second = list;
        scanner.close();
    }


}
