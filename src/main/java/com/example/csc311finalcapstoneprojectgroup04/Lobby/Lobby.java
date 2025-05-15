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
    private TextSource currentSource;
    private int currentPlayers = 0;
    public Lobby(String LobbyIP, String LobbyHostName){
        this.LobbyIP = LobbyIP;
        this.LobbyHostName = LobbyHostName;
        currentSource = TextSource.league_of_legends;
    }
    public void setText(String text) {
        this.text = text;
    }
    public enum TextSource { //different faker sources
        league_of_legends,
        twin_peaks,
        lord_of_the_rings,
        game_of_thrones,
        back_to_the_future
    }
    public void generateNewText(){
        Faker faker = new Faker();
        switch (currentSource) {
            case league_of_legends -> text = faker.leagueOfLegends().quote();
            case twin_peaks -> text = faker.twinPeaks().quote();
            case game_of_thrones -> text = faker.gameOfThrones().quote();
            case back_to_the_future -> text = faker.backToTheFuture().quote();
        }

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

    public void setCurrentSource(TextSource currentSource) {
        this.currentSource = currentSource;
    }
}
