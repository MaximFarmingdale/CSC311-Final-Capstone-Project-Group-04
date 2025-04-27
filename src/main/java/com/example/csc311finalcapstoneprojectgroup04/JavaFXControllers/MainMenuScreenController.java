package com.example.csc311finalcapstoneprojectgroup04.JavaFXControllers;

import com.example.csc311finalcapstoneprojectgroup04.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenuScreenController {

    @FXML
    private Button exitButton;

    @FXML
    private Button optionsButton;

    @FXML
    private Button playButton;

    private User user;

    @FXML
    void exitProgram(ActionEvent event) {

    }

    @FXML
    void openPlayMenu(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("PlayMenu.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Play Menu");
            Scene scene = new Scene(root, 320, 180 );
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

        @FXML
    void optionPullUp(ActionEvent event) {

    }
    public void enterMainMenu(User event) {
        user = event;
    }
    @FXML
    void goToWaitingRoom(ActionEvent event) {
        try {
            Stage stage = (Stage) exitButton.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("WaitingRoom.fxml"));
            Parent newRoot = loader.load();
            WaitingRoomController controller = loader.getController();
            controller.enterWaitingRoom(user);
            Scene scene = new Scene(newRoot, 1270, 720);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void goToHostScreen(ActionEvent event) {
        try {
            Stage stage = (Stage) exitButton.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("HostScreen.fxml"));
            Parent newRoot = loader.load();
            HostScreenController controller = loader.getController();
            controller.enterHostScreen(user);
            Scene scene = new Scene(newRoot, 1270, 720);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}