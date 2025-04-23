package com.example.csc311finalcapstoneprojectgroup04.Lobby;

import java.io.IOException;
import java.net.Socket;

public final class Lobby {
    private String LobbyIP;
    private String LobbyHostName;
    private int numPlayers = 0;
    private final int MaxPlayers = 3;
    private final int port = 1234;
    private int currentPlayers = 0;
    public Lobby(String LobbyIP, String LobbyHostName, int currentPlayers) {
        this.LobbyIP = LobbyIP;
        this.LobbyHostName = LobbyHostName;
        this.numPlayers = currentPlayers;
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
    public int getCurrentPlayers() {
        return currentPlayers;
    }

    public void setCurrentPlayers(int currentPlayers) {
        this.currentPlayers = currentPlayers;
    }
}
