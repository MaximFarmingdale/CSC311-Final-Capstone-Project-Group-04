package com.example.csc311finalcapstoneprojectgroup04;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
/// Application
@SpringBootApplication
public class TypeApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TypeApplication.class.getResource("SplashScreen.fxml"));//change to whatever fxml file you are testing
        Scene scene = new Scene(fxmlLoader.load(), 1270, 720);
        stage.setTitle("Type Application");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}