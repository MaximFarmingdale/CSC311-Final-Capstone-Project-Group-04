package com.example.csc311finalcapstoneprojectgroup04.JavaFXControllers;

import com.example.csc311finalcapstoneprojectgroup04.Lobby.Lobby;
import com.example.csc311finalcapstoneprojectgroup04.NetworkMessagesandUpdate.RaceUpdate;
import com.example.csc311finalcapstoneprojectgroup04.TCPNetworking.Server;
import com.example.csc311finalcapstoneprojectgroup04.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class HostScreenController implements Initializable {
    private User user;
    private Lobby lobby;
    @Autowired
    private ApplicationContext applicationContext;
    @FXML
    private VBox messageVbox;
    @FXML
    private TextField messageField, raceField;
    @FXML
    Label typedLabel, untypedLabel;
    private String raceText;
    private Server server;
    private List<RaceUpdate> raceUpdateList = new ArrayList<>();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            lobby = new Lobby(Inet4Address.getLocalHost().getHostAddress(), user.getUsername());
            server = new Server(new ServerSocket(12345), user.getUsername(), lobby, messageVbox, raceUpdateList);
            new Thread(server).start();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Sends a message to all clients and adds Message to messageVbox
     * @param event
     */
    @FXML
    void SendMessage(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {

        }
    }

    /**
     * Sends a RaceUpdate to all clients and adds it to the raceUpdateList
     * @param event
     */
    @FXML
    void sendRaceUpdate(KeyEvent event) {

    }

    /**
     * Sends user information to HostScreenController
     * @param currentUser
     */
    public void enterHostScreen(User currentUser) {
        user = currentUser;
    }

    /**
     * Starts the race by setting an active race in Lobby, resetting Labels
     * and calling the server start method
     * @param event
     */
    @FXML
    void startRace(ActionEvent event) {
        lobby.setActiveRace(true);
        lobby.generateNewText();
        typedLabel.setText("");
        untypedLabel.setText(lobby.getText());
        server.startRace();
    }
}
