package com.example.csc311finalcapstoneprojectgroup04;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/// controller for the first screen the user sees, before they log in or start a match etc.
public class SplashScreenController implements Initializable {
    private List<User> users = new ArrayList<>();
    // this user only exists for debugging get rid of it in the final version
    private User user;
    private boolean newUser = false;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField usernameField;

    @FXML
    private Text signUpText;

    @FXML
    private Text problemText;

    @FXML
    private Text promptTextOne;

    @FXML
    private Text promptTextTwo;

    @FXML
    private Button signInButton;

    @FXML
    private HBox hbox;

    @FXML
    void login(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        if(newUser) {
            if (checkUniqueUsername(username)) {
                users.add(new User(username, password));
                //call change controller here
            } else {
                problemText.setText("Username is already taken or invalid!");
                errorColor(usernameField);
            }
        }
        else {
            if (!checkUniqueUsername(username)) {
                if(getUser(username).checkPassword(password)) {
                    //change controller
                    System.out.println(getUser(username));
                }
                else {
                    problemText.setText("Password is incorrect!");
                    errorColor(passwordField);
                }
            }
            else {
                problemText.setText("Wrong username!");
                errorColor(usernameField);
            }
        }
    }
    private boolean checkUniqueUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            return false;
        }
        for (User user : users) {
            if (user.username.equals(username)) {
                return false;
            }
        }
        return true;
    }
    private User getUser(String username) {
        for (User user : users) {
            if(user.username.equals(username)) {
                return user;
            }
        }
        return null;
    }
    private void errorColor(TextField field) {
        if (field.equals(usernameField)) {
            passwordField.setStyle("-fx-border-color: null");
            usernameField.setStyle("-fx-border-color: RED");
        }
        else {
            usernameField.setStyle("-fx-border-color: null");
            passwordField.setStyle("-fx-border-color: RED");
        }
    }
    @FXML
    void switchModes(MouseEvent event) {
        if(newUser) {
            newUser = false;
            promptTextOne.setWrappingWidth(730.0);
            promptTextOne.setText("Sign In To Your Account Using Your Username And Password");
            promptTextTwo.setWrappingWidth(255.6);
            promptTextTwo.setText("Don't Have an Account?");
            signUpText.setText("Signup");
        }
        else {
            newUser = true;
            promptTextOne.setWrappingWidth(875.0);
            promptTextOne.setText("Sign Up To Your New Account Using a Unique Username And a Password");
            promptTextTwo.setWrappingWidth(190.4);
            promptTextTwo.setText("Have an Account?");
            signUpText.setText("Log In");
        }
    }
    //add when you make the next controller
    /*
    public void changeController(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("WaitingRoomTestController.fxml"));
            Parent newRoot = loader.load();
            WaitingRoomTestController controller = loader.getController();
            controller.enterWaitingRoom(user);
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
        //add code to get user class from a database
        //for now use this hard coded user
        user = new User("maxim", "password");
        users.add(user);
    }
}