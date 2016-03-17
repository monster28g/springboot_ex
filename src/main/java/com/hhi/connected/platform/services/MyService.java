package com.hhi.connected.platform.services;

import com.hhi.connected.platform.models.Greeting;

public interface MyService {
    public Greeting sayHello(String name);

    public Object home();
}
