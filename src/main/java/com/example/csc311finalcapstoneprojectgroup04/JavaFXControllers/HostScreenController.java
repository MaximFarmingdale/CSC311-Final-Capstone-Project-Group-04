package com.example.csc311finalcapstoneprojectgroup04.JavaFXControllers;

import com.example.csc311finalcapstoneprojectgroup04.Eureka.ClientEureka;
import com.example.csc311finalcapstoneprojectgroup04.Lobby.Lobby;
import com.example.csc311finalcapstoneprojectgroup04.NetworkMessagesandUpdate.Message;
import com.example.csc311finalcapstoneprojectgroup04.NetworkMessagesandUpdate.RaceUpdate;
import com.example.csc311finalcapstoneprojectgroup04.TCPNetworking.Host;
import com.example.csc311finalcapstoneprojectgroup04.TCPNetworking.Server;
import com.example.csc311finalcapstoneprojectgroup04.User;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.ResourceBundle;

@Component
public class HostScreenController implements Initializable {
    private User user;
    private Lobby lobby;
    @Autowired
    ApplicationContext applicationContext;
    @Autowired
    ClientEureka clientEureka;
    @FXML
    private VBox messageVbox;
    @FXML
    private TextField messageField, raceField;
    @FXML
    Text typedText, untypedText;
    @FXML
    Button startRaceButton;
    private Server server;
    private String raceText = "";
    private String[] raceWords;
    private double racePercentage;
    private int raceWordindex = 0;
    private int raceIndex = 0;
    private RaceUpdate raceUpdate;
    private Message message;
    private String typedString = "";
    private String untypedString = "";
    private ObservableList<RaceUpdate> raceUpdates = FXCollections.observableArrayList();
    private Host host;
    private Socket socket;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
    void sendMessage(KeyEvent event) {
        messageField.requestFocus();
        if (event.getCode() == KeyCode.ENTER) {
            message.setMessage(messageField.getText());
            host.sendMessage(message);
            System.out.println(messageField.getText());
            messageField.clear();
        }
    }
    /**
     * Sends a RaceUpdate to all clients and adds it to the raceUpdateList
     * @param event
     */
    @FXML
    void sendRaceUpdate(KeyEvent event) {
        System.out.println(event.getCode());
        if(lobby.getActiveRace() && event.getCode() == KeyCode.SPACE) {
            if (raceWords.length - 1 == raceWordindex) {
                if (raceField.getText()
                        .trim()
                        .equals(raceWords[raceWordindex])) {//if it's the last word
                    raceUpdate.incrementWordIndex();
                    raceUpdate.setProgress(1);
                    raceUpdate.setWinner(true);
                    host.sendMessage(raceUpdate);
                    endOfRace(raceUpdate);
                    typedText.setText(raceText);
                    untypedText.setText("");
                }
                raceField.clear();
            } else if (raceField.getText().trim().equals(raceWords[raceWordindex])) {
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
                host.sendMessage(raceUpdate);
                //clearing the field
                typedText.setText(typedString);
                untypedText.setText(untypedString);
                System.out.println(untypedString.toString() + " " + typedString.toString());
                raceField.clear();
            }
        }
        if(!lobby.getActiveRace()) {
            raceField.clear();
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
        try {
            lobby = new Lobby(Inet4Address.getLocalHost().getHostAddress(), user.getUsername());
            server = new Server(new ServerSocket(12345), user.getUsername(), lobby, clientEureka);
            new Thread(server).start();
            Thread.sleep(200);
            socket = new Socket("localhost", 12345);
            host = new Host(socket, new ObjectOutputStream(socket.getOutputStream()), new ObjectInputStream(socket.getInputStream()), user.getUsername(), messageVbox, raceUpdates, lobby);
            new Thread(host).start();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Thread.currentThread().interrupt();

    }
    public void endOfRace(RaceUpdate update) {
        host.sendMessage(update.getUsername() + " Won!!!!!");
        lobby.setActiveRace(false);
        host.sendMessage(lobby);
        startRaceButton.setDisable(false);
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
        raceText = lobby.getText();
        typedText.setText("");
        untypedText.setText(raceText);
        host.startRace();
        raceWords = raceText.split(" ");
        startRaceButton.setDisable(true);
    }
}
