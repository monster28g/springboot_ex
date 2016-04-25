package com.hhi.ees.platform.handlers;

import com.google.common.collect.ImmutableMap;
import com.hhi.ees.platform.models.BaseModel;
import com.hhi.ees.platform.models.enums.ModelType;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class ModelHandlerImpl implements ModelHandler {

    private static final String VDM_PATH = "vdmpath";
    private static final String ALARM_TIME = "alarmTime";

    @Override
    public List<BaseModel> process(List<Map<String, Object>> payload, ModelType type) {

        // FIXME with Stream if can
        List<BaseModel> list = new ArrayList<>();
        payload.stream().forEach(e -> list.add(new BaseModel(String.valueOf(e.get(VDM_PATH)), Long.valueOf(e.get(ALARM_TIME).toString()), getPropertiesWithoutKeyAndTimeStamp(e))));
        return list;
    }

    private Map getPropertiesWithoutKeyAndTimeStamp(Map<String, Object> e) {
        Map<String, Object> tmp = new HashMap<>();

        e.entrySet().stream().forEach(k -> {
            if(!Arrays.asList(VDM_PATH, ALARM_TIME).contains(k.getKey()))
                tmp.put(k.getKey(), k.getValue());
        });
        return tmp;

    }

    @Override
    public List<BaseModel> process(Map<String, Object> payload, ModelType type) {
        return payload.entrySet().stream().map(e -> this.transform(e, type)).flatMap(Collection::stream).collect(Collectors.toList());
    }

    private List<BaseModel> transform(Map.Entry<String, Object> e, ModelType type) {

        List<BaseModel> list = null;
        switch(type){
            case DATA:
                list = assembleModelFromData(e);
                break;
            case CONFIG:
            case ALARM:
                list = assembleModelFromConfigOrAlarm(e);
                break;
            default:
                break;
        }

        return list;
    }

    private List<BaseModel> assembleModelFromConfigOrAlarm(Map.Entry<String, Object> e) {
        List<BaseModel> list = new ArrayList<>();
        Long timestamp = System.currentTimeMillis();

        Collections.singletonList(e.getValue()).stream().forEach(v -> {
            list.add(new BaseModel(e.getKey(), timestamp, (Map<String, Object>) e.getValue()));
        });
        return list;
    }

    private List<BaseModel> assembleModelFromData(Map.Entry<String, Object> e) {
        List<BaseModel> list = new ArrayList<>();
        Collections.singletonList(e.getValue()).stream().forEach(v -> {
            List<Object> valueList = (List<Object>) v;

            // FIX ME
            for(int i = 0; i < valueList.size(); i = i + 3)
            {
                if(!((i + 1) % 3 == 0)){
                    Float val = getValue(valueList.get(i + 1).toString());
                    if(val != null) {

                        list.add(new BaseModel(e.getKey(), Long.valueOf(valueList.get(i).toString()),
                                ImmutableMap.<String, Object>builder()
                                        .put("value", val)
                                        .put("valid", Integer.valueOf(valueList.get(i + 2).toString()))
                                        .build()
                        ));
                    }
                }
            }});
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