package com.example.csc311finalcapstoneprojectgroup04;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
/// test controller for the networking with lobbies
public class WaitingRoomTestController implements Initializable {

    @FXML
    private VBox vbox;

    private Socket socket;
    private Client client;
    private String username;
    private String usernameHost = "max";
    private String ipAddress = "localhost";
    private Server server;
    public void enterWaitingRoom(String inputUsername) throws IOException {
        socket = new Socket(ipAddress, 1234);
        username = inputUsername;
        client = new Client(socket, inputUsername);
        client.getMessage();
        Button button = new Button(username);
        vbox.getChildren().add(button);
    }
    public void createLobby(String inputUsername) throws IOException {
        usernameHost = inputUsername;
        ipAddress = InetAddress.getLocalHost().getHostAddress();
        ServerSocket serverSocket = new ServerSocket(1234);
        Server server = new Server(serverSocket, username);
        Button button = new Button(username);
        vbox.getChildren().add(button);
        server.startServer();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
