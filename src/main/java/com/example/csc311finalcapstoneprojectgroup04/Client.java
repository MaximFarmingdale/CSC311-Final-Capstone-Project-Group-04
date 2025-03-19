package com.example.csc311finalcapstoneprojectgroup04;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
/// testing out network and socket programming.
/// ## Work in progress
public class Client {
    private Socket socket;
    private BufferedReader BufferedReader;
    private BufferedWriter BufferedWriter;
    private String username;
    private String password;

    public Client(Socket socket, String username, String password) {
        try {
            this.socket = socket;
            this.BufferedWriter= new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream()));
            this.BufferedReader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            this.username = username;
            this.password = password;
        }
        catch (Exception e) {
            closeClient(socket, BufferedWriter, BufferedReader);
        }
    }
    public void sendMessage(String message) {
        try {
            BufferedWriter.write(username);
            BufferedWriter.newLine();
            BufferedWriter.flush();

            Scanner scanner = new Scanner(System.in);
            while (socket.isConnected()) {
                String line = scanner.nextLine();
                BufferedWriter.write(username + ": " + message);
                BufferedWriter.newLine();
                BufferedWriter.flush();
            }
        } catch (IOException e) {
            closeClient(socket, BufferedWriter, BufferedReader);
        }
    }
    public void sendRaceUpdate(double percentage) {
        try {
            BufferedWriter.write(username);
            BufferedWriter.newLine();
            BufferedWriter.flush();

            Scanner scanner = new Scanner(System.in);
            while (socket.isConnected()) {
                String line = scanner.nextLine();
                BufferedWriter.write(percentage + ": " + username);
                BufferedWriter.newLine();
                BufferedWriter.flush();
            }
        }
        catch (IOException e) {
            closeClient(socket, BufferedWriter, BufferedReader);
        }
    }

    public void closeClient(Socket socket, BufferedWriter bufferedWriter, BufferedReader bufferedReader) {
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
    public boolean valid_login() {
        return true;
    }
    public void getMessage() {
        new Thread(new Runnable() {
            public void run() {
                String sentMessage;
                while(socket.isConnected()) {
                    try {
                        sentMessage = BufferedReader.readLine();
                        if (isRaceupdate())
                            updateRace(sentMessage);
                        System.out.println(sentMessage);
                    } catch (IOException e) {
                        closeClient(socket, BufferedWriter, BufferedReader);
                    }
                }
            }
        }).start();
    }
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your username: ");
        String username = scanner.nextLine();
        if (username.equals("")) {

        }
        System.out.println("Enter your password: ");
        String password = scanner.nextLine();
        Socket socket = new Socket("localhost", 1234);
        Client client = new Client(socket, username, password);
        client.getMessage();
        client.sendMessage("hi");
    }
    public boolean existingUsername(String username) {
        return true;
    }
    public boolean isRaceupdate() {
        return true;
    }
    public void updateRace(String message) {

    }
}
