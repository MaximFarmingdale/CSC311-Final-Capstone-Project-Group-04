package com.example.csc311finalcapstoneprojectgroup04.JavaFXControllers;

import com.example.csc311finalcapstoneprojectgroup04.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class HostScreenController {
    private User user;
    @Autowired
    private ApplicationContext applicationContext;
    public void enterHostScreen(User currentUser) {
        user = currentUser;
    }

}
