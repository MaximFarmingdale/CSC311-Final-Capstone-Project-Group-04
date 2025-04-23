package com.example.csc311finalcapstoneprojectgroup04.NetworkMessagesandUpdate;

import java.io.Serializable;
/// message class for the message objects sent over the sockets
public class Message implements Serializable {
    public String message;
    public String sender;
    public Message(String sender, String message) {
        this.sender = sender;
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getSender() {
        return sender;
    }
}
