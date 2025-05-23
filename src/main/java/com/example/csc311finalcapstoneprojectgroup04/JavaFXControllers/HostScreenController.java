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
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.*;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * The Controller for the HostScreen.fxml file. Allows the user to send messages,
 * make progress in the race and to start the race. The main differences between
 * this and the ClientScreenController is that this class allows the user to start the
 * race, and this class manages properties such as Lobbys, which clients cannot control.
 * It also does not have a listener like ClientScreenController for lobbys, and this class
 * also listens for the winner and ends the game when one is found.
 */
@Component
public class HostScreenController {
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
    @FXML
    private ChoiceBox<Lobby.TextSource> textSourceBox;
    private Server server;
    private String raceText = "";
    private String[] raceWords;
    private double racePercentage;
    private int raceWordindex = 0;
    private int raceLetterIndex = 0;
    private RaceUpdate raceUpdate;
    private Message message;
    private String typedString = "";
    private String untypedString = "";
    private ObservableList<RaceUpdate> raceUpdates = FXCollections.observableArrayList();
    private Host host;
    private Socket socket;

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
        if(lobby.getActiveRace()) { //if it is an active race
            //if the last letter was typed
            if (raceWords.length - 1 == raceWordindex && Objects.equals(raceField.getText(), raceWords[raceWordindex])) {
                if (raceField.getText()
                        .trim()
                        .equals(raceWords[raceWordindex])) {//if it's the last word
                    lobby.setActiveRace(false);
                    Lobby temp = new Lobby(lobby.getLobbyIP(), lobby.getLobbyHostName());
                    temp.setCurrentPlayers(lobby.getCurrentPlayers());
                    temp.setText(lobby.getText());
                    temp.setActiveRace(false);
                    host.sendMessage(temp);
                    typedText.setText(raceText);
                    untypedText.setText("");
                    //since raceupdate automatically tells if someone won and calls the
                    //endRace method we dont need to call it just update raceUpdate
                    raceUpdate.incrementWordIndex();
                    raceUpdate.setProgress(1);
                    raceUpdate.setWinner(true);
                    for (int i = 0; i < raceUpdates.size(); i++) {
                        if (raceUpdates.get(i).getUsername().equals(raceUpdate.getUsername())) {
                            raceUpdates.set(i, raceUpdate);
                            break;
                        }
                    }
                    RaceUpdate winnerUpdate = new RaceUpdate(raceUpdate.getUsername());
                    winnerUpdate.setProgress(1);
                    winnerUpdate.setWinner(true);
                    host.sendMessage(winnerUpdate);
                    System.out.println("done");
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
        textSourceBox.getItems().setAll(Lobby.TextSource.values());
        this.user = currentUser;
        this.message = new Message(currentUser.getUsername(),"");
        try {
            this.lobby = new Lobby(publicIP(), user.getUsername());
            this.server = new Server(new ServerSocket(12345), user.getUsername(), lobby, clientEureka, raceUpdates);
            new Thread(server).start();
            Thread.sleep(200);
            this.socket = new Socket("localhost", 12345);
            this.host = new Host(socket, new ObjectOutputStream(socket.getOutputStream()), new ObjectInputStream(socket.getInputStream()), lobby, user.getUsername(), messageVbox, this.raceUpdates);
            raceUpdate = new RaceUpdate(user.getUsername());
            raceUpdates.add(raceUpdate);
            new Thread(host).start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        addListener();
    }
    public void addListener(){
        this.raceUpdates.addListener((ListChangeListener<RaceUpdate>) c -> {
            while (c.next()) {
                if (c.wasAdded()) {
                    for(RaceUpdate r : c.getAddedSubList()) {
                        if(r.isWinner()) {
                            endOfRace(r);
                            break;
                        }
                    }
                }
                else if (c.wasPermutated())//check if this is needed
                    System.out.println(c.getRemoved());

                else if (c.wasUpdated()) {
                    for (RaceUpdate r : raceUpdates) {
                        if(r.isWinner()) {
                            endOfRace(r);
                            break;
                        }
                    }
                }
            }
        });
    }
    public static String publicIP() throws MalformedURLException {
        String urlString = "http://checkip.amazonaws.com/";
        URL url = new URL(urlString);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()))) {
            return br.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * method for ending the race
     * @param update
     */
    public void endOfRace(RaceUpdate update) {
        host.sendMessage(update.getUsername() + " Won!!!!!");
        startRaceButton.setDisable(false);
    }

    /**
     * Starts the race by setting an active race in Lobby, resetting Labels
     * and calling the server start method
     */
    @FXML
    void startRace() {
        if(textSourceBox.getValue() != null)
            lobby.setCurrentSource(textSourceBox.getValue());
        raceUpdate = new RaceUpdate(user.getUsername());
        lobby.generateNewText();
        raceText = lobby.getText();
        raceWordindex = 0;
        raceLetterIndex = 0;
        typedText.setText("");
        untypedText.setText(raceText);
        raceWords = raceText.split(" ");
        startRaceButton.setDisable(true);
        host.startRace();
    }

}
