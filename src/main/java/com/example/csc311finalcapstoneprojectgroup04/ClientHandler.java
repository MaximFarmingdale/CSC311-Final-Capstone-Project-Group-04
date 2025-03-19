package com.example.csc311finalcapstoneprojectgroup04;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
/// testing out network and socket programming.
/// ## Work in progress
/// uses threads to handle multiple users at the same time
public class ClientHandler implements Runnable {
    private static ArrayList<ClientHandler> clients = new ArrayList<>();
    private Socket socket;
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
            sendMessage(clienUserName + "has entered the game");

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
                    client.BufferedWriter.newLine(); //not needed for this game
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
        sendMessage("User: " + clienUserName + " has left!");
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
