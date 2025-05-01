package com.example.csc311finalcapstoneprojectgroup04.JavaFXControllers;

import com.example.csc311finalcapstoneprojectgroup04.Lobby.Lobby;
import com.example.csc311finalcapstoneprojectgroup04.NetworkMessagesandUpdate.Message;
import com.example.csc311finalcapstoneprojectgroup04.NetworkMessagesandUpdate.RaceUpdate;
import com.example.csc311finalcapstoneprojectgroup04.TCPNetworking.Server;
import com.example.csc311finalcapstoneprojectgroup04.User;
import com.netflix.appinfo.InstanceInfo;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.URL;
import java.net.UnknownHostException;
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
    private Server server;
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
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            lobby = new Lobby(Inet4Address.getLocalHost().getHostAddress(), user.getUsername());
            server = new Server(new ServerSocket(12345), user.getUsername(), lobby, messageVbox, raceUpdates);
            new Thread(server).start();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        raceUpdates.addListener(new ListChangeListener<RaceUpdate>() {
            @Override
            public void onChanged(Change<? extends RaceUpdate> c) {
                while (c.next()) {
                    if (c.wasAdded()) {
                        for(RaceUpdate r : c.getAddedSubList()) {
                            if(r.isWinner())
                                endOfRace(r);
                        }
                    }
                    else if (c.wasPermutated())//check if this is needed
                        System.out.println(c.getRemoved());

                    else if (c.wasUpdated()) {
                        for (RaceUpdate r : raceUpdates) {
                            if(r.isWinner())
                                endOfRace(r);
                        }
                    }
                }
            }

        });
    }
    /**
     * Sends a message to all clients and adds Message to messageVbox
     * @param event
     */
    @FXML
    void SendMessage(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            message.setMessage(messageField.getText());
            server.sendMessage(message);
            messageField.clear();
        }
    }
    /**
     * Sends a RaceUpdate to all clients and adds it to the raceUpdateList
     * @param event
     */
    @FXML
    void sendRaceUpdate(KeyEvent event) {
        if(lobby.getActiveRace()) {
            if (raceWords.length - 1 == raceWordindex) {
                if (raceField.getText()
                        .trim()
                        .equals(raceWords[raceWordindex])) {//if it's the last word
                    raceUpdate.incrementWordIndex();
                    raceUpdate.setProgress(1);
                    endOfRace(raceUpdate);
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
                server.sendMessage(raceUpdate);
                //clearing the field
                raceField.clear();
                typedLabel.setText(typedString);
                untypedLabel.setText(untypedString);
                System.out.println(untypedString.toString() + " " + typedString.toString());
            }
        }
    }
    /**
     * Sends user information to HostScreenController
     * @param currentUser
     */
    public void enterHostScreen(User currentUser) {
        user = currentUser;
        raceUpdate = new RaceUpdate(user.getUsername());
        message = new Message(currentUser.getUsername(),"");
    }
    public void endOfRace(RaceUpdate update) {

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
        raceWords = raceText.split(" ");
    }
}
