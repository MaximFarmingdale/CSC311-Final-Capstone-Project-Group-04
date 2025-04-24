package com.example.csc311finalcapstoneprojectgroup04;

import javafx.stage.Stage;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * Initializes the stage after listening for a stage ready event
 */
@Component
public class StageInitializer implements ApplicationListener<TypeApplication.StageReadyEvent> {
    /**
     * StageReadyEvent listener that listens for a ready stage.
     * @param event the event to listen to
     */
    @Override
    public void onApplicationEvent(TypeApplication.StageReadyEvent event) {
        Stage stage = event.getStage();
    }
}
