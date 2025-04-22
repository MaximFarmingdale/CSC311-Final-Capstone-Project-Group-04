package com.example.csc311finalcapstoneprojectgroup04;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainMenuScreenController {

    @FXML
    private Button exitButton;

    @FXML
    private Button optionsButton;

    @FXML
    private Button playButton;

    @FXML
    private Pane playMenu;

    @FXML
    private Button playMenu_Exit;

    @FXML
    private Button playMenu_HostButton;

    @FXML
    private Button playMenu_JoinButton;

    @FXML
    private Text playMenu_selectModeText;

    @FXML
    private Text typeracerLogo;

    @FXML
    private Pane volumePane;

    @FXML
    private Slider volumePane_Slider;

    @FXML
    private Text volumePane_Text;

    @FXML
    void exitProgram(MouseEvent event) {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void openPlayMenu(MouseEvent event) {

    }

    @FXML
    void optionPullUp(MouseEvent event) {

    }

    @FXML
    void playMenu_Host(MouseEvent event) {

    }

    @FXML
    void playMenu_Join(MouseEvent event) {

    }

    @FXML
    void playMenu_exitMenu(MouseEvent event) {

    }

}
