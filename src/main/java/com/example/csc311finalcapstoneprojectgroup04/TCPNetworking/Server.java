package com.example.csc311finalcapstoneprojectgroup04.TCPNetworking;

import com.example.csc311finalcapstoneprojectgroup04.Eureka.ClientEureka;
import com.example.csc311finalcapstoneprojectgroup04.Lobby.Lobby;
import com.example.csc311finalcapstoneprojectgroup04.NetworkMessagesandUpdate.Message;
import com.example.csc311finalcapstoneprojectgroup04.NetworkMessagesandUpdate.Ping;
import com.example.csc311finalcapstoneprojectgroup04.NetworkMessagesandUpdate.RaceUpdate;
import com.example.csc311finalcapstoneprojectgroup04.User;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import static com.example.csc311finalcapstoneprojectgroup04.TCPNetworking.ClientHandler.clients;

/**
 * This is the Server class that manages a Server socket to allow it to accept multiple connections,
 * respond to pings, get messages that are sent to it and to send messages back. The class is used
 * by the host of a lobby and makes use of a lobby object to send a ping response back to a pinging
 * user.
 * <p>
 * It can also send an instance of Ping, Message and Lobby to the sockets that are connected
 * to it. It uses ClientHandler to manage the lobbies, which by having each client as a different
 * thread, allows the manging of multiple clients.
 */
public class Server implements Runnable {
    private ServerSocket serverSocket;
    private String username;
    private Lobby lobby;
    private ClientEureka clientEureka;
    private ObservableList<RaceUpdate> raceUpdates;
    /**
     * Allows you to construct a Server object using the following parameters.
     * @param serverSocket uses the serversocket to make a new socket that connects to it and creates an objectInputStream and objectOutputStream from it
     * @param username username from the user object of the host
     * @throws IOException Incase there is an error with the objectInputStream and objectOutputStream
     * the objectInputStream and objectOutputStream are to read the input from users and to output objects to users
     * it is used throughout the class for these functions
     */
    public Server(ServerSocket serverSocket, String username, Lobby lobby, ClientEureka clientEureka, ObservableList<RaceUpdate> raceUpdates) throws IOException {
        this.serverSocket = serverSocket;
        this.username = username;
        this.lobby = lobby;
        this.clientEureka = clientEureka;
        clientEureka.registerLobby(username, lobby.getLobbyIP());
        this.raceUpdates = raceUpdates;
    }

    /**
     * This method accepts and rejects socket connections and also responds to pings.
     * Accepted connections become new ClientHandler objects and get added to the ClientHandler lst: clients.
     * Always put this method in an unused thread as it is in an endless loop
     */
    public void run() {
        try {
            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                ObjectOutputStream OOS = new ObjectOutputStream(socket.getOutputStream());
                OOS.flush();//to flush metadata
                ObjectInputStream OIS = new ObjectInputStream(socket.getInputStream());
                System.out.println("Accepted connection from " + socket.getRemoteSocketAddress());
                    try {
                        Object object = OIS.readObject();
                        if(object instanceof Message) {
                            Message message = (Message) object;
                            ClientHandler clientHandler = new ClientHandler(socket, OOS, OIS, message.getSender(), this, lobby, clientEureka, raceUpdates);
                            Thread thread = new Thread(clientHandler);
                            thread.start();
                            lobby.increaseNumPlayers();
                            clientEureka.updateLobby(username,lobby.getCurrentPlayers(), lobby.getActiveRace());
                        }
                        //if the application is just pinging the server to see if it works
                        if(object instanceof Ping) {
                            pingResponse(socket, OOS);
                            socket.close();
                        }

                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }

            }
        } catch (IOException e) {
            stopServer();
        }
    }

    /**
     * Stops the server and frees up server and client resources if we run into any IOException or any other exception.
     */
    public void stopServer() {
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
            for (ClientHandler client : clients) {
                client.closeClient(); // assume ClientHandler has this method
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }
    /**
     * internal helper method to respond to pings
     * @param socket
     * sends a response ping of GameFull if the game is full and returns GameInProgress if the game is in progress
     */
    private void pingResponse(Socket socket, ObjectOutputStream OOS) throws IOException {
        try {
            if (lobby.fullRace())
                OOS.writeObject(Ping.GameFull);
            else if(lobby.getActiveRace())
                OOS.writeObject(Ping.GameInProgress);
            else
                OOS.writeObject(lobby);
        }
        catch (IOException e) {
            stopServer();
        }
    }
}
