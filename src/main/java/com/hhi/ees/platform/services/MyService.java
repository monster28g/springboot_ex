package com.hhi.ees.platform.services;

import com.hhi.ees.platform.models.Greeting;

public interface MyService {
    Greeting sayHello(String name);
    Object home(String ruleName, String body);
    Object tick();
}
