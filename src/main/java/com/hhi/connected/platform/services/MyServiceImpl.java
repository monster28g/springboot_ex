package com.hhi.connected.platform.services;

import com.hhi.connected.platform.models.Greeting;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

@Service
public class MyServiceImpl implements MyService {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @Override
    public Greeting sayHello(String name) {
        return new Greeting(counter.incrementAndGet(),
                String.format(template, name));
    }

    @Override
    public Greeting home() {

        return new Greeting(0L, "none");
    }
}
