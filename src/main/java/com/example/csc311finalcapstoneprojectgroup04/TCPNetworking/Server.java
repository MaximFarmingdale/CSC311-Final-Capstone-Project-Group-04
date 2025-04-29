package com.example.csc311finalcapstoneprojectgroup04.TCPNetworking;

import com.example.csc311finalcapstoneprojectgroup04.Lobby.Lobby;
import com.example.csc311finalcapstoneprojectgroup04.NetworkMessagesandUpdate.Message;
import com.example.csc311finalcapstoneprojectgroup04.NetworkMessagesandUpdate.Ping;
import com.example.csc311finalcapstoneprojectgroup04.NetworkMessagesandUpdate.RaceUpdate;
import com.example.csc311finalcapstoneprojectgroup04.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

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
public class Server  {
    private ServerSocket serverSocket;
    private String username;
    private Socket socketServer;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private Lobby lobby;
    private ObjectOutputStream pingStream;


    /**
     * Allows you to construct a Server object using the following parameters.
     * @param serverSocket uses the serversocket to make a new socket that connects to it and creates an objectInputStream and objectOutputStream from it
     * @param username username from the user object of the host
     * @param lobby lobby object that the host is also using
     * @throws IOException Incase there is an error with the objectInputStream and objectOutputStream
     * the objectInputStream and objectOutputStream are to read the input from users and to output objects to users
     * it is used throughout the class for these functions
     */
    public Server(ServerSocket serverSocket, String username, Lobby lobby) throws IOException {
        this.serverSocket = serverSocket;
        this.username = username;
        this.socketServer = new Socket("localhost", 1234);
        this.objectInputStream = new ObjectInputStream(socketServer.getInputStream());
        objectOutputStream = new ObjectOutputStream(socketServer.getOutputStream());
        this.lobby = lobby;
    }

    /**
     * This method accepts and rejects socket connections and also responds to pings.
     * Accepted connections become new ClientHandler objects and get added to the ClientHandler lst: clients.
     * Always put this method in an unused thread as it is in an endless loop
     */
    public void startServer() {
        try {
            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                System.out.println("Accepted connection from " + socket.getRemoteSocketAddress());
                while (true) {
                    try {
                        Object object = objectInputStream.readObject();
                        if(object instanceof Message) {
                            Message message = (Message) object;
                            ClientHandler clientHandler = new ClientHandler(socket, message.sender);
                            Thread thread = new Thread(clientHandler);
                            thread.start();
                        }
                        //if the application is just pinging the server to see if it works
                        if(object instanceof Ping) {
                            pingResponse(socket);
                            socket.close();
                        }

                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
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
            if(pingStream != null) {
                pingStream.close();
            }
            if(socketServer != null) {
                closeSocket();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void sendMessage(String text) {
        try {
            for (ClientHandler clientHandler : clients) {
                clientHandler.objectOutputStream.writeObject(text);
            }
        }
        catch (IOException e) {
            closeSocket();
        }

    }

    /**
     * Allows the host to send a message to all clients
     * @param message
     */
    public void sendMessage(Message message) {
        try {
            for (ClientHandler clientHandler : clients) {
                clientHandler.objectOutputStream.writeObject(message);
            }
        }
        catch (IOException e) {
            closeSocket();
        }

    }

    /**
     * Allows the host to send a user to all clients
     * @param user
     */
    public void sendMessage(User user) {
        try {
            for (ClientHandler clientHandler : clients) {
                clientHandler.objectOutputStream.writeObject(user);
            }
        }
        catch (IOException e) {
            closeSocket();
        }

    }
    public void startRace() {
        try {
            lobby.generateNewText();
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
            throw new RuntimeException(e);
        }


    }

    /**
     * Allows the host to send a ping to all clients
     * @param ping
     */
    public void sendMessage(Ping ping) {
        try {
            for (ClientHandler clientHandler : clients) {
                clientHandler.objectOutputStream.writeObject(ping);
            }
        }
        catch (IOException e) {
            closeSocket();
        }

    }

    /**
     * Allows the host to send a RaceUpdate to all clients
     * @param raceUpdate
     */
    public void sendMessage(RaceUpdate raceUpdate) {
        try {
            for (ClientHandler clientHandler : clients) {
                clientHandler.objectOutputStream.writeObject(raceUpdate);
            }
        }
        catch (IOException e) {
            closeSocket();
        }

    }

    /**
     * internal helper method to respond to pings
     * @param socket
     * sends a response ping of GameFull if the game is full and returns GameInProgress if the game is in progress
     */
    private void pingResponse(Socket socket) {
        try {
            pingStream = new ObjectOutputStream(socket.getOutputStream());
            if (lobby.fullRace())
                pingStream.writeObject(Ping.GameFull);
            else if(lobby.getActiveRace())
                pingStream.writeObject(Ping.GameInProgress);
        }
        catch (IOException e) {
            closeSocket();
        }
    }
    /**
     * Closes the socket that the server class uses for ObjectOutputStream and ObjectInputStream and frees up resources
     */
    public void closeSocket() {
        try {
            if (objectOutputStream != null) {
                objectOutputStream.close();
            }
            if(objectInputStream != null) {
                objectInputStream.close();
            }
            if(socketServer != null) {
                socketServer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
