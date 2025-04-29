package com.example.csc311finalcapstoneprojectgroup04.JavaFXControllers;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import com.example.csc311finalcapstoneprojectgroup04.TCPNetworking.Client;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.Window;

/// this is a former test class which I will be getting rid of
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

                Window window = ((Node) event.getSource()).getScene().getWindow();
                Stage stage = (Stage) window;
                //changeController(stage);
            }
        }
    }
    /*
    public void changeController(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("WaitingRoomTestController.fxml"));
            Parent newRoot = loader.load();
            WaitingRoomTestController controller = loader.getController();
            controller.enterWaitingRoom(username);
            Scene scene = new Scene(newRoot, 1270, 720);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
*/

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        list = text.split(" ");
    }
}
