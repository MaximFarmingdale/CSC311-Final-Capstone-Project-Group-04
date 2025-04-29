package com.example.csc311finalcapstoneprojectgroup04.NetworkMessagesandUpdate;

import com.example.csc311finalcapstoneprojectgroup04.User;

import java.io.Serializable;

/**
 * Used to send updates on client and host progress to each other.
 * Keeps track of the current race progress via an int of the current
 * word index, a double that signifies percentage completion and a
 * boolean for race completion.
 */
public class RaceUpdate implements Serializable {
    private int currentWordIndex;
    //private double wordsPerMinute;
    private boolean isWinner;
    private double progress;
    private String username;
    public RaceUpdate(String username) {
        this.username = username;
        this.currentWordIndex = 0;
        this.isWinner = false;
        this.progress = 0.0;
    }
    public int getCurrentWordIndex() {
        return currentWordIndex;
    }
    public void setCurrentWordIndex(int currentWordIndex) {
        this.currentWordIndex = currentWordIndex;
    }
    public void incrementWordIndex(){currentWordIndex++;}
    public boolean isWinner() {
        return isWinner;
    }
    public void setWinner(boolean winner) {
        isWinner = winner;
    }
    public double getProgress() {
        return progress;
    }
    public void setProgress(double progress) {
        this.progress = progress;
    }
}
