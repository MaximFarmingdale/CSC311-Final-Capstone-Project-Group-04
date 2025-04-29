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
    private Button debug;

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
        //null-pointer check
        if(usernameField.getText().equals("")) {
            problemText.setText("Username is empty!");
            errorColor(usernameField);
            return;
        }
        String username = usernameField.getText();
        if(passwordField.getText().equals("")) {
            problemText.setText("Password is empty!");
            errorColor(passwordField);
            return;
        }
        String password = passwordField.getText();
        User currentUser = new User(username, password);
        //if the user is signing up
        if(newUser) {
            if (checkUniqueUsername(username)) {
                users.add(currentUser); //get rid of later
                //call change controller here
                changeController(currentUser);
            } else {
                problemText.setText("Username is already taken or invalid!");
                errorColor(usernameField);
            }
        }
        //if the user is logging in
        else {
            if (!checkUniqueUsername(username)) {
                if(getUser(username).checkPassword(password)) {
                    //change controller
                    System.out.println(getUser(username)); //get rid of later
                    changeController(currentUser);
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
    //checks if the username is already in use or not
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
    //helper method to get back a user object with a given username
    private User getUser(String username) {
        for (User user : users) {
            if(user.username.equals(username)) {
                return user;
            }
        }
        return null;
    }
    //changes the color of an incorrect field to make the user know they filled it in wrong
    //change the name of this method later
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
    //Lets the user switch from signing in to logging in and vice versa
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
    @FXML
    void addUsers(ActionEvent event) {

    }
    public void changeController(User currentUser) {
        try {
            Stage stage = (Stage) signInButton.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/JavaFX_FXML/MainMenuScreen.fxml"));
            Parent newRoot = loader.load();
            MainMenuScreenController controller = loader.getController();
            controller.enterMainMenu(currentUser);
            Scene scene = new Scene(newRoot, 1270, 720);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //add code to get user class from a database
        //for now use this hard coded user
        user = new User("maxim", "password");
        users.add(user);
    }

    @FXML
    void debuglogin(MouseEvent event) throws IOException {
        Stage stage = (Stage) signInButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(TypeApplication.class.getResource("MainMenuScreen.fxml"));//change to whatever fxml file you are testing
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        stage.setTitle("Type Application");
        stage.setScene(scene);
        stage.show();
    }
}