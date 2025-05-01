package com.example.csc311finalcapstoneprojectgroup04.JavaFXControllers;

import com.example.csc311finalcapstoneprojectgroup04.Lobby.Lobby;
import com.example.csc311finalcapstoneprojectgroup04.NetworkMessagesandUpdate.Message;
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
import javafx.scene.input.KeyCode;
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

    private String raceText;
    private String[] raceWords;
    private double racePercentage;
    private int raceWordindex = 0;
    private int raceIndex = 0;
    private RaceUpdate raceUpdate;
    private Message message;
    private String typedString = "";
    private String untypedString = "";

    private ObservableList<RaceUpdate> raceUpdates;
    @FXML
    void SendMessage(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            message.setMessage(messageField.getText());
            client.sendMessage(message);
            messageField.clear();
        }
    }

    @FXML
    void sendRaceUpdate(KeyEvent event) {
        if(lobby.getActiveRace()) {
            if (raceWords.length - 1 == raceWordindex) {
                if (raceField.getText()
                        .trim()
                        .equals(raceWords[raceWordindex])) {//if it's the last word
                    raceUpdate.incrementWordIndex();
                    raceUpdate.setProgress(1);
                    //endOfRace(raceUpdate);
                }
            } else if (raceField.getText().equals(raceWords[raceWordindex])) {
                raceIndex += raceWords[raceWordindex].length() + 1;
                //sets the untyped and typed messages to their respective new index
                typedString = raceText.substring(0, raceIndex);
                untypedString = raceText.substring(raceIndex);
                //updating values
                raceWordindex++;
                raceUpdate.incrementWordIndex();
                racePercentage = (double) raceWordindex / raceWords.length;
                raceUpdate.setProgress(racePercentage);
                //sending messages
                client.sendMessage(raceUpdate);
                //clearing the field
                raceField.clear();
                typedLabel.setText(typedString);
                untypedLabel.setText(untypedString);
                System.out.println(untypedString.toString() + " " + typedString.toString());
            }
        }

    }
    public void enterClientScreen(User user, Lobby lobby) {
        this.user = user;
        this.lobby = lobby;
        try {
            raceUpdates = FXCollections.observableArrayList(new ArrayList<>());
            new Thread(client = new Client(new Socket(lobby.getLobbyIP(),12345), user.getUsername(), raceUpdates));
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
