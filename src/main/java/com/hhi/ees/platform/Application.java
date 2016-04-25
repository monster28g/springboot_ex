package com.hhi.ees.platform;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@SpringBootApplication
@PropertySources({
        @PropertySource(value = "gateway.properties", ignoreResourceNotFound = true),
        @PropertySource(value = "db.properties", ignoreResourceNotFound = true),
        @PropertySource(value = "push_service.properties", ignoreResourceNotFound = true)
})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
