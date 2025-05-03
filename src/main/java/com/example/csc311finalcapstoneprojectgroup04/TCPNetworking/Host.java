package com.example.csc311finalcapstoneprojectgroup04.TCPNetworking;

import com.example.csc311finalcapstoneprojectgroup04.Lobby.Lobby;
import com.example.csc311finalcapstoneprojectgroup04.NetworkMessagesandUpdate.RaceUpdate;
import javafx.collections.ObservableList;
import javafx.scene.layout.VBox;

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
    public Host(Socket socket, ObjectOutputStream objectOutputStream, ObjectInputStream objectInputStream, String username, VBox messageBox, ObservableList<RaceUpdate> raceUpdates, Lobby lobby) {
        super(socket, objectOutputStream, objectInputStream, username, messageBox, raceUpdates);
        this.lobby = lobby;
    }
    public void startRace() {
        try {
            String text = lobby.getText();
            sendMessage("Race begins in 3");
            Thread.sleep(1000);
            sendMessage("Race begins in 2");
            Thread.sleep(1000);
            sendMessage("Race begins in 1");
            Thread.sleep(1000);
            sendMessage("Start!");
            //send lobby


        } catch (InterruptedException e) {
            closeClient();
        }
    }
}
