package com.example.csc311finalcapstoneprojectgroup04;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static com.example.csc311finalcapstoneprojectgroup04.ClientHandler.clients;

///  this is just some experimentation with dealing with a server
public class Server {
    private ServerSocket serverSocket;
    private String username;
    private ClientHandler serverHandler;
    private Socket socketServer;

    public Server(ServerSocket serverSocket, String username) throws IOException {
        this.serverSocket = serverSocket;
        this.username = username;
        this.socketServer = new Socket("localhost", 1234);
        this.serverHandler = new ClientHandler(socketServer);
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
            throw new RuntimeException(e);
        }
    }

    public void stopServer() {
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void sendMessage(String message) {
        serverHandler.sendMessage(message);
    }
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(1234);
        Server server = new Server(serverSocket, "user");
        server.startServer();
    }


}
