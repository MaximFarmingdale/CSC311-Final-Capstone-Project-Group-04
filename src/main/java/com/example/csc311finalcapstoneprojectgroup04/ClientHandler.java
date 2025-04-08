package com.example.csc311finalcapstoneprojectgroup04;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/// uses threads to handle multiple users at the same time
public class ClientHandler implements Runnable {
    public static List<ClientHandler> clients = new CopyOnWriteArrayList<>();
    private Socket socket;
    public ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    public String clientUserName;

    public ClientHandler(Socket socket, String clientUserName) {
        try {
            this.socket = socket;
            this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            this.objectInputStream = new ObjectInputStream(socket.getInputStream());
            this.clientUserName = clientUserName;
            clients.add(this);
            sendMessage(new Message(clientUserName, clientUserName + " has entered the game"));
        } catch (Exception e) {
            closeClient();
        }
    }
    @Override
    public void run() {
        Message message;
        while (socket.isConnected()) {
            try {
                message = new Message(clientUserName, objectInputStream.readLine()); //change this
                sendMessage(message);
            }
             catch (IOException e) {
                 closeClient();
                 break;
            }
        }
    }
    public void sendMessage(Message message) {
        for (ClientHandler client : clients) {
            try {
                if(!client.clientUserName.equals(clientUserName)) {
                    client.objectOutputStream.writeObject(message);
                    client.objectOutputStream.flush();
                }
            }
            catch (IOException e) {
                closeClient();
            }

        }
    }
    public void sendMessage(User user) {
        for (ClientHandler client : clients) {
            try {
                if(!client.clientUserName.equals(clientUserName)) {
                    client.objectOutputStream.writeObject(user);
                    client.objectOutputStream.flush();
                }
            }
            catch (IOException e) {
                closeClient();
            }

        }
    }
    public void removeClient() {
        clients.remove(this);
        sendMessage(new Message(clientUserName, "user: " + clientUserName + " has left!"));
    }
    public void closeClient() {
        removeClient();
        try {
            if(objectOutputStream != null) {
                objectOutputStream.close();
            }
            if(objectInputStream != null) {
                objectInputStream.close();
            }
            if(socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace(); //add better logging
        }
    }
    public static void main(String[] args) throws IOException {
        //ClientHandler clientHandler = new ClientHandler(new Socket("localhost", 1234));
    }
}
