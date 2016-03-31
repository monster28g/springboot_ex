package com.hhi.connected.platform.services.utils;

import java.util.List;
import java.util.function.Function;

public class MyListUtils {

    public static <U, R> Function<List, Function<List, List>> subtract() {
        return x -> y -> subtract(x, y);
    }

    private static List subtract(List x, List y) {
        x.removeAll(y);
        return x;
    }

    public static <U, R> Function<List, Function<Object, List>> addFirst() {
        return x -> y -> addFirst(x, y);
    }

    private static  List addFirst(List x, Object y) {
        x.add(0, y);
        return x;
    }

}
