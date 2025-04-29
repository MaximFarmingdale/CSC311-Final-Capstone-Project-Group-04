package com.example.csc311finalcapstoneprojectgroup04;

import java.io.Serializable;

/**
 * Used to store user information after successful logins and signups.
 * Successful signups will be added to the database. For a sign in
 * to be valid it must be the right username and passwords and for a
 * login to be valid it must be a unique password.
 */
public class User implements Serializable {
    public String username;
    private String password; //get rid of password later
    private double raceProgress;
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }
    public double getRaceProgress() {
        return raceProgress;
    }
    public void setRaceProgress(double raceProgress) {
        this.raceProgress = raceProgress;
    }
}
