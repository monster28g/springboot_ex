package com.hhi.ees.platform;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@SpringBootApplication
@PropertySources({
        @PropertySource(value = "/properties/gateway.properties", ignoreResourceNotFound = true),
        @PropertySource(value = "/properties/db.properties", ignoreResourceNotFound = true),
        @PropertySource(value = "/properties/push_service.properties", ignoreResourceNotFound = true)
})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
