package com.example.csc311finalcapstoneprojectgroup04.JavaFXControllers;

import com.example.csc311finalcapstoneprojectgroup04.TypeApplication;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
public class MainMenuScreenController {


    @FXML
    private AnchorPane MainMenuAPane;

    @FXML
    private Button exitButton;

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
    private BorderPane PlayMenuBPane;

    @FXML
    private Button playMenu_SoloButton;

    private User user;

    @Autowired
    private ApplicationContext applicationContext;

    /**
     * Exits the program.
     * @param event
     */
    @FXML
    void exitProgram(MouseEvent event) {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }
    /**
     * opens the play menu
     * @param event
     */
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
//    int optionclicked = 1; ~~~~~~~~~~~~~~~~~~~~~OPTIONS PANE DISCONTINUED~~~~~~~~~~~~~~~~~~~~~
    //    @FXML
//    void optionPullUp(MouseEvent event) {
//        optionsButton.setOnAction(e -> {
//            optionclicked++;
//            optionsButton.getOnMouseClicked();
//        });
//    }
//        @FXML
//    void optionPullUp(ActionEvent event) {
//
//        if (optionclicked % 1 == 0) {
//            volumePane.setOpacity(1);
//            volumePane.setDisable(false);
//        }
//        if (optionclicked % 2 == 0) {
//            volumePane.setOpacity(0);
//            volumePane.setDisable(true);
//        }
//    } ~~~~~~~~~~~~~~~~~~~~~OPTIONS PANE DISCONTINUED~~~~~~~~~~~~~~~~~~~~~

    /**
     * Method to pass a user to MainMenuScreen
     * @param event
     */
    public void enterMainMenu(User event) {
        user = event;
    }

    /**
     * Sets the current scene to the WaitingRoom.
     * @param event
     */
    @FXML
    void goToWaitingRoom(ActionEvent event) {
        try {
            Stage stage = (Stage) PlayMenuBPane.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/JavaFX_FXML/WaitingRoom.fxml"));
            loader.setControllerFactory(applicationContext::getBean); //gets beans from spring
            Parent newRoot = loader.load();
            WaitingRoomController controller = loader.getController();
            loader.setControllerFactory(applicationContext::getBean);
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
            loader.setControllerFactory(applicationContext::getBean); //gets beans from spring
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

//    public void darkMode_Toggle(ActionEvent event) {
//    } ~~~~~~~~~~~~~~~~~~~~~OPTIONS PANE DISCONTINUED~~~~~~~~~~~~~~~~~~~~~

    /**
     * Sets the current scene to the HostScreen.
     * @param event
     */
    public void playMenu_Host(MouseEvent event) {
        try {
            Stage stage =(Stage) PlayMenuBPane.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/JavaFX_FXML/HostScreen.fxml"));
            loader.setControllerFactory(applicationContext::getBean); //gets beans from spring
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

    /**
     * Sets the current scene to the WaitingRoom.
     * @param event
     */
    public void playMenu_Join(MouseEvent event) {
        try {

            Stage stage = (Stage) PlayMenuBPane.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/JavaFX_FXML/WaitingRoom.fxml"));
            loader.setControllerFactory(applicationContext::getBean); //gets beans from spring
            Parent newRoot = loader.load();
            WaitingRoomController controller = loader.getController();
            loader.setControllerFactory(applicationContext::getBean);
            controller.enterWaitingRoom(user);
            Scene scene = new Scene(newRoot, 1270, 720);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void playMenu_exitMenu(MouseEvent event) {
        playmenuclickcount++;
        playMenu_Pane.setOpacity(0);
        playMenu_Pane.setDisable(true);
    }

    @FXML
    void playMenu_SoloMode(MouseEvent event) {
        try {
            Stage stage =(Stage) PlayMenuBPane.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/JavaFX_FXML/SoloMode.fxml"));
            loader.setControllerFactory(applicationContext::getBean); //gets beans from spring
            Parent newRoot = loader.load();
            SoloModeController controller = loader.getController();
            controller.enterSoloMode(user);
            Scene scene = new Scene(newRoot, 1270, 720);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}