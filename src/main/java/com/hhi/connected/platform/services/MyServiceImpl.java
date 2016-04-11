package com.hhi.connected.platform.services;

import com.hhi.connected.platform.models.Greeting;
import org.joda.time.DateTime;
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
    public Object home(String ruleName, String body) {

        return new Greeting(0L, body);
    }

    @Override
    public Object tick() {

        return new Greeting(counter.incrementAndGet(),
                "tic toc: "+ new DateTime());
    }
}
