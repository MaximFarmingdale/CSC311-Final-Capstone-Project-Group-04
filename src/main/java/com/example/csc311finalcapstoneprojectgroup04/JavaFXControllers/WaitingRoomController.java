package com.example.csc311finalcapstoneprojectgroup04.JavaFXControllers;

import com.example.csc311finalcapstoneprojectgroup04.User;
import javafx.event.ActionEvent;

/// controller for the lobby select mode

public class WaitingRoomController {
    private User user;
    public void enterWaitingRoom(User currentUser) {
        user = currentUser;
    }
}
