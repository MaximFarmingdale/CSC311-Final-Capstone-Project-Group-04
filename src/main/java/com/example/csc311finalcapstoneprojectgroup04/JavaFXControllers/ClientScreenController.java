package com.example.csc311finalcapstoneprojectgroup04.JavaFXControllers;

import com.example.csc311finalcapstoneprojectgroup04.Lobby.Lobby;
import com.example.csc311finalcapstoneprojectgroup04.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class ClientScreenController implements Initializable {
    private User user;
    @Autowired
    private ApplicationContext applicationContext;

    @FXML
    private TextField messageField, raceField;

    @FXML
    private VBox messageVbox;

    @FXML
    private ScrollPane messagefield;

    @FXML
    private Label typedLabel, untypedLabel;

    @FXML
    void SendMessage(KeyEvent event) {

    }

    @FXML
    void sendRaceUpdate(KeyEvent event) {

    }
    public void enterClientScreen(User user, Lobby lobby) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
