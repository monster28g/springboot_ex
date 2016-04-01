package com.hhi.connected.platform.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

@Service
public class RestApiServiceImpl implements RestApiService{

//    @Autowired
    private RestTemplate restTemplate;


    @Override
    public Object test() {
        restTemplate = new RestTemplate();
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        HttpEntity<Map> entity = new HttpEntity<>(null, headers);
        String url = "http://10.100.16.66:8086/query?q=SHOW+MEASUREMENTS&db=hivaas";
//        ResponseEntity<Map> response = restTemplate.execute(url, HttpMethod.GET, entity, Map.class);
        ;
        ResponseEntity<String> response = null;
        try {
            response = restTemplate.getForEntity(
                    URLDecoder.decode(url, "UTF-8"),
                    String.class);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
//        return response;
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
