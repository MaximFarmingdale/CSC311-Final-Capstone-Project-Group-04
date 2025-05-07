package com.example.csc311finalcapstoneprojectgroup04.Eureka;
import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.InstanceInfo;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.EurekaInstanceConfigBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
@Component
public class EurekaHider {

    @Autowired
    private ApplicationInfoManager applicationInfoManager;

    @PostConstruct
    public void setInitialStatusToDown() {
        System.out.println(">>> Forcing Eureka status to DOWN at startup");
        applicationInfoManager.setInstanceStatus(InstanceInfo.InstanceStatus.DOWN);
    }
}
