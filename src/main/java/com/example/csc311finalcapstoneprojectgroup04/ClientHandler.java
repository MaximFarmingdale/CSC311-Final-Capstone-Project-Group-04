package com.example.csc311finalcapstoneprojectgroup04;

import javafx.scene.control.TextField;
import javafx.scene.text.TextFlow;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/// testing out network and socket programming.
/// ## Work in progress
/// uses threads to handle multiple users at the same time
public class ClientHandler implements Runnable {
    private static List<ClientHandler> clients = new CopyOnWriteArrayList<>();    private Socket socket;
    private BufferedReader BufferedReader;
    private BufferedWriter BufferedWriter;
    private PrintWriter PrintWriter;
    private String clienUserName;

    public ClientHandler(Socket socket) {
        try {
            this.socket = socket;
            this.BufferedWriter= new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream()));
            this.BufferedReader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            this.clienUserName = BufferedReader.readLine();
            clients.add(this);
            sendMessage(clienUserName + " has entered the game");

        } catch (Exception e) {
            closeClient(socket, BufferedWriter, BufferedReader);
        }
    }

    @Override
    public void run() {
        String message;
        while (socket.isConnected()) {
            try {
                message = BufferedReader.readLine();
                sendMessage(message);
            }
             catch (IOException e) {
                 closeClient(socket, BufferedWriter, BufferedReader);
                 break;
            }
        }
    }
    public void sendMessage(String message) {
        for (ClientHandler client : clients) {
            try {
                if(!client.clienUserName.equals(clienUserName)) {
                    client.BufferedWriter.write(message);
                    client.BufferedWriter.newLine();
                    client.BufferedWriter.flush();
                }
            }
            catch (IOException e) {
                closeClient(socket, BufferedWriter, BufferedReader);
            }
        }
    }
    public void removeClient() {
        clients.remove(this);
        sendMessage("user: " + clienUserName + " has left!");
    }
    public void closeClient(Socket socket, BufferedWriter bufferedWriter, BufferedReader bufferedReader) {
        removeClient();
        try {
            if(BufferedWriter != null) {
                bufferedWriter.close();
            }
            if(BufferedReader != null) {
                BufferedReader.close();
            }
            if(socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
