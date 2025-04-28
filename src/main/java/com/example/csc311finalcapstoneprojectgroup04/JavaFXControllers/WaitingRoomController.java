package com.example.csc311finalcapstoneprojectgroup04.JavaFXControllers;

import com.example.csc311finalcapstoneprojectgroup04.Eureka.ClientEureka;
import com.example.csc311finalcapstoneprojectgroup04.User;
import com.netflix.appinfo.InstanceInfo;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

/// controller for the lobby select mode
//@Controller?
@Component
public class WaitingRoomController implements Initializable {
    private User user;
    @FXML
    private VBox LobbyVbox;
    @Autowired
    private ClientEureka clientEureka;
    private ObservableList<InstanceInfo> instances;
    private HashMap<InstanceInfo, HBox> instancesMap;
    private final int maxPlayers = 8;
    public void enterWaitingRoom(User currentUser) {
        user = currentUser;
        instances = FXCollections.observableArrayList(clientEureka.fillList());
        instances.addListener(new ListChangeListener<InstanceInfo>() {
            @Override
            public void onChanged(Change<? extends InstanceInfo> c) {
                while (c.next()) {
                    if (c.wasAdded()) {
                        for (InstanceInfo addedInstance : c.getAddedSubList()) {
                            HBox hbox = new HBox();
                            updateHbox(hbox, addedInstance);
                            instancesMap.put(addedInstance, hbox);
                            LobbyVbox.getChildren().add(hbox);
                        }
                    }
                    else if (c.wasRemoved()) {
                        for (InstanceInfo removedInstance : c.getRemoved()) {
                            HBox hbox = instancesMap.remove(removedInstance);
                            if (hbox != null) {
                                LobbyVbox.getChildren().remove(hbox);
                            }
                        }
                    }
                    else if(c.wasPermutated())//check if this is needed
                        ;
                    else if(c.wasUpdated()) {
                        for (InstanceInfo updatedInstance : instances) {
                            HBox hbox = instancesMap.get(updatedInstance);
                            if (hbox != null) {
                                hbox.getChildren().clear();
                                updateHbox(hbox, updatedInstance);
                            }
                        }
                    }
                }
            }
        } );
    }
    private void fillLobbyVbox() {
        for (InstanceInfo i : instances) {
            HBox hbox = new HBox();
            updateHbox(hbox, i);
            instancesMap.put(i, hbox);
            LobbyVbox.getChildren().add(hbox);
        }
    }
    private void updateHbox(HBox hbox, InstanceInfo i) {
        HBox hbox1 = new HBox();
        Label label = new Label(i.getMetadata().get("host-name"));
        Label label2 = new Label(i.getMetadata().get("current-players"));
        hbox1.getChildren().addAll(label, label2);
        Label label3 = new Label(i.getMetadata().get("active-game"));
        hbox1.getChildren().addAll(label);
        hbox.getChildren().addAll(hbox1);
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
