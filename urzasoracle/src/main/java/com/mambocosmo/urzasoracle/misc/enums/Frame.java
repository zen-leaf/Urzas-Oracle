package com.mambocosmo.urzasoracle.misc.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Frame {
    NONE("none"), y1997("1997"), y2015("2015"), y1993("1993"), y2003("2003"), future("future");

    private final String val;

    Frame(String s) {
        this.val = s;
    }

    @JsonValue
    public String getValue() {
        return val;
    }

    @JsonCreator
    public static Frame fromString(String in) {
        if (in == null) {
            return null;

        }
        String temp;
        if (in != null && Character.isDigit(in.charAt(0))) {
            temp = "y" + in;

        } else {
            temp = in;
        }
        return Frame.valueOf(temp);
    }
}
