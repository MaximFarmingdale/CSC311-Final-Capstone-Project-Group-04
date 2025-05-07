package com.example.csc311finalcapstoneprojectgroup04.JavaFXControllers;

import com.example.csc311finalcapstoneprojectgroup04.Lobby.Lobby;
import com.example.csc311finalcapstoneprojectgroup04.NetworkMessagesandUpdate.Message;
import com.example.csc311finalcapstoneprojectgroup04.NetworkMessagesandUpdate.RaceUpdate;
import com.example.csc311finalcapstoneprojectgroup04.TCPNetworking.Client;
import com.example.csc311finalcapstoneprojectgroup04.User;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
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
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.CopyOnWriteArrayList;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * This is the JavaFX controller that controls ClientScreen.fxml,
 * it allows the user to complete in the type racing game and also
 * type messages in the chat. There is also functionality for
 * an end race effect after one ends. The class manages
 * an array of RaceUpdates, however, it does not currently do anything
 * with them and just waits for the Host to announce a winner. In the
 *  future it could be used for animation.
 *
 */
@Component
public class ClientScreenController {
    @Autowired
    private ApplicationContext applicationContext;

    @FXML
    private TextField messageField, raceField;

    @FXML
    private VBox messageVbox;
    @FXML
    Text typedText, untypedText;
    private User user;
    private Lobby lobby;
    private Client client;

    private String raceText;
    private String[] raceWords;
    private double racePercentage;
    private int raceWordindex = 0;
    private int raceLetterIndex = 0;
    private RaceUpdate raceUpdate;
    private Message message;
    private String typedString = "";
    private String untypedString = "";
    private Socket socket;
    private final ReadOnlyObjectWrapper<Lobby> lobbyRead = new ReadOnlyObjectWrapper<>();
    private ObservableList<RaceUpdate> raceUpdates;

    /**
     * To send chat messages to other users
     * @param event
     */
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
        if(lobby.getActiveRace()) { //if it is an active race
            //if the last letter was typed
            if (raceWords.length - 1 == raceWordindex && Objects.equals(raceField.getText(), raceWords[raceWordindex])) {
                if (raceField.getText()
                        .trim() //to get rid of any whitespace
                        .equals(raceWords[raceWordindex])) {//if it's the last word
                    typedText.setText(raceText);
                    untypedText.setText("");
                    //since raceupdate automatically tells if someone won and calls the
                    //endRace method, we don't need to call it just update raceUpdate
                    raceUpdate.incrementWordIndex();
                    raceUpdate.setProgress(1);
                    raceUpdate.setWinner(true);
                    client.sendMessage(raceUpdate); //send race update to everyone else
                }
                raceField.clear();
            }
            //if its not the last letter and the word matches after space
            else if (raceField.getText().trim().equals(raceWords[raceWordindex]) && event.getCode() == KeyCode.SPACE) {
                raceLetterIndex += raceWords[raceWordindex].length() + 1;
                //sets the untyped and typed messages to their respective new index
                typedString = raceText.substring(0, raceLetterIndex);
                untypedString = raceText.substring(raceLetterIndex);
                //updating values
                raceWordindex++;
                raceUpdate.incrementWordIndex();
                racePercentage = (double) raceWordindex / raceWords.length;
                raceUpdate.setProgress(racePercentage);
                //sending messages
                client.sendMessage(raceUpdate);
                //clearing the field
                typedText.setText(typedString);
                untypedText.setText(untypedString);
                System.out.println(untypedString.toString() + " " + typedString.toString());
                raceField.clear();
            }
        }
        if(!lobby.getActiveRace()) {
            raceField.clear(); //to make sure the user isn't typing prematurely.
        }
    }

    /**
     * Method to send User and Lobby information to the ClientScreenController.
     * It uses this information to make new Client as well as a
     * @param user
     * @param lobby
     */
    public void enterClientScreen(User user, Lobby lobby) {
        this.user = user;
        this.lobby = lobby;
        try {
            socket = new Socket(lobby.getLobbyIP(),12345);
            lobbyRead.set(lobby);
            new Thread(client = new Client(socket, new ObjectOutputStream(
                    socket.getOutputStream()),
                    new ObjectInputStream(socket.getInputStream()),
                    user.getUsername(), lobby, messageVbox, raceUpdates, lobbyRead)).start();
        } catch (IOException e) {
            throw new RuntimeException(e);

        }
        message = new Message(user.getUsername(), "");
        raceUpdate = new RaceUpdate(user.getUsername());
        raceUpdates = FXCollections.observableArrayList(new CopyOnWriteArrayList<>());
        raceUpdates.add(raceUpdate);
        configureListeners();
    }


    public void configureListeners() {
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
        //ad a listener to the lobby to change status based on if the game is active or not
        lobbyRead.addListener((observable, oldValue, newValue) -> {
            //only checks for changes if the active race value changes
            if (oldValue == null) {
                if (newValue.getActiveRace()) {
                    startRace();
                }
                return;
            }
            if(oldValue.getActiveRace() && !newValue.getActiveRace()) {
                endRace();
            }

            if(!oldValue.getActiveRace() && newValue.getActiveRace()) {
                startRace();
            }
        });
    }
    public void startRace() {
        lobby = lobbyRead.get();
        raceText = lobby.getText();
        typedText.setText("");
        untypedText.setText(raceText);
        raceWords = raceText.split(" ");
        raceWordindex = 0;
        raceLetterIndex = 0;
    }
    public void endRace() {
        System.out.println("end of race");
        raceUpdate.setProgress(0);
        raceUpdate.setWinner(false);
    }

}
