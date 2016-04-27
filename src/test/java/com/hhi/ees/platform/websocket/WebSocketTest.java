package com.hhi.ees.platform.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hhi.ees.platform.Application;
import com.hhi.ees.platform.models.HelloMessage;
import com.hhi.ees.platform.models.PushServiceInfo;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.AbstractSubscribableChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.JsonPathExpectationsHelper;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)

// TODO replace configuration with SpringApplicationConfiguration ?
//@ContextConfiguration(classes = {
//        WebSocketTest.TestWebSocketConfig.class,
//        WebSocketTest.TestConfig.class
//})
@SpringApplicationConfiguration(classes = Application.class)
@TestPropertySource({
        "/properties/gateway.properties",
        "/properties/db.properties",
        "/properties/push_service.properties"
})
public class WebSocketTest {
    @Autowired private AbstractSubscribableChannel clientInboundChannel;

    @Autowired private AbstractSubscribableChannel clientOutboundChannel;

    @Autowired private AbstractSubscribableChannel brokerChannel;

    private TestChannelInterceptor clientOutboundChannelInterceptor;

    private TestChannelInterceptor brokerChannelInterceptor;


    @Before
    public void setUp() throws Exception {

        this.brokerChannelInterceptor = new TestChannelInterceptor();
        this.clientOutboundChannelInterceptor = new TestChannelInterceptor();

        this.brokerChannel.addInterceptor(this.brokerChannelInterceptor);
        this.clientOutboundChannel.addInterceptor(this.clientOutboundChannelInterceptor);
    }

    @Test
    public void testHello() throws Exception {
        String name = "DELL";

        HelloMessage helloMessage = new HelloMessage();
        helloMessage.setName(name);

        byte[] payload = new ObjectMapper().writeValueAsBytes(helloMessage);

        StompHeaderAccessor headers = StompHeaderAccessor.create(StompCommand.SEND);
        headers.setDestination("/app/hivaas");
        headers.setSessionId("0");
        headers.setSessionAttributes(new HashMap<>());
        Message<byte[]> message = MessageBuilder.createMessage(payload, headers.getMessageHeaders());

        this.brokerChannelInterceptor.setIncludedDestinations("/topic/**");
        this.clientInboundChannel.send(message);

        Message<?> reply = this.brokerChannelInterceptor.awaitMessage(5);
        Assert.assertNotNull(reply);

        StompHeaderAccessor replyHeaders = StompHeaderAccessor.wrap(reply);
        assertEquals("0", replyHeaders.getSessionId());
        assertEquals("/topic/hivaas", replyHeaders.getDestination());

        String json = new String((byte[]) reply.getPayload(), Charset.forName("UTF-8"));
        new JsonPathExpectationsHelper("$.content").assertValue(json, String.format("Hello, WS echo, DELL!!", name));
    }

    @Test
    public void testPushData() throws Exception {

        String name = PushServiceInfo.getSensorRuleName();

        HelloMessage helloMessage = new HelloMessage();
        helloMessage.setName(name);
        helloMessage.setBody(getMessagesAsString("/sample_data/data.json"));

        byte[] payload = new ObjectMapper().writeValueAsBytes(helloMessage);

        StompHeaderAccessor headers = StompHeaderAccessor.create(StompCommand.SEND);
        headers.setDestination("/app/hivaas");
        headers.setSessionId("0");
        headers.setSessionAttributes(new HashMap<>());
        Message<byte[]> message = MessageBuilder.createMessage(payload, headers.getMessageHeaders());

        this.brokerChannelInterceptor.setIncludedDestinations("/topic/**");
        this.clientInboundChannel.send(message);

        Message<?> reply = this.brokerChannelInterceptor.awaitMessage(5);
        Assert.assertNotNull(reply);

        StompHeaderAccessor replyHeaders = StompHeaderAccessor.wrap(reply);
        assertEquals("0", replyHeaders.getSessionId());
        assertEquals("/topic/hivaas", replyHeaders.getDestination());

        String json = new String((byte[]) reply.getPayload(), Charset.forName("UTF-8"));
        assertTrue(json.contains("vdmSampleContent") || json.contains("alarmSampleContent"));
    }

    private String getMessagesAsString(String s) throws IOException {
        return FileUtils.readFileToString(new File(this.getClass().getResource(s).getFile()));
    }

    // TODO replace configuration with SpringApplicationConfiguration ?
//    @Configuration
//    @EnableScheduling
//    @ComponentScan(
//            basePackages="com.hhi.ees.platform",
//            excludeFilters = @ComponentScan.Filter(type= FilterType.ANNOTATION, value = Configuration.class)
//    )
//    @EnableWebSocketMessageBroker
//    static class TestWebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {
//
//        @Autowired
//        Environment env;
//
//        @Override
//        public void registerStompEndpoints(StompEndpointRegistry registry) {
//            registry.addEndpoint("/hivaas").withSockJS();
//        }
//
//        @Override
//        public void configureMessageBroker(MessageBrokerRegistry registry) {
//            registry.enableSimpleBroker("/queue/", "/topic/");
////			registry.enableStompBrokerRelay("/queue/", "/topic/");
//            registry.setApplicationDestinationPrefixes("/app");
//        }
//    }
//
//    /**
//     * Configuration class that un-registers MessageHandler's it finds in the
//     * ApplicationContext from the message channels they are subscribed to...
//     * except the message handler used to invoke annotated message handling methods.
//     * The intent is to reduce additional processing and additional messages not
//     * related to the execute.
//     */
//    @Configuration
//    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
//    static class TestConfig implements ApplicationListener<ContextRefreshedEvent> {
//
//        @Autowired
//        private List<SubscribableChannel> channels;
//
//        @Autowired
//        private List<MessageHandler> handlers;
//
//
//        @Override
//        public void onApplicationEvent(ContextRefreshedEvent event) {
//            for (MessageHandler handler : handlers) {
//                if (handler instanceof SimpAnnotationMethodMessageHandler) {
//                    continue;
//                }
//                for (SubscribableChannel channel :channels) {
//                    channel.unsubscribe(handler);
//                }
//            }
//        }
//    }
}
