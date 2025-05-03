package com.example.csc311finalcapstoneprojectgroup04.TCPNetworking;

import com.example.csc311finalcapstoneprojectgroup04.NetworkMessagesandUpdate.Message;
import com.example.csc311finalcapstoneprojectgroup04.NetworkMessagesandUpdate.RaceUpdate;
import com.example.csc311finalcapstoneprojectgroup04.User;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Used by the server for handling client's messages and race updates that are intended for other clients.
 * Only intended as a helper class for server.
 */
public class ClientHandler implements Runnable {
    public static List<ClientHandler> clients = new CopyOnWriteArrayList<>(); //change?
    private Socket socket;
    public ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    public String clientUserName;
    private Server server;

    /**
     * Allows you to construct a new ClientHandler for managing multiple connections to a server.
     * @param socket The socket that the server accepts. The constructor uses the socket to make an
     * ObjectOutputStream and ObjectInputStream that is used throughout the class.
     * @param clientUserName The client username which the server gets from the join message.
     *
     */
    public ClientHandler(Socket socket, ObjectOutputStream objectOutputStream, ObjectInputStream objectInputStream, String clientUserName, Server server) {
        try {
            this.socket = socket;
            this.objectOutputStream = objectOutputStream;
            this.objectInputStream = objectInputStream;
            this.clientUserName = clientUserName;
            this.server = server;
            clients.add(this);
            sendMessage(new Message(clientUserName, clientUserName + " has entered the game"));
        } catch (Exception e) {
            removeClient();
        }
    }

    /**
     * Override of the runnable interface which runs a while loop that looks for new race updates or new messages
     * in the objectInputStream and sends it to the rest of the clients.
     */
    @Override
    public void run() {
        while (socket.isConnected()) {

            try {
                Object input = objectInputStream.readObject();
                if(input.getClass() == Message.class) {
                    this.sendMessage((Message) input);
                }
                else if(input.getClass() == RaceUpdate.class) {
                    this.sendMessage((RaceUpdate) input);
                }
            }
             catch (IOException e) {
                 closeClient();
                 break;
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Sends a client message to every other client and the host.
     * @param message the client message object.
     */
    public void sendMessage(Message message) {
        for (ClientHandler client : clients) {
            try {
                if(!client.clientUserName.equals(clientUserName)) {
                    client.objectOutputStream.writeObject(message);
                    client.objectOutputStream.flush();
                }
            }
            catch (IOException e) {
                removeClient();
            }

        }
    }

    /**
     * Sends a client user object to every other client.
     * @deprecated
     * @param user the client user object.
     */
    public void sendMessage(User user) {
        for (ClientHandler client : clients) {
            try {
                if(!client.clientUserName.equals(clientUserName)) {
                    client.objectOutputStream.writeObject(user);
                    client.objectOutputStream.flush();
                }
            }
            catch (IOException e) {
                removeClient();
            }
        }
    }
    public void sendMessage(RaceUpdate raceUpdate) {
        for (ClientHandler client : clients) {
            try {
                if(!client.clientUserName.equals(clientUserName)) {
                    client.objectOutputStream.writeObject(raceUpdate);
                    client.objectOutputStream.flush();
                }
            } catch (IOException e) {
                removeClient();
            }
        }
    }

    /**
     * Removes the ClientHandler from the clients list and releases the resources of the ClientHandler.
     */
    public void removeClient() {
        clients.remove(this);
        sendMessage(new Message(clientUserName, "user: " + clientUserName + " has left!"));
        closeClient();
    }

    /**
     * Releases the resources of the ClientHandler.
     */
    public void closeClient() {
        removeClient();
        try {
            if(objectOutputStream != null) {
                objectOutputStream.close();
            }
            if(objectInputStream != null) {
                objectInputStream.close();
            }
            if(socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace(); //add better logging
        }
    }
}
