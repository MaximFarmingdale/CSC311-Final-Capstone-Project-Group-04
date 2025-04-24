package com.example.csc311finalcapstoneprojectgroup04;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class MainMenuScreenController {

    @FXML
    private Button exitButton;

    @FXML
    private Button optionsButton;

    @FXML
    private Button playButton;

    @FXML
    private Pane playMenu_Pane;

    @FXML
    private Button playMenu_Exit;

    @FXML
    private Button playMenu_HostButton;

    @FXML
    private Button playMenu_JoinButton;

    @FXML
    private Button playMenu_Solo;

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

    int playmenuclickcount = 1;
    @FXML
    void openPlayMenu(MouseEvent event) {
        playButton.setOnAction(e -> {
            System.out.println(playmenuclickcount);
            playmenuclickcount++;
            playButton.getOnMouseClicked();
        });

        if (playmenuclickcount % 1 == 0) {
            playMenu_Pane.setOpacity(1);
            playMenu_Pane.setDisable(false);
        }
        if (playmenuclickcount % 2 == 0) {
            playMenu_Pane.setOpacity(0);
            playMenu_Pane.setDisable(true);
        }
    }


    int optionclicked = 1;
    @FXML
    void optionPullUp(MouseEvent event) {

        optionsButton.setOnAction(e -> {
            optionclicked++;
            optionsButton.getOnMouseClicked();
        });

        if (optionclicked % 1 == 0) {
            volumePane.setOpacity(1);
            volumePane.setDisable(false);
        }
        if (optionclicked % 2 == 0) {
            volumePane.setOpacity(0);
            volumePane.setDisable(true);
        }
    }

    @FXML
    void playMenu_Host(MouseEvent event) {

    }

    @FXML
    void playMenu_Join(MouseEvent event) {

    }

    @FXML
    void playMenu_exitMenu(MouseEvent event) {
        playmenuclickcount++;
        playMenu_Pane.setOpacity(0);
        playMenu_Pane.setDisable(true);
    }


    @FXML
    void playMenu_SoloStart(MouseEvent event) throws IOException {
        Stage stage = (Stage) playMenu_Exit.getScene().getWindow();
        try {
            FXMLLoader solo = new FXMLLoader(getClass().getResource("Solo.fxml"));
            Scene scene = new Scene(solo.load(), 1280, 720);
            stage.setTitle("Solo");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
