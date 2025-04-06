package com.example.csc311finalcapstoneprojectgroup04;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import static com.example.csc311finalcapstoneprojectgroup04.ClientHandler.clients;

/// Server class which takes in input from the user and uses a client handler to send those inputs to every user
public class Server  {
    private ServerSocket serverSocket;
    private String username;
    private Socket socketServer;
    private ObjectOutputStream objectOutputStream;


    public Server(ServerSocket serverSocket, String username) throws IOException {
        this.serverSocket = serverSocket;
        this.username = username;
        this.socketServer = new Socket("localhost", 1234);
        objectOutputStream = new ObjectOutputStream(socketServer.getOutputStream());
    }
    public void startServer() {
        try {
            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                System.out.println("Accepted connection from " + socket.getRemoteSocketAddress());
                ClientHandler clientHandler = new ClientHandler(socket);
                Thread thread = new Thread(clientHandler);
                thread.start();
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
    public void closeServer() {
        try {
            if (objectOutputStream != null) {
                objectOutputStream.close();
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
