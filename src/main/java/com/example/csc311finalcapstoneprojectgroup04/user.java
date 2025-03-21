package com.example.csc311finalcapstoneprojectgroup04;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.time.Duration;
import java.util.Optional;
/// for connecting to other users
/// incomplete and might get rid of
public class user {
    private String IP;
    private String Username;
    private String Client;
    private String request;
    public user(String Username, String Client) {
        request =
        this.Username = Username;
        this.Client = Client;
    }

}
