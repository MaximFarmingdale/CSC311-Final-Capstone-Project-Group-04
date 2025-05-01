package com.example.csc311finalcapstoneprojectgroup04.TCPNetworking;

import com.example.csc311finalcapstoneprojectgroup04.Lobby.Lobby;
import com.example.csc311finalcapstoneprojectgroup04.NetworkMessagesandUpdate.Message;
import com.example.csc311finalcapstoneprojectgroup04.NetworkMessagesandUpdate.RaceUpdate;
import com.example.csc311finalcapstoneprojectgroup04.User;
import javafx.collections.ObservableList;

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
public class Client {
    private Socket socket;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private String username;
    private ObservableList<RaceUpdate> raceUpdates;

    /**
     * Constructs a client which uses a socket to make ObjectOutputStream and
     * ObjectOutputStream.
     * @param socket the provided socket.
     * @param username the username from username.
     */
    public Client(Socket socket, String username, ObservableList<RaceUpdate> raceUpdates) {
        try {
            this.socket = socket;
            this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            this.objectInputStream = new ObjectInputStream(socket.getInputStream());
            this.username = username;
            objectOutputStream.writeObject(username);//check if it's necessary to send a new line
            objectOutputStream.flush();
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
    public void getMessage() {
        new Thread(() -> {
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
        }).start();
    }

    /**
     * Process a String Object from the network
     * @param message
     */
    private void processMessage(String message) {
        try {
            objectOutputStream.writeObject(message);
            objectOutputStream.flush();
        } catch (IOException e) {
            closeClient();
        }
    }

    /**
     * Process a User Object from the network
     * @param user
     */
    private void processMessage(User user) {
        try {
            objectOutputStream.writeObject(user);
            objectOutputStream.flush();
        } catch (IOException e) {
            closeClient();
        }
    }

    /**
     * Process a Lobby Object from the network
     * @param lobby
     */
    private void processMessage(Lobby lobby) {
        try {
            objectOutputStream.writeObject(lobby);
            objectOutputStream.flush();
        } catch (IOException e) {
            closeClient();
        }
    }

    /**
     * Process a Message Object from the network
     * @param message
     */
    private void processMessage(Message message) {
        try {
            objectOutputStream.writeObject(message);
            objectOutputStream.flush();
        } catch (IOException e) {
            closeClient();
        }
    }

    /**
     * Process a RaceUpdate Object from the network
     * @param raceUpdate
     */
    private void processMessage(RaceUpdate raceUpdate) {
        boolean wasAdded = false;
        for (RaceUpdate r : raceUpdates) {
            if (r.getUsername().equals(raceUpdate.getUsername())) {
                r = raceUpdate;
                wasAdded = true;
            }
        }
        if(wasAdded)
            raceUpdates.add(raceUpdate);
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
}