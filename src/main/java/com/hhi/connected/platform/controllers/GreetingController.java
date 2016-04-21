package com.hhi.connected.platform.controllers;

import com.hhi.connected.platform.models.HelloMessage;
import com.hhi.connected.platform.services.MyService;
import com.hhi.connected.platform.services.PushService;
import org.codehaus.plexus.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
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
    private TaskScheduler scheduler;

    @RequestMapping(value = "/greeting", method = RequestMethod.GET )
    public Object greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return myService.sayHello(name);
    }

    @MessageMapping("/hivaas")
    @SendTo("/topic/hivaas")
    public Object hello(HelloMessage message) {
        return StringUtils.isEmpty(message.getBody())?
                myService.sayHello("WS echo, " + message.getName() + "!")
                : myService.home(message.getName(), message.getBody());
    }

    @PostConstruct
    private void broadcastTimePeriodically() {
        scheduler.scheduleAtFixedRate(this::keepAlivePushService, 60000L);

    }

    private void keepAlivePushService() {
        if(pushService != null) {
            if (pushService.isConnected()) {
                LOGGER.debug("push service is alive");
            } else {
                LOGGER.debug("push service is not alive");
                try {
                    pushService.reStart();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @MessageExceptionHandler
    @SendToUser("/queue/errors")
    public String handleException(Throwable exception) {
        return exception.getMessage();
    }
}
