package com.example.csc311finalcapstoneprojectgroup04.TCPNetworking;

import com.example.csc311finalcapstoneprojectgroup04.NetworkMessagesandUpdate.Message;
import com.example.csc311finalcapstoneprojectgroup04.NetworkMessagesandUpdate.Ping;
import com.example.csc311finalcapstoneprojectgroup04.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import static com.example.csc311finalcapstoneprojectgroup04.TCPNetworking.ClientHandler.clients;

/// Server class which takes in input from the user and uses a client handler to send those inputs to every user
/// Make sure to add a way to send over the amount of users later when a server pings the lobby
public class Server  {
    private ServerSocket serverSocket;
    private String username;
    private Socket socketServer;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;


    public Server(ServerSocket serverSocket, String username) throws IOException {
        this.serverSocket = serverSocket;
        this.username = username;
        this.socketServer = new Socket("localhost", 1234);
        this.objectInputStream = new ObjectInputStream(socketServer.getInputStream());
        objectOutputStream = new ObjectOutputStream(socketServer.getOutputStream());
    }
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
                            Ping ping = (Ping) object;
                            //add ping response
                        }
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        } catch (IOException e) {
            closeServer();
        }
    }

    public void stopServer() {
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException e) {
            closeServer();
        }
    }
    public void sendMessage(Message message) {
        try {
            for (ClientHandler clientHandler : clients) {
                clientHandler.objectOutputStream.writeObject(message);
            }
        }
        catch (IOException e) {
            closeServer();
        }

    }
    public void sendMessage(User user) {
        try {
            for (ClientHandler clientHandler : clients) {
                clientHandler.objectOutputStream.writeObject(user);
            }
        }
        catch (IOException e) {
            closeServer();
        }

    }
    public void sendMessage(Ping ping) {
        try {
            for (ClientHandler clientHandler : clients) {
                clientHandler.objectOutputStream.writeObject(ping);
            }
        }
        catch (IOException e) {
            closeServer();
        }

    }
    public void pingResponse(Ping ping) {
        //fill in
    }
    public void closeServer() {
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
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(1234);
        Server server = new Server(serverSocket, "user");
        server.startServer();
    }


}
