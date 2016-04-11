package com.hhi.connected.platform.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.hhi.connected.platform.models.APIInfo;
import com.hhi.connected.platform.models.Greeting;
import com.hhi.connected.platform.models.PushServiceInfo;
import com.hhi.connected.platform.models.enums.ModelType;
import com.hhi.connected.platform.services.utils.RegisterUtil;
import com.hhi.vaas.platform.middleware.client.rest.APIGatewayClient;
import com.hhi.vaas.platform.middleware.client.websocket.EventHandler;
import com.hhi.vaas.platform.middleware.client.websocket.WebSocketClient;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.ws.rs.core.Response;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.security.KeyStore;

@Component
public class PushServiceImpl implements PushService{
    private static final Logger LOGGER = LoggerFactory.getLogger(PushServiceImpl.class);
    private static final String DESTINATION = "/topic/greetings";

    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    private MyParser myParser;

    private APIGatewayClient apiGatewayClient;
    private WebSocketClient websocketClient;

    /** 3rd Party username and password for authentication which is given at installation time */
    private static String username;
    private static String password;

    /** 3rd Party consumerKey and consumerSecret for authentication which is given at installation time */
    private static String consumerKey;
    private static String consumerSecret;

    private static int duration = 10;

    public PushServiceImpl() throws Exception {
        init();
        run();
    }

    @Override
    public void reStart() throws Exception {
        init();
        run();
    }

    private void run() throws Exception {
        startSensor();
        startAlarm();
    }

    private void init() throws Exception {
        if(registerSoftware()){
            deleteSensorEventRules(PushServiceInfo.getSensorRuleName());
            deleteAlarmEventRules(PushServiceInfo.getAlarmRuleName());
        }
    }

    // Register 3rd Party S/W Certificate and get API information
    public boolean registerSoftware() throws Exception {
        File file = new File(PushServiceImpl.class.getResource("/").getFile(), PushServiceInfo.confFileName);

        APIInfo apiInfo;
        if (!file.exists()) {
            // Not registered in VDIP

            // Loading Signed DPL
            URI dplFile = PushServiceImpl.class.getResource("/" + PushServiceInfo.dplFileName).toURI();

            boolean usingKeyStore = true;

            if (usingKeyStore) {
                // Loading keyStore
                KeyStore keyStore  = KeyStore.getInstance(KeyStore.getDefaultType());
                keyStore.load(PushServiceImpl.class.getResourceAsStream("/" + PushServiceInfo.jksFileName), PushServiceInfo.passPhrase.toCharArray());

                // Register 3rd Party S/W Certificate and get API information
                apiInfo = RegisterUtil.registCertificate(PushServiceInfo.registerApiUrl, PushServiceInfo.registerAppName, keyStore, PushServiceInfo.passPhrase, dplFile);
            } else {
                // Loading privateKey
                URI keyFile = PushServiceImpl.class.getResource("/" + PushServiceInfo.keyFileName).toURI();

                // Register 3rd Party S/W Certificate and get API information
                apiInfo = RegisterUtil.registCertificate(PushServiceInfo.registerApiUrl, PushServiceInfo.registerAppName, keyFile, dplFile);
            }

            if (apiInfo != null) {
                LOGGER.debug("[" + PushServiceInfo.registerAppName + "] has been registered successfully.");
                LOGGER.debug(apiInfo.toString());

                username = apiInfo.getUsername();
                password = apiInfo.getPassword();
                consumerKey = apiInfo.getConsumerKey();
                consumerSecret = apiInfo.getConsumerSecret();

                IOUtils.write(RegisterUtil.mapper.writeValueAsString(apiInfo), new FileOutputStream(file));
                return true;
            } else {
                LOGGER.debug("[" + PushServiceInfo.registerAppName + "] already registered but access info doesn't exist.");
                return false;
            }
        } else {
            // Already registered in VDIP
            LOGGER.debug("apiinfo.json file does exist.");

            String info = IOUtils.toString(file.toURI());
            apiInfo = RegisterUtil.mapper.readValue(info, new TypeReference<APIInfo>(){});

            username = apiInfo.getUsername();
            password = apiInfo.getPassword();
            consumerKey = apiInfo.getConsumerKey();
            consumerSecret = apiInfo.getConsumerSecret();
            return true;
        }
    }

    public void startSensor() throws Exception {
    // 1. Create a CEP event rule for 3rd party
        if (createSensorEventRule()) {
            LOGGER.debug("\n:+:+:+:+ Waiting for seconds to refresh CEP event modules :+:+:+:+");

            // 3. Get event data using WebScoket
            getDataUsingWebSocket(PushServiceInfo.pushApiUrl + "/sensor", PushServiceInfo.getSensorRuleName());
        } else {
            LOGGER.debug("create SensorEventRule has failed");
            init();
        }
    }

    @Override
    public boolean isConnected() {
        return this.websocketClient != null && this.websocketClient.isAlive();
    }

