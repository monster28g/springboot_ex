package com.hhi.ees.platform.services.utils;

import java.util.HashMap;
import java.util.Map;

public class MyMapUtils {
    public static <K, V> Map<K, V> mapDifference(Map<? extends K, ? extends V> left, Map<? extends K, ? extends V> right) {
        Map<K, V> difference = new HashMap<>();
        difference.putAll(left);
        difference.putAll(right);
        difference.entrySet().removeAll(right.entrySet());
        return difference;
    }
}
