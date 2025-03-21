package com.example.csc311finalcapstoneprojectgroup04;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.TextFlow;

import javax.imageio.IIOException;

/// test controller to test networking
/// ## Not to be used for anything other than experimenting
/// So far very basic implementation of the network sending over user input in
/// a text field to other clients.
public class ClientTestController implements Initializable {


    @FXML
    private TextField inputField;

    @FXML
    private Label pastLabel;

    private String username = "";

    private Client client;

    private Socket socket;

    private String text =
""" 
Mary had a little lamb,
Little lamb, little lamb.
Mary had a little lamb,
Its fleece was white as snow.""";
    private String[] list;

    private int count;

    @FXML
    void testClient(KeyEvent event) throws IOException {
        if (event.getCode() == KeyCode.SPACE) {
            if (count < list.length && Objects.equals(inputField.getText(), list[count])) {
                client.sendMessage(inputField.getText());
                count++;
                inputField.clear();
            }
        }
        if (event.getCode() == KeyCode.ENTER) {
            if (username.isEmpty()) {
                username = inputField.getText();
                pastLabel.setText(text);
                socket = new Socket("localhost", 1234);
                client = new Client(socket, username);
                client.getMessage();
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        list = text.split(" ");
    }
}
