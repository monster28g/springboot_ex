package com.hhi.connected.platform.controllers;

import com.hhi.connected.platform.models.HelloMessage;
import com.hhi.connected.platform.services.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
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

//    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
//    Object home() {
//        return myService.home();
//    }

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Object greeting(HelloMessage message) throws Exception {
        return myService.sayHello("WS, " + message.getName() + "!");
    }
}
