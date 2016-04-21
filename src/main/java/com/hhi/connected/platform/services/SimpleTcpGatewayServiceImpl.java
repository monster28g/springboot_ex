package com.hhi.connected.platform.services;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.Socket;

@Component
public class SimpleTcpGatewayServiceImpl implements SimpleGatewayService{
    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleTcpGatewayServiceImpl.class);

    static final String HOST = "localhost";
    static final int PORT = 18088;

    Socket socket;

    @Override
    public void send(String payload) {
        try {
            new Socket(HOST, PORT).getOutputStream().write(payload.getBytes());
            LOGGER.debug("tcp send : {}", payload);
        } catch (IOException e) {
            LOGGER.debug("cannot connect to {}:{}", HOST, PORT);
        }
    }
}
