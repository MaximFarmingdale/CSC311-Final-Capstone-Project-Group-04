package com.example.csc311finalcapstoneprojectgroup04.JavaFXControllers;

import com.example.csc311finalcapstoneprojectgroup04.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class SoloModeController {

    @FXML private TextField enterWordField;

    @FXML private Button startButton;

    @FXML private ListView<String> wordList;

    @FXML private Text wordsEnteredCounter;

    @FXML private Circle youNode;

    @FXML private Text youNodetext;

    private User user;

    @FXML private Text enterWordLabel;

    private List<String> wordPool;

    // hashset for words already used from list
    private Set<String> usedWords = new HashSet<>();

    // observablelist to help with wordlist updating current word needed to spell
    private final ObservableList<String> displayList = FXCollections.observableArrayList();

    private int correctCount = 0;

    private int attemptCount = 0;

    private final int maxWords = 20;

    private final int winThreshold = 10;

    private String currentWord;

    /**
     * functionality for the start button -- starts practice mode
     */
    @FXML
    public void initialize() {
        startButton.setOnAction(e -> startGame());
        enterWordField.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.SPACE) {
                handleInput();
            }
        });
    }

    /**
     * starts game and initalizes the wordpool with 20 predetermined words. words are selected from random
     */
    private void startGame() {
        correctCount = 0;
        attemptCount = 0;
        usedWords.clear();
        displayList.clear();

        wordsEnteredCounter.setText("0");
        enterWordLabel.setText("Enter Word");
        enterWordField.clear();
        enterWordField.setDisable(false);
        enterWordField.requestFocus();
        youNode.setTranslateX(-300);
        youNodetext.setTranslateX(-300);

        // predetermined word pool
        wordPool = new ArrayList<>(List.of(
                "fountain", "famine", "keyboard", "plateau", "class", "java", "steam", "extradite", "expedition", "mountain",
                "determination", "array", "string", "integer", "training", "switch", "discovery", "boolean", "application", "eureka"
        ));
        Collections.shuffle(wordPool);

        wordList.setItems(displayList);
        loadNextWord();
    }

    /**
     * loads next word after word is checked and auto scrolls down after new word in loaded into the list
     */
    private void loadNextWord() {
        if (usedWords.size() >= maxWords) {
            endGame();
            return;
        }

        for (String word : wordPool) {
            if (!usedWords.contains(word)) {
                currentWord = word;
                usedWords.add(word);
                displayList.add("Type this: " + currentWord);
                wordList.scrollTo(displayList.size() - 1);
                return;
            }
        }

        endGame();
    }

    /**
     * handles input and checks whethered entered word correctly matches prompted word on pressing the spacebar
     */
    private void handleInput() {
        if (currentWord == null || correctCount >= winThreshold || attemptCount >= maxWords) return;

        String typed = enterWordField.getText().trim();

        if (typed.equals(currentWord)) {
            correctCount++;
            displayList.add(currentWord + " -- correct!");
            moveNode();
        } else {
            displayList.add(currentWord + " -- incorrect!");
        }

        attemptCount++;
        wordsEnteredCounter.setText(String.valueOf(correctCount));
        enterWordField.clear();

        if (correctCount >= winThreshold || attemptCount >= maxWords) {
            endGame();
        } else {
            loadNextWord();
        }
    }

    /**
     * method to move the youNode and youNodeText with each correct word entered
     */
    private void moveNode() {
        double progress = (double) correctCount / winThreshold;
        double newTranslateX = -300 + progress * 600;

        youNode.setTranslateX(newTranslateX);
        youNodetext.setTranslateX(newTranslateX);
    }

    /**
     * ends the game after 10 correct words or all the words in the list have been checked
     */
    private void endGame() {
        enterWordField.setDisable(true);
        if (correctCount >= winThreshold) {
            enterWordLabel.setText("Congrats!");
        } else {
            enterWordLabel.setText("You Lose");
        }
    }

    public void enterSoloMode(User currentUser) {
        user = currentUser;
    }
}
