package com.example.csc311finalcapstoneprojectgroup04.Lobby;

import java.io.IOException;
import java.net.Socket;

public final class Lobby {
    private String LobbyIP;
    private String LobbyName;
    private String LobbyHostName;
    private int numPlayers = 0;
    private final int MaxPlayers = 3;
    private final int port = 1234;
    public Lobby(String LobbyIP, String LobbyName, String LobbyHostName) {
        this.LobbyIP = LobbyIP;
        this.LobbyName = LobbyName;
        this.LobbyHostName = LobbyHostName;
    }
    public boolean stillRunning() {
        try {
            new Socket(LobbyIP, port);
            //have it send over a ping to see the status of the lobby
            return true;
        } catch (IOException e) {
            return false;
        }
    }




}
