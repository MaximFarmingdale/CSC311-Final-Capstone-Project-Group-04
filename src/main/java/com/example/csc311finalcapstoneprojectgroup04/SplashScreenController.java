package com.example.csc311finalcapstoneprojectgroup04;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
/// controller for the first screen the user sees, before they log in or start a match etc.
public class SplashScreenController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}