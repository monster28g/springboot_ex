package com.hhi.connected.platform.controllers;

import com.hhi.connected.platform.models.HelloMessage;
import com.hhi.connected.platform.services.MyService;
import com.hhi.connected.platform.services.PushService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GreetingController {
    private static final Logger LOGGER = LoggerFactory.getLogger(GreetingController.class);

    @Autowired
    private
    MyService myService;

    @Autowired
    private
    PushService pushService;

    private final AtomicLong counter = new AtomicLong();

    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    private TaskScheduler scheduler;

    @RequestMapping(value = "/greeting", method = RequestMethod.GET )
    public Object greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return myService.sayHello(name);
    }

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Object hello(HelloMessage message) {
        return myService.sayHello("WS, " + message.getName() + "!");
    }

    @PostConstruct
    private void broadcastTimePeriodically() {
        scheduler.scheduleAtFixedRate((Runnable) this::tictoc, 60000L);

        scheduler.scheduleAtFixedRate((Runnable) this::keepAlivePushService, 10000L);

    }

    private void keepAlivePushService() {
        if(pushService.isConnected()){
            LOGGER.debug("push service is alive");
        }else{
            // TODO restart pushService
            LOGGER.debug("push service is not alive");
        }
    }

    private void tictoc() {
        template.convertAndSend("/topic/greetings", myService.tick());
    }
}
