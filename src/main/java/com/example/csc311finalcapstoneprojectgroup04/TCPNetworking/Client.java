package com.example.csc311finalcapstoneprojectgroup04.TCPNetworking;

import com.example.csc311finalcapstoneprojectgroup04.Lobby.Lobby;
import com.example.csc311finalcapstoneprojectgroup04.NetworkMessagesandUpdate.Message;
import com.example.csc311finalcapstoneprojectgroup04.NetworkMessagesandUpdate.RaceUpdate;
import com.example.csc311finalcapstoneprojectgroup04.User;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Class used by clients to join lobbies and allows said clients to send Messages,
 * Users, and RaceUpdates and lets users receive Messages, Users, RaceUpdates, and
 * lobbies. Messages are added as text in FX Java Hbox, so that they are displayed
 * visually. Once a message is sent, it will be sent to
 */
public class Client implements Runnable{
    private Socket socket;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private String username;
    private ObservableList<RaceUpdate> raceUpdates;
    private Message message;
    private Lobby lobby;
    private String text;
    private User user;
    private VBox messageBox;
    private final ReadOnlyObjectWrapper<Lobby> lobbyRead = new ReadOnlyObjectWrapper<>();
    /**
     * Constructs a client which uses a socket to make ObjectOutputStream and
     * ObjectOutputStream.
     * @param socket the provided socket.
     * @param username the username from username.
     */
    public Client(Socket socket, String username, VBox messageBox, ObservableList<RaceUpdate> raceUpdates) {
        try {
            this.socket = socket;
            this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            this.objectInputStream = new ObjectInputStream(socket.getInputStream());
            this.username = username;
            objectOutputStream.writeObject(username);//check if it's necessary to send a new line
            objectOutputStream.flush();
            this.messageBox = messageBox;
        } catch (IOException e) {
            closeClient();
        }
    }

    /**
     *
     * @param message
     */
    public void sendMessage(Message message) {
        try {
            if (socket.isConnected()) {
                objectOutputStream.writeObject(message);
                objectOutputStream.flush();
            }
        } catch (IOException e) {
            closeClient();
        }
    }

    /**
     * @deprecated
     * Allows the client to send users to all other clients and the host.
     * @param user user message.
     */
    public void sendMessage(User user) {
        try {
            objectOutputStream.writeObject(user);
            objectOutputStream.flush();
        } catch (IOException e) {
            closeClient();
        }
    }

    /**
     * Allows the client to send users to all other clients and the host.
     * @param raceUpdate
     */
    public void sendMessage(RaceUpdate raceUpdate) {
        try {
            objectOutputStream.writeObject(raceUpdate);
            objectOutputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Waits for input and calls various methods to process the input
     */
    @Override
    public void run() {
        try {
            while (true) {
                Object receivedObject = objectInputStream.readObject();
                if(receivedObject instanceof User) {
                    processMessage((User) receivedObject);
                }
                else if(receivedObject instanceof String) {
                    processMessage((String) receivedObject);
                }
                else if(receivedObject instanceof Message) {
                    processMessage((Message) receivedObject);
                }
                else if(receivedObject instanceof Lobby) {
                    processMessage((Lobby) receivedObject);
                }
                else if(receivedObject instanceof RaceUpdate) {
                    processMessage((RaceUpdate) receivedObject);
                }
            }
            } catch (IOException | ClassNotFoundException e) {
                closeClient();
            }
    }

    /**
     * Process a String Object from the network and adds it to the GUI
     * @param message
     */
    private void processMessage(String message) {
        Label label = new Label(message);
        label.setAlignment(Pos.BASELINE_LEFT);
        Platform.runLater(() -> {
            messageBox.getChildren().add(label);
        });
    }

    /**
     * @deprecated
     * Process a User Object from the network
     * @param currentUser
     */
    private void processMessage(User currentUser) {
        user = user;
    }

    /**
     * Process a Lobby Object from the network
     * @param currentLobby
     */
    private void processMessage(Lobby currentLobby) {
        lobbyRead.set(currentLobby);
    }

    /**
     * Process a Message Object from the network and adds it to the GUI
     * @param message
     */
    private void processMessage(Message message) {
        Label label = new Label(message.getSender() + ": " + message.getMessage());
        label.setAlignment(Pos.BASELINE_LEFT);
        Platform.runLater(() -> {
            messageBox.getChildren().add(label);
        });
    }

    /**
     * Process a RaceUpdate Object from the network
     * @param currentRaceUpdate
     */
    private void processMessage(RaceUpdate currentRaceUpdate) {
        boolean wasAdded = false;
        for (RaceUpdate r : raceUpdates) {
            if (r.getUsername().equals(currentRaceUpdate.getUsername())) {
                r = currentRaceUpdate;
                wasAdded = true;
            }
        }
        if(wasAdded)
            raceUpdates.add(currentRaceUpdate);
    }
    /**
     * Releases the resources of Client.
     */
    public void closeClient() {
        try {
            if (objectOutputStream != null) {
                objectOutputStream.close();
            }
            if (objectInputStream != null) {
                objectInputStream.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Getter for ReadOnlyObjectProperty value
     * @return value
     */
    public ReadOnlyObjectProperty<Lobby> lobbyProperty() {
        return lobbyRead.getReadOnlyProperty();
    }
    //no setters needed
    /**
     * Getter for LobbyObservable
     * @return LobbyObservable
     */
    public ObservableValue<Lobby> lobbyObservableProperty() {
        return lobbyRead;
    }

}