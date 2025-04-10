package com.example.csc311finalcapstoneprojectgroup04;

import java.io.Serializable;

/// User Object, which pulls from the database and is also sent to other users
/// to update race progress.
/// get rid of password in the final version
public class User implements Serializable {
    public String username;
    private String password;
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
