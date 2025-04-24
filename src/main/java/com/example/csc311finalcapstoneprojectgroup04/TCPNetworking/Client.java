package com.example.csc311finalcapstoneprojectgroup04.TCPNetworking;

import com.example.csc311finalcapstoneprojectgroup04.User;

import java.io.*;
import java.net.Socket;

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

    /**
     * Constructs a client which uses a socket to make ObjectOutputStream and
     * ObjectOutputStream.
     * @param socket the provided socket.
     * @param username the username from username.
     */
    public Client(Socket socket, String username) {
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
     * Allows the client to send messages to all other clients.
     * @param message client message.
     */
    public void sendMessage(String message) {
        try {
            if (socket.isConnected()) {
                objectOutputStream.writeObject(username + ": " + message);
                objectOutputStream.flush();
            }
        } catch (IOException e) {
            closeClient();
        }
    }

    /**
     * Allows the client to send users to all other clients.
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
    //add comments later
    public void getMessage() {
        new Thread(() -> {
            try {
                while (true) {
                    User user;
                    String receivedMessage = "";
                    Object receivedObject = objectInputStream.readObject();
                    if(receivedObject instanceof User) {
                        user = (User) receivedObject;
                    }
                    if(receivedObject instanceof String) {
                        receivedMessage = (String) receivedObject;
                    }
                    System.out.println(receivedMessage);
                }
            } catch (IOException e) {
                closeClient();
            } catch (ClassNotFoundException e) {
                closeClient();
            }
        }).start();
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