package com.mambocosmo.urzasoracle.misc.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;


public enum AttractionLight {
    
    a1("1"),a2("2"),a3("3"),a4("4"),a5("5"),a6("6");

    private final String val;

    AttractionLight(String s) {
        this.val = s;
    }

    @JsonValue
    public String getValue() {
        return val;
    }

    @JsonCreator
    public static AttractionLight fromString(String in) {
        if (in == null) {
            return null;
        }

        String temp;
        if (in != null && Character.isDigit(in.charAt(0))) {
            temp = "a" + in;
        } else {
            temp = in;
        }
        
        return AttractionLight.valueOf(temp);
    }

}
