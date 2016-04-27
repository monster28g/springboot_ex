package com.hhi.ees.platform.services.utils;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertTrue;

public class MyListUtilsTest {

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void subtract() throws Exception {
        String expectedValue = "value";
        List l = new ArrayList<>();
        l.add(expectedValue);
        assertTrue(MyListUtils.subtract().apply(l).apply(Arrays.asList(expectedValue)).isEmpty());
    }

    @Test
    public void addFirst() throws Exception {
        String firstValue = "first";

        List l = new ArrayList<>();
        l.add("second");
        MyListUtils.addFirst().apply(l).apply(firstValue);
        assertTrue(l.stream().findFirst().orElse(null).equals(firstValue));

    }
}