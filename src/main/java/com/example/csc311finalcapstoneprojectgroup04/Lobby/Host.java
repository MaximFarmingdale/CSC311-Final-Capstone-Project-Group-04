package com.example.csc311finalcapstoneprojectgroup04.Lobby;

import com.example.csc311finalcapstoneprojectgroup04.TCPNetworking.Server;
import com.example.csc311finalcapstoneprojectgroup04.User;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;
/// host class for users that are hosting their own lobbies
public class Host extends User {
    String hostIP = InetAddress.getLocalHost().getHostAddress(); // note this might not work in a live application
    Server server;
    public Host(String username, String password, ServerSocket serverSocket) throws UnknownHostException {
        super(username, password);
        try {
            server = new Server(serverSocket, username);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void restartLobby(){
        //fill in
    }
    public void getPrompt(){
        //fill in
    }
    public void endLobby(){
        //fill in
    }
}
