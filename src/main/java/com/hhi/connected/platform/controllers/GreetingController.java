package com.hhi.connected.platform.controllers;

import com.hhi.connected.platform.services.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    @Autowired
    MyService myService;

    @RequestMapping(value = "/greeting", method = RequestMethod.GET )
    public Object greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return myService.sayHello(name);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    Object home() {
        return myService.home();
    }
}
