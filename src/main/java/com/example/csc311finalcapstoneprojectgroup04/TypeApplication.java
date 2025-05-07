package com.example.csc311finalcapstoneprojectgroup04;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

/**
 * JavaFX application that is called by the TypeSpringBootApplication, which launches the
 * JavaFX components.
 * THIS PROGRAM WILL NOT RUN CORRECTLY WITHOUT A EUREKA SERVER RUNNING
 */
public class TypeApplication extends Application {
    private static ConfigurableApplicationContext applicationContext;
    /**
     * Override of the intializer method of the JavaFX application, sets the ConfigurableApplicationContext.
     */
    @Override
    public void init() {
        applicationContext = TypeSpringBootApplication.getApplicationContext();
    }

    /**
     * Start method of the application that loads SplashScreenController
     * @param stage the primary stage for this application, onto which
     * the application scene can be set.
     * Applications may create other stages, if needed, but they will not be
     * primary stages.
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {

        applicationContext.publishEvent(new StageReadyEvent(stage));
        FXMLLoader loader = new FXMLLoader(TypeApplication.class.getResource("/com/example/JavaFX_FXML/SplashScreen.fxml"));//change to whatever fxml file you are testing
        loader.setControllerFactory(applicationContext::getBean); //gets beans from spring
        Scene scene = new Scene(loader.load(), 1270, 720);
        stage.setTitle("Type Application");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Stops the application.
     */
    @Override
    public void stop() {
        applicationContext.close();
        Platform.exit();
    }

    /**
     * returns application context for linking beans to JavaFX controllers
     *
     * @return ApplicationEvent
     */
    public static ConfigurableApplicationContext getApplicationEvent() {
        return applicationContext;
    }

    /**
     */
    static class StageReadyEvent extends ApplicationEvent {
        /**
         * StageReadyEvent constuctor
         * @param stage
         */
        public StageReadyEvent(Stage stage) {
            super(stage);
        }

        /**
         * Gets stage
         * @return Stage
         */
        public Stage getStage() {
            return ((Stage) getSource());
        }
    }

}