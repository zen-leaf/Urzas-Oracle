package com.mambocosmo.urzasoracle.misc.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchCriteria {
    private String key;
    private String logic;
    private Object value;
    private Double parsedValue;

    public static List<SearchCriteria> StringToCriteria(String inCriteria) {
        String regex = "(\\w+)\\s*([:<>]|~)\\s*(\"(.+?)\"|(.+?)(?=\\s\\w+[:<>~]|$))";
        Pattern pattern = Pattern.compile(regex);
        Matcher m = pattern.matcher(inCriteria.trim());
        List<SearchCriteria> out = new ArrayList<>();
        while (m.find()) {
            // System.out.println();
            // for (int i = 0; i < m.groupCount(); i++) {
            // System.out.println("Group " + i + ": " + m.group(i));
            // }
            String tempValue = (m.group(4) == null ? m.group(3) : m.group(4));
            Double parse = null;
            try {
                System.out.println("parsing " + tempValue + " to double");
                parse = Double.parseDouble(tempValue);
            } catch (NumberFormatException e) {
                System.out.println(e.getMessage());
            }

            out.add(new SearchCriteria(m.group(1), m.group(2), tempValue, parse));
            System.out.println("result:\n" + "key: " + m.group(1) + "\n" + "logic: " + m.group(2) + "\n" + "value: "
                    + tempValue);
        }
        if (out.size() == 0) {
            out.add(new SearchCriteria("name", ":", inCriteria, null));
        }
        return out;
    }
}
