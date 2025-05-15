package com.example.csc311finalcapstoneprojectgroup04.JavaFXControllers;

import com.example.csc311finalcapstoneprojectgroup04.DataBase;
import com.example.csc311finalcapstoneprojectgroup04.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/// controller for the first screen the user sees, before they log in or start a match etc.
@Component
public class SplashScreenController {
    private boolean newUser = false;

    @FXML
    private Button debug;


    @FXML
    private PasswordField passwordField;

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
    private DataBase dataBase = new DataBase();
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
        String hashCode = String.valueOf(passwordField.getText().hashCode());//keep for now
        String password = String.valueOf(passwordField.getText());
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
            if (dataBase.checkDistinct(username)) {
                dataBase.addUser(username, hashCode);
                goToMainMenu(currentUser);
            } else {
                problemText.setText("Username is already taken or invalid!");
                errorColor(usernameField);
            }
        }
        //if the user is logging in
        else {
            if (!dataBase.checkDistinct(username)) {
                if(dataBase.validLogin(username, hashCode)) {
                    goToMainMenu(currentUser);
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
    public void goToMainMenu(User currentUser) {
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


    @FXML
    void debuglogin(MouseEvent event) throws IOException {
        goToMainMenu(new User("maxim", "password"));
    }
}