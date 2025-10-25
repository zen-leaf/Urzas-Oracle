package com.mambocosmo.urzasoracle.misc.Utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import com.mambocosmo.urzasoracle.misc.enums.BorderColor;

public class Util {
    public static String readFileContent(String path) {
        File bFile = new File(path);
        BufferedReader bReader = null;
        try {
            bReader = new BufferedReader(new FileReader(bFile));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }


}
