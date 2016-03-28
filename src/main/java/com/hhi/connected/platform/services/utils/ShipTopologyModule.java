package com.hhi.connected.platform.services.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ShipTopologyModule {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShipTopologyModule.class);

    Map<String, Object> vdmpathSet = new HashMap<>();

    private static final String CARGO = "Cargo";
    private static final String ELECTRICAL = "Electrical";
    private static final String GENERAL = "General";
    private static final String HULL = "Hull";
    private static final String MECHANICAL = "Mechanical";
    private static final String NAVIGATIONAL = "Navigational";

    private static final String DOT = ".";
    private static final String SLASH = "/";

    private static final String VAL = "val";
    private static final String STVAL = "stVal";
    private static final String CTLVAL = "ctlVal";
    private static final List leaf = Arrays.asList(VAL, STVAL, CTLVAL);

    public Map<String, Object> refine(Map<String, Object> map ){
        createPath(map, "", 0);
        return vdmpathSet;
    }

    private void createPath(Map<String, Object> map, String path, int depth) {

        map.entrySet().stream().forEach(e -> {
            if(leaf.contains(e.getKey())){
                vdmpathSet.put(path+e.getKey(), e.getValue());
            }
            else if(!e.getKey().equals(GENERAL))
            {
                try {
                    createPath((Map<String, Object>) e.getValue(), getPath(path, depth, e), depth + 1);
                }catch (ClassCastException e1){
//                    e1.printStackTrace();
                }

            }
            else{
                LOGGER.debug(String.format("key : %s, value : %s", e.getKey(), e.getValue()));
            }
        });
    }

    private String getPath(String path, int depth, Map.Entry<String, Object> e) {
        return path + e.getKey() + getSeparator(path, depth);
    }

    private String getSeparator(String path, int depth) {
        if(path.startsWith(HULL) || path.startsWith(ELECTRICAL) || path.startsWith(CARGO)) {
            return depth > 2 ? DOT : SLASH;
        }
        else if(path.startsWith(MECHANICAL) || path.startsWith(NAVIGATIONAL)) {
            if(path.contains("Boiler") || path.contains("Shaft")){
                return depth > 2 ? DOT : SLASH;
            }
            return depth > 3 ? DOT : SLASH;
        }
        else
            return SLASH;
    }
}