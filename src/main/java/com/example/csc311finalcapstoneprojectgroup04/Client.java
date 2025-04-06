package com.example.csc311finalcapstoneprojectgroup04;

import java.io.*;
import java.io.*;
import java.net.Socket;
public class Client {
    private Socket socket;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private String username;

    public Client(Socket socket, String username) {
        try {
            this.socket = socket;
            this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            this.objectInputStream = new ObjectInputStream(socket.getInputStream());
            this.username = username;
            objectOutputStream.writeObject(username);//check if its necessary to send a new line
            objectOutputStream.flush();
        } catch (IOException e) {
            closeClient();
        }
    }

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
    public void sendMessage(User user) {
        try {
            objectOutputStream.writeObject(user);
            objectOutputStream.flush();
        } catch (IOException e) {
            closeClient();
        }
    }


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