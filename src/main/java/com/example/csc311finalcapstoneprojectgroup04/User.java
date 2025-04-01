package com.example.csc311finalcapstoneprojectgroup04;

public class User {
    public String username;
    private String password;
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

}
