package com.example.csc311finalcapstoneprojectgroup04.Eureka;

import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.EurekaAccept;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.EurekaEventListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EurekaInstanceConfigBean;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Class that enables clients to fetch lobby information from metadata in Eureka into a list called instances
 * and also lets hosts register their lobbies to Eureka and update the metadata when the lobby changes.
 */
@Service
public class ClientEureka {
    @Autowired
    private EurekaInstanceConfigBean instance;
    //private DiscoveryClient discovery;
    @Autowired
    private ApplicationInfoManager applicationInfoManager;
    @Autowired
    private EurekaClient eurekaClient;
    List<InstanceInfo> instances;

    /**
     * Registers a lobby to eureka for clients to find, adds information such as host username, current players
     * and if there is an active game to metadata.
     * @param username host username
     */
    public void registerLobby(String username) {
        Map<String, String> metadata = instance.getMetadataMap();
        metadata.put("host-name", username);
        metadata.put("current-players", "1");
        metadata.put("active-game", "false");
        instance.setMetadataMap(metadata);
        applicationInfoManager.registerAppMetadata(metadata);
        applicationInfoManager.setInstanceStatus(InstanceInfo.InstanceStatus.UP);
    }
    public void updateLobby(String username, int currentPlayers, boolean activeGame) {
        Map<String, String> metadata = instance.getMetadataMap();
        metadata.put("host-name", username);
        metadata.put("current-players", String.valueOf(currentPlayers));
        metadata.put("active-game", String.valueOf(activeGame));
        instance.setMetadataMap(metadata);
        applicationInfoManager.registerAppMetadata(metadata);
    }
    //testing method
    public void printLobbies() {
        for(InstanceInfo instanceInfo : instances) {
            System.out.println(instanceInfo);
        }
    }
    public void hide() {
    }

    /**
     * fills the instances' list with EurekaClient instances
     */
    public List<InstanceInfo> fillList() {
        if (eurekaClient.getApplication("TYPING_RACE") != null) {
            for (InstanceInfo instanceInfo : eurekaClient.getApplication("TYPING_RACE").getInstances()) {
                if (instanceInfo.getStatus() == InstanceInfo.InstanceStatus.UP) {
                    instances.add(instanceInfo);
                    System.out.println("Found lobby: " + instanceInfo.getMetadata());
                }
            }
        } else {
            System.out.println("No instances found.");
        }
        return instances;
    }
}