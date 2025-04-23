package com.example.csc311finalcapstoneprojectgroup04.Eureka;

import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.EurekaAccept;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.EurekaClient;
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
@SpringBootApplication
@EnableDiscoveryClient
class ClientEureka implements CommandLineRunner {

    @Autowired
    private EurekaInstanceConfigBean instance;
    //private DiscoveryClient discovery;
    @Autowired
    private ApplicationInfoManager applicationInfoManager;
    @Autowired
    private EurekaClient eurekaClient;


    List<InstanceInfo> instances;
    public void registerLobby(String username, int numPlayers, boolean active) {
        Map<String, String> metadata = instance.getMetadataMap();
        metadata.put("host-name", username);
        metadata.put("current-players", String.valueOf(numPlayers));
        metadata.put("active-game", String.valueOf(active));
        instance.setMetadataMap(metadata);
        applicationInfoManager.registerAppMetadata(metadata);
        applicationInfoManager.setInstanceStatus(InstanceInfo.InstanceStatus.UP);
        //applicationInfoManager.setInstanceStatus(InstanceInfo.InstanceStatus.UP);
    }
    public void printLobbies() {
        for(InstanceInfo instanceInfo : instances) {
            System.out.println(instanceInfo);
        }
    }
    public void fillList() {
        if (eurekaClient.getApplication("EUREKACLIENTTEST") != null) {
            List<InstanceInfo> instances = eurekaClient.getApplication("EUREKACLIENTTEST").getInstances();
            for (InstanceInfo info : instances) {
                System.out.println("Found instance: " + info.getMetadata() + info.getIPAddr());
            }
        } else {
            System.out.println("No instances found.");
        }
    }
    public static void main(String[] args) {
        SpringApplication.run(ClientEureka.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        registerLobby("max", 10, true);
        Thread.sleep(10000); //only if necessary
        fillList();
    }
}