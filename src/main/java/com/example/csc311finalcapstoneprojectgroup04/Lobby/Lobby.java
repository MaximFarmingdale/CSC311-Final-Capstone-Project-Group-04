package com.example.csc311finalcapstoneprojectgroup04.Lobby;

import com.github.javafaker.Faker;

import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;

public final class Lobby implements Serializable {
    private String LobbyIP;
    private String LobbyHostName;
    private int numPlayers = 0;
    private boolean activeRace = false;
    private String text;
    private final int maxPlayers = 3;
    private final int port = 1234;
    private int currentPlayers = 0;
    public Lobby(String LobbyIP, String LobbyHostName){
        this.LobbyIP = LobbyIP;
        this.LobbyHostName = LobbyHostName;
    }
    public void generateNewText(){
        Faker faker = new Faker();
        text = faker.twinPeaks().quote();
    }
    public int getCurrentPlayers() {
        return currentPlayers;
    }
    public void setCurrentPlayers(int currentPlayers) {
        this.currentPlayers = currentPlayers;
    }
    public int getNumPlayers() {return numPlayers;}

    /**
     *
     * @return
     */

    public boolean increaseNumPlayers(){
        if(numPlayers == maxPlayers) {
            numPlayers++;
            return true;
        }
        return false;
    }
    public boolean decreaseNumPlayers(){
        if(numPlayers == 0) {
            numPlayers--;
            return true;
        }
        return false;
    }
    public String getLobbyIP() {return LobbyIP;}
    public String getLobbyHostName() {return LobbyHostName;}
    public String getText() {return text;}
    public void setActiveRace(boolean activeRace) {this.activeRace = activeRace;}
    public boolean getActiveRace() {return activeRace;}
    public boolean fullRace() {return maxPlayers == numPlayers;}

}
