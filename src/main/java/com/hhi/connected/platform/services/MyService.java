package com.hhi.connected.platform.services;

import com.hhi.connected.platform.models.Greeting;

public interface MyService {
    Greeting sayHello(String name);

    Object home();
    Object tick();
}
