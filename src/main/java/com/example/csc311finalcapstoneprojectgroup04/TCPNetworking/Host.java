package com.example.csc311finalcapstoneprojectgroup04.TCPNetworking;

import com.example.csc311finalcapstoneprojectgroup04.Lobby.Lobby;
import com.example.csc311finalcapstoneprojectgroup04.NetworkMessagesandUpdate.RaceUpdate;
import javafx.collections.ObservableList;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Host extends Client{
    private Lobby lobby;

    /**
     * Constructs a client which uses a socket to make ObjectOutputStream and
     * ObjectOutputStream.
     *
     * @param socket             the provided socket.
     * @param objectOutputStream
     * @param objectInputStream
     * @param username           the username from username.
     * @param messageBox
     * @param raceUpdates
     */
    public Host(Socket socket, ObjectOutputStream objectOutputStream, ObjectInputStream objectInputStream, Lobby lobby, String username, VBox messageBox, ObservableList<RaceUpdate> raceUpdates) {
        super(socket, objectOutputStream, objectInputStream, username, lobby, messageBox, raceUpdates);
        this.lobby = lobby;
    }
    public void startRace() {
        new Thread(() -> {
            try {
                Lobby temp = new Lobby(lobby.getLobbyIP(), lobby.getLobbyHostName());
                temp.setCurrentPlayers(lobby.getCurrentPlayers());
                temp.setText(lobby.getText());
                sendMessage("Race begins in 3");
                Thread.sleep(1000);
                sendMessage("Race begins in 2");
                Thread.sleep(1000);
                sendMessage("Race begins in 1");
                Thread.sleep(1000);
                sendMessage("Start!");
                lobby.setActiveRace(true);
                sendMessage(lobby);



            } catch (InterruptedException e) {
                closeClient();
            }
        }).start();

    }
    public void sendMessage(Lobby lobby) {
        if(super.socket.isConnected()) {
            try {
                objectOutputStream.writeObject(lobby);
                objectOutputStream.flush();
            } catch (IOException e) {
                closeClient();
            }
        }
    }
}
