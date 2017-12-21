package com.company;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class FileReader {

    /**
     * 1. Method reads text file and returns it as list of strings
     * @param filepath
     * @return
     */
    public static ArrayList<String> fileToStrings(String filepath){

        File file = new File(filepath);
        ArrayList<String> array = new ArrayList<>();
        try{
            BufferedReader br = new BufferedReader(new java.io.FileReader(file));
            String line = "";
            while((line = br.readLine()) != null){
                array.add(line);
            }
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return array;
    }
}
