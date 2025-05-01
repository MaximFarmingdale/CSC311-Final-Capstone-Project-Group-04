package com.example.csc311finalcapstoneprojectgroup04.JavaFXControllers;

import com.example.csc311finalcapstoneprojectgroup04.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.text.Text;

import java.io.IOException;
// test
public class MainMenuScreenController {


    @FXML
    private AnchorPane MainMenuAPane;

    @FXML
    private CheckBox darkmode_Checkbox;

    @FXML
    private Button exitButton;

    @FXML
    private Button optionsButton;

    @FXML
    private Button playButton;

    @FXML
    private Button playMenu_Exit;

    @FXML
    private Button playMenu_HostButton;

    @FXML
    private Button playMenu_JoinButton;

    @FXML
    private Pane playMenu_Pane;

    @FXML
    private Button playMenu_Solo;

    @FXML
    private Text playMenu_selectModeText;

    @FXML
    private Pane volumePane;


    @FXML
    private BorderPane PlayMenuBPane;


//    @FXML
//    private AnchorPane MainMenuAPane;


    private User user;

    @FXML
    void exitProgram(ActionEvent event) {

    }

    /**
     * opens the play menu
     * @param event
     */
    int playmenuclickcount = 1;
    @FXML
    void openPlayMenu(MouseEvent event) {
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/JavaFX_FXML/PlayMenu.fxml"));
//            Parent playMenuPane = loader.load();
//            MainMenuAPane.getChildren().add(playMenuPane);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


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

        @FXML
    void optionPullUp(ActionEvent event) {

    }

    /**
     * Method to pass a user to mainMenu
     * @param event
     */
    public void enterMainMenu(User event) {
        user = event;
    }

    /**
     *
     * @param event
     */
    @FXML
    void goToWaitingRoom(ActionEvent event) {
        try {

            Stage stage = (Stage) PlayMenuBPane.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/JavaFX_FXML/WaitingRoom.fxml"));
            Parent newRoot = loader.load();
            WaitingRoomController controller = loader.getController();
            controller.enterWaitingRoom(user);
            Scene scene = new Scene(newRoot, 1270, 720);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void goToHostScreen(ActionEvent event) {
        try {
            Stage stage =(Stage) PlayMenuBPane.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/JavaFX_FXML/HostScreen.fxml"));
            Parent newRoot = loader.load();
            HostScreenController controller = loader.getController();
            controller.enterHostScreen(user);
            Scene scene = new Scene(newRoot, 1270, 720);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void darkMode_Toggle(ActionEvent event) {
    }

    public void playMenu_Host(MouseEvent event) {
    }

    public void playMenu_Join(MouseEvent event) {
    }

    public void playMenu_exitMenu(MouseEvent event) {
    }
}