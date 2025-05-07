package com.example.csc311finalcapstoneprojectgroup04;

import com.example.csc311finalcapstoneprojectgroup04.Eureka.ClientEureka;
import javafx.application.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EnableDiscoveryClient
public class TypeSpringBootApplication {
    private static ConfigurableApplicationContext applicationContext;
    public static void main(String[] args) {
        applicationContext = new SpringApplicationBuilder(TypeSpringBootApplication.class).run(args);
        Application.launch(TypeApplication.class, args);
    }
    public static ConfigurableApplicationContext getApplicationContext() {
        return applicationContext;
    }
}
