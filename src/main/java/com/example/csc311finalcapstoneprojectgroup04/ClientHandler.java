package com.example.csc311finalcapstoneprojectgroup04;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;

/// testing out network and socket programming.
/// ## Work in progress
/// uses threads to handle multiple users at the same time
public class ClientHandler implements Runnable {
    public static List<ClientHandler> clients = new CopyOnWriteArrayList<>();
    private Socket socket;
    private BufferedReader chatBufferedReader;
    private BufferedWriter chatBufferedWriter;
    private BufferedReader raceBufferedReader;
    private BufferedWriter raceBufferedWriter;
    private PrintWriter PrintWriter;
    public String clienUserName;

    public ClientHandler(Socket socket) {
        try {
            this.socket = socket;
            this.chatBufferedWriter = new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream()));
            this.chatBufferedReader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            this.raceBufferedWriter = new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream()));
            this.clienUserName = chatBufferedReader.readLine();
            clients.add(this);
            sendMessage(clienUserName + " has entered the game");

        } catch (Exception e) {
            closeClient(socket, chatBufferedWriter, chatBufferedReader);
        }
    }
    @Override
    public void run() {
        String message;
        while (socket.isConnected()) {
            try {
                message = chatBufferedReader.readLine();
                sendMessage(message);
            }
             catch (IOException e) {
                 closeClient(socket, chatBufferedWriter, chatBufferedReader);
                 break;
            }
        }
    }
    public void sendMessage(String message) {
        for (ClientHandler client : clients) {
            try {
                if(!client.clienUserName.equals(clienUserName)) {
                    client.chatBufferedWriter.write(message);
                    client.chatBufferedWriter.newLine();
                    client.chatBufferedWriter.flush();
                }
            }
            catch (IOException e) {
                closeClient(socket, chatBufferedWriter, chatBufferedReader);
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
            if(chatBufferedWriter != null) {
                bufferedWriter.close();
            }
            if(chatBufferedReader != null) {
                chatBufferedReader.close();
            }
            if(socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws IOException {
        ClientHandler clientHandler = new ClientHandler(new Socket("localhost", 1234
        ));
    }
}
