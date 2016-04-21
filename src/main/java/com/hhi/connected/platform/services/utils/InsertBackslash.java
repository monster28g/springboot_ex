package com.hhi.connected.platform.services.utils;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class InsertBackslash {
    public String insertBackslash(String vdm){
        List<String> parts = Arrays.asList(vdm.split(Pattern.quote("/")));

        String result = "";

        for(int i = 0; i < parts.size(); i++){
            result += "\\/" + parts.get(i);
        }

        return result.substring(1);
    }
}
