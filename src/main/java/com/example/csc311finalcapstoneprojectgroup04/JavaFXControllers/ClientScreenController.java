package com.example.csc311finalcapstoneprojectgroup04.JavaFXControllers;

import com.example.csc311finalcapstoneprojectgroup04.Lobby.Lobby;
import com.example.csc311finalcapstoneprojectgroup04.NetworkMessagesandUpdate.RaceUpdate;
import com.example.csc311finalcapstoneprojectgroup04.TCPNetworking.Client;
import com.example.csc311finalcapstoneprojectgroup04.User;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
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

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class ClientScreenController implements Initializable {
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
    private User user;
    private Lobby lobby;
    private Client client;
    private ObservableList<RaceUpdate> raceUpdates;
    @FXML
    void SendMessage(KeyEvent event) {

    }

    @FXML
    void sendRaceUpdate(KeyEvent event) {

    }
    public void enterClientScreen(User user, Lobby lobby) {
        this.user = user;
        this.lobby = lobby;
        try {
            raceUpdates = FXCollections.observableArrayList(new ArrayList<>());
            client = new Client(new Socket(lobby.getLobbyIP(),12345), user.getUsername(), raceUpdates);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // adds a listener which can be used for animations.
        raceUpdates.addListener(new ListChangeListener<RaceUpdate>() {
            @Override
            public void onChanged(Change<? extends RaceUpdate> c) {
                while (c.next()) {
                    if (c.wasAdded()) {
                        System.out.println("added");
                    }
                    else if (c.wasPermutated())//check if this is needed
                        System.out.println("removed");

                    else if (c.wasUpdated()) {
                        System.out.println("updated");
                    }
                }
            }
        });


    }
}
