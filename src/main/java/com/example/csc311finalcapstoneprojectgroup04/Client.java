package com.example.csc311finalcapstoneprojectgroup04;

import java.io.*;
import java.io.*;
import java.net.Socket;

public class Client {
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String username;

    public Client(Socket socket, String username) {
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.username = username;
            bufferedWriter.write(username);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            closeClient();
        }
    }

    public void sendMessage(String message) {
        try {
            if (socket.isConnected()) {
                bufferedWriter.write(username + ": " + message);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
        } catch (IOException e) {
            closeClient();
        }
    }

    public void sendRaceUpdate(double percentage) {
        try {
            if (socket.isConnected()) {
                bufferedWriter.write("UPDATE " + username + " " + percentage);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
        } catch (IOException e) {
            closeClient();
        }
    }

    public void getMessage() {
        new Thread(() -> {
            try {
                while (true) {
                    String receivedMessage = bufferedReader.readLine();
                    if (receivedMessage == null)
                        break;
                    System.out.println(receivedMessage);
                }
            } catch (IOException e) {
                closeClient();
            }
        }).start();
    }

    public void closeClient() {
        try {
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}