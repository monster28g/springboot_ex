package com.hhi.connected.platform;

import org.jooq.lambda.Seq;
import org.jooq.lambda.Window;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

public class MyTest {

    @Test
    public void test(){
        Map<Integer, String> random = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            random.put((int)(Math.random() * 100), String.valueOf((int) (Math.random() * 100)));
        }

        System.out.println("Initial Map: " + Arrays.toString(random.entrySet().toArray()));

        Map<Integer, String> sortedMap =
                random.entrySet().stream()
                        .sorted(Map.Entry.comparingByValue())
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                                (e1, e2) -> e1, LinkedHashMap::new));
        System.out.println("Sorted Map: " + Arrays.toString(sortedMap.entrySet().toArray()));

        System.out.println(
                Seq.of(10, 1, 1, 1, 1, 15, 30, 15, 15, 2, 6)
                        .window()
                        .filter(w -> {
                                    if (w.lead().isPresent())
                                        return !Objects.equals(w.value(), w.lead().get());
                                    else
                                        return true;
                                })
                        .map(Window::value)
                        .toList()
        );
    }
}
