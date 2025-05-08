package com.example.csc311finalcapstoneprojectgroup04.JavaFXControllers;

import com.example.csc311finalcapstoneprojectgroup04.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class SoloModeController {

    @FXML
    private TextField enterWordField;

    @FXML
    private Button startButton;

    @FXML
    private ListView<?> wordList;

    @FXML
    private Text wordsEnteredCounter;

    @FXML
    private Circle youNode;

    @FXML
    private Text youNodetext;

    private User user;

    public void enterSoloMode(User currentUser) {
        user = currentUser;
    }

}
