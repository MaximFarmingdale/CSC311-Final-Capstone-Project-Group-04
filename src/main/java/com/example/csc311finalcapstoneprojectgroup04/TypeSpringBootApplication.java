package com.example.csc311finalcapstoneprojectgroup04;

import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
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
