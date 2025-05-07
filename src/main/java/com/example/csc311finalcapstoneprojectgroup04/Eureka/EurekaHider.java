package com.example.csc311finalcapstoneprojectgroup04.Eureka;
import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.InstanceInfo;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class EurekaHider {
        @Autowired
        private ApplicationInfoManager applicationInfoManager;
        @PostConstruct
        public void setInitialStatusToDown() {
            applicationInfoManager.setInstanceStatus(InstanceInfo.InstanceStatus.DOWN);
        }
    }