    public void startAlarm() throws Exception{
        if (createAlarmEventRule()) {
            LOGGER.debug("\n:+:+:+:+ Waiting for seconds to refresh CEP event modules :+:+:+:+");

            // 3. Get event data using WebScoket
            getDataUsingWebSocket(PushServiceInfo.pushApiUrl + "/alarm", PushServiceInfo.getAlarmRuleName());
        }else {
            LOGGER.debug("create AlarmEventRule has failed");
            init();
        }
    }

    /**
     * Close a websocket session
     */
    public void closeWebSocket() {
        if (websocketClient != null) {
            websocketClient.close();
            LOGGER.debug("Close a websocket session");
        }
    }

    /**
     * Get event data using WebSocket
     *
     * @throws URISyntaxException
     * @throws InterruptedException
     */
    private void getDataUsingWebSocket(String endpointURI, String ruleName) throws URISyntaxException {
        final boolean ackMode = true;

        EventHandler handler = new EventHandler() {
            @Override
            public void handleMessageEvent(String msg) {

                /** describe something to do */
                LOGGER.trace("Received Message via WebSocket : {}, {}", ruleName, msg);

                if(template != null) {
                    String payload = myParser.parse(msg, getType(ruleName));

                    if(!(StringUtils.isEmpty(payload))) {
                        LOGGER.trace("Sent Message via WebSocket : " + payload);

                        template.convertAndSend(DESTINATION, new Greeting(0L, payload));
                    }
            }

                /** in case of ackMode is enable, invoke sendAck or sendNack to receive next message */
                sendAck();// sendNack();
            }

            @Override
            public void handleCloseEvent(int statusCode, String reason) {
                /** describe something to do */
                LOGGER.debug("Status Code : " + statusCode + ", Reason : " + reason);
                try {
                    deleteSensorEventRules(ruleName);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void handleErrorEvent(Throwable t) {
                t.printStackTrace();
            }
        };

        websocketClient = new WebSocketClient(new URI(endpointURI), username, password, ruleName, ackMode, handler);
        websocketClient.start();


        /**
         * Thread will not be stopped. If you want to stop the websocketClient thread, invoke close() manually.
         * {@link com.hhi.vaas.platform.sample.SampleAPIGatewayClient#closeWebSocket()}.
         */
    }

    private ModelType getType(String ruleName) {
        return ruleName.equals(PushServiceInfo.getSensorRuleName())? ModelType.DATA : ruleName.equals(PushServiceInfo.getAlarmRuleName())?ModelType.ALARM:null;
    }



    /**
     * Delete a CEP vdm sensor event rule for 3rd party
     *
     * @param ruleName
     * @throws Exception
     */
    private boolean deleteSensorEventRules(String ruleName) throws Exception {
        return deleteRules("deleteSensorRule", ruleName);
    }

    /**
     * Delete a CEP alarm event rule for 3rd party
     *
     * @param ruleName
     * @throws Exception
     */
    private boolean deleteAlarmEventRules(String ruleName) throws Exception {
        return deleteRules("deleteAlarmRule", ruleName);
    }

    private boolean deleteRules(String ruleType, String ruleName) throws Exception {
        if (apiGatewayClient == null) {
            initClient();
        }

        String apiUrl = PushServiceInfo.cepApiUrl + "/" + ruleType + "?ruleName=" + ruleName;

        Response response = apiGatewayClient.deleteRequest(apiUrl);

        return response.getStatus() == Response.Status.OK.getStatusCode();
    }

    /**
     * Get an event data using REST API
     *
     * @throws Exception
     */
    public void getDataUsingRest(String url) throws Exception {
        if (apiGatewayClient == null) {
            initClient();
        }

        Response response = apiGatewayClient.getRequest(url);

        /** in case of HTTP Resopnse is 200 OK */
        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
            String msg = response.readEntity(String.class);
            LOGGER.debug("Received Message via REST API : " + msg);
        } else {
            LOGGER.debug("HttpResonse : " + response);
        }
    }

    private boolean createSensorEventRule() throws Exception {
		String fullPaths = "*";

        Integer timeWindow = 1;

        return createRule(PushServiceInfo.cepApiUrl + "/createSensorRule", fullPaths, timeWindow, PushServiceInfo.getSensorRuleName());
        
    }

    private boolean createAlarmEventRule() throws Exception {

        String fullPaths = "*";
        Integer timeWindow = 2;

        return createRule(PushServiceInfo.cepApiUrl + "/createAlarmRule", fullPaths, timeWindow, PushServiceInfo.getAlarmRuleName());
    }

    private boolean createRule(String apiUrl, String fullPaths, Integer timeWindow, String ruleName) throws Exception {
        if (apiGatewayClient == null) {
            initClient();
        }

        return apiGatewayClient.postRequest(
                apiUrl,
                ("ruleName=" + URLEncoder.encode(ruleName, "UTF-8") + "&" +
                        "fullPaths=" + URLEncoder.encode(fullPaths, "UTF-8") + "&" +
                        "timeWindow=" + timeWindow).getBytes()
                )
                .getStatus() == Response.Status.OK.getStatusCode();
    }

    private void initClient() {
        apiGatewayClient = new APIGatewayClient(PushServiceInfo.tokenApiUrl, username, password, consumerKey, consumerSecret);
    }

}
