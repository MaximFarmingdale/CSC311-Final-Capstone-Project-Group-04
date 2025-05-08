package com.example.csc311finalcapstoneprojectgroup04;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
/// why does this exist use the mainMenuScreenController in the JavaFXController Class
/// Also if you are going to make a new Controller class add this variable
/// ApplicationContext applicationContext;
/// and this after loading the FXML
/// loader.setControllerFactory(applicationContext::getBean);
/// see the change Controller methods in SplashScreenController
public class UNUSEDMainMenuScreenController {


//    @FXML
//    private CheckBox darkmode_Checkbox;

    @FXML
    private Button exitButton;

//    @FXML
//    private Button optionsButton;

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

//    @FXML
//    private Text typeracerLogo;
//
//    @FXML
//    private Pane volumePane;
//
//    @FXML
//    private Slider volumePane_Slider;
//
//    @FXML
//    private Text volumePane_Text;
    ApplicationContext applicationContext; //KEEP THIS IN ALL CONTROLLERS


    /**
     * exits program from main menu
     * @param event On Click
     */
    @FXML
    void exitProgram(MouseEvent event) {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    int playmenuclickcount = 1;
    @FXML
    void openPlayMenu(MouseEvent event) { // probably an easier way to do this, but i used a counter to track button clicks and translated it to a toggle function
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


//    int optionclicked = 1;
//    @FXML
//    void optionPullUp(MouseEvent event) { // probably an easier way to do this, but i used a counter to track button clicks and translated it to a toggle function
//        // todo - make this a dark mode setting instead of volume slider
//        optionsButton.setOnAction(e -> {
//            optionclicked++;
//            optionsButton.getOnMouseClicked();
//        });
//
//        if (optionclicked % 1 == 0) {
//            volumePane.setOpacity(1);
//            volumePane.setDisable(false);
//        }
//        if (optionclicked % 2 == 0) {
//            volumePane.setOpacity(0);
//            volumePane.setDisable(true);
//        }
//    }

    @FXML
    void playMenu_Host(MouseEvent event) { // todo - opens FXML file for host screen -- shows you and empty slots until player joins
        // todo - change to proper fxml file or whatever is set up for host screen
        Stage stage = (Stage) playMenu_Exit.getScene().getWindow();
        try {
            FXMLLoader solo = new FXMLLoader(getClass().getResource("HostScreen.fxml"));
            Scene scene = new Scene(solo.load(), 1280, 720);
            stage.setTitle("Host");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void playMenu_Join(MouseEvent event) { // todo - opens up lobby screen

    }

    @FXML
    void playMenu_exitMenu(MouseEvent event) { // secondary button to close play menu (can also close by pressing the play button again)
        playmenuclickcount++;
        playMenu_Pane.setOpacity(0);
        playMenu_Pane.setDisable(true);
    }

//    @FXML
//    void darkMode_Toggle(ActionEvent event) {
//
//    }


    @FXML
    void playMenu_SoloStart(MouseEvent event) throws IOException { // loads solo mode
//        Stage stage = (Stage) playMenu_Exit.getScene().getWindow();
//        try {
//            FXMLLoader solo = new FXMLLoader(getClass().getResource("HostScreen.fxml"));
//            Scene scene = new Scene(solo.load(), 1280, 720);
//            stage.setTitle("Solo");
//            stage.setScene(scene);
//            stage.show();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }

}
