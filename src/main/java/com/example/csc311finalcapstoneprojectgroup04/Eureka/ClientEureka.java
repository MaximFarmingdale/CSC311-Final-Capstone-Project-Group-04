package com.example.csc311finalcapstoneprojectgroup04.Eureka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EurekaInstanceConfigBean;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ClientEureka implements CommandLineRunner {

    @Autowired
    private EurekaInstanceConfigBean instance;
    public void registerLobby(String username, int numPlayers, boolean active) {
        Map<String, String> metadata = instance.getMetadataMap();
        metadata.put("host-name", username);
        metadata.put("current-players", String.valueOf(numPlayers));
        metadata.put("active-game", String.valueOf(active));
    }
    public static void main(String[] args) {
        SpringApplication.run(EurekaServer.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        registerLobby("max", 10, true);
    }
}