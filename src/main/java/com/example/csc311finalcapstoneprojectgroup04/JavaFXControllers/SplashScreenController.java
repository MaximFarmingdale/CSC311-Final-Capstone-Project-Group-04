package com.example.csc311finalcapstoneprojectgroup04.JavaFXControllers;

import com.example.csc311finalcapstoneprojectgroup04.TypeApplication;
import com.example.csc311finalcapstoneprojectgroup04.User;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/// controller for the first screen the user sees, before they log in or start a match etc.
@Component
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
    @Autowired
    private ApplicationContext applicationContext;
    private Pattern usernamePattern = Pattern.compile("^[a-zA-Z0-9]{2,16}");
    private Pattern passwordPattern = Pattern.compile("^[a-zA-Z0-9]{4,16}"); //change the pattern to have one special character.
    private Matcher matcher;
    /**
     * Method that on button clickchecks if the user is typing in correct sign-up or
     * login information. Adds signups to the database and pass it to the MainMenuScreen
     * and just directly passes the login user to the MainMenuScreen.
     * @param event
     */
    @FXML
    void login(ActionEvent event) {
        //null-pointer check
        String username = usernameField.getText();
        String password = passwordField.getText();
        matcher = usernamePattern.matcher(username);
        if(!matcher.matches()) {
            problemText.setText("Make Username Between 2-16 Characters");
            errorColor(usernameField);
            return;
        }
        matcher = passwordPattern.matcher(password);
        if(!matcher.matches()) {
            problemText.setText("Make Password Between 4-16 Characters");
            errorColor(passwordField);
            return;
        }
        User currentUser = new User(username, password);
        //if the user is signing up
        if(newUser) {
            if (checkUniqueUsername(username)) {
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

    /**
     * Checks if a username is unique.
     * @param username
     * @return boolean if its true or not.
     */
    private boolean checkUniqueUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            return false;
        }
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Helper method to get current User from an array of users by username.
     * @param username
     * @return current user
     */
    private User getUser(String username) {
        for (User user : users) {
            if(user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Makes either the username or password text field light up red to signify a
     * problem.
     * @param field username or password field
     */
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

    /**
     * Switches the GUI elements to the user login in to signing up and vise versa
     * also stores if the user is a new user to use in other methods to make sure to
     * sign them up.
     * @param event
     */
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

    /**
     * Changes the scene to MainMenu
     * @param currentUser
     */
    public void changeController(User currentUser) {
        try {
            Stage stage = (Stage) signInButton.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/JavaFX_FXML/MainMenuScreen.fxml"));
            loader.setControllerFactory(applicationContext::getBean); //gets beans from spring
            Parent newRoot = loader.load();
            MainMenuScreenController controller = loader.getController();
            controller.enterMainMenu(currentUser);
            Scene scene = new Scene(newRoot, 1270, 720);
            stage.setScene(scene);
            stage.show();
            if(applicationContext == null){
                System.out.println("ApplicationContext is null");
            }
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