package com.hhi.connected.platform.services;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.Socket;

@Component
public class SimpleTcpGatewayServiceImpl implements SimpleGatewayService{
    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleTcpGatewayServiceImpl.class);

    @Value("${tcpserver.host}")
    private String host;

    @Value("${tcpserver.port}")
    private int port;

    Socket socket;

    @Override
    public void send(String payload) {
        try {
            new Socket(host, port).getOutputStream().write(payload.getBytes());
            LOGGER.debug("tcp send : {}", payload);
        } catch (IOException e) {
            LOGGER.debug("cannot connect to {}:{}", host, port);
        }
    }
}
