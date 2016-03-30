package com.hhi.connected.platform.handlers;

import com.hhi.connected.platform.models.BaseModel;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class ModelHandler {

    public List<BaseModel> process(Map<String, Object> tweet) {
        return tweet.entrySet().stream().map(this::transform).flatMap(Collection::stream).collect(Collectors.toList());
    }

    private List<BaseModel> transform(Map.Entry<String, Object> e) {

        List<BaseModel> list = new ArrayList<>();

        Collections.singletonList(e.getValue()).stream().forEach(v -> {
            List<Object> valueList = (List<Object>) v;

            // FIX ME
            for(int i = 0; i < valueList.size(); i = i + 3)
            {
                if(!((i + 1) % 3 == 0)){
                    Float val = getValue(valueList.get(i+1).toString());
                    if(val != null) {
                        list.add(new BaseModel(e.getKey(), Long.valueOf(valueList.get(i).toString()), val, Integer.valueOf(valueList.get(i+2).toString())));
                    }
                }
            }});

        list.sort(Comparator.comparing(BaseModel::getTimestamp));
        
        return list;
    }

    private Float getValue(String s) {
        s = StringUtils.trimAllWhitespace(s);
        if(StringUtils.isEmpty(s) || "null".equalsIgnoreCase(s) || s.length() ==0){
            return null;
        }
        return "true".equalsIgnoreCase(s) ? Float.valueOf(1f) : "false".equalsIgnoreCase(s) ? Float.valueOf(0f) : Float.valueOf(s);
    }
}