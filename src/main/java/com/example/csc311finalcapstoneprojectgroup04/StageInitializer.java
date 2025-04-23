package com.example.csc311finalcapstoneprojectgroup04;

import javafx.stage.Stage;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class StageInitializer implements ApplicationListener<TypeApplication.StageReadyEvent> {

    @Override
    public void onApplicationEvent(TypeApplication.StageReadyEvent event) {
        Stage stage = event.getStage();

    }
}
