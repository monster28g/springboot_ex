package com.hhi.connected.platform;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CurryTestWithCurrying {

    private static Integer b = 2;

    private static List<Integer> calculate(List<Integer> list, Integer a){

        ((Function<Integer, Function<Integer, Function<Integer, Integer>>>) x -> y -> t -> x + y * t)
                .apply(b).apply(a);

        return list.stream().map(
                ((Function<Integer, Function<Integer, Function<Integer, Integer>>>) x -> y -> t -> x + y * t)
                .apply(b).apply(a))
                .collect(Collectors.toList());

    }



    public static void main(String[] args) {

        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);

        System.out.println(calculate(list, 3));

        System.out.println(((Function<Integer, Function<Integer, Integer>>) x -> y -> x + y)
                .apply(1).apply(2));

        System.out.println(((Function<List, Function<List, List>>) x -> y -> {x.removeAll(y);return x;})
                .apply(new ArrayList<>(Arrays.asList(1, 2, 3))).apply(Arrays.asList(1, 2)));

    }

}