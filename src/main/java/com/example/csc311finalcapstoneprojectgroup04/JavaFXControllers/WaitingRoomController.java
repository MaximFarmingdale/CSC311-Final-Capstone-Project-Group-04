package com.example.csc311finalcapstoneprojectgroup04.JavaFXControllers;

import com.example.csc311finalcapstoneprojectgroup04.Eureka.ClientEureka;
import com.example.csc311finalcapstoneprojectgroup04.User;
import com.netflix.appinfo.InstanceInfo;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;


//@Controller?

/**
 * The controller for the WaitingRoom fxml, it displays all the lobbies in a visual form. It does this
 * by using an observable list called instances to display the lobby objects and a listener to detect changes
 * and update the GUI.
 */
@Component
public class WaitingRoomController implements Initializable {
    private User user;
    @FXML
    private VBox LobbyVbox;
    @Autowired
    private ClientEureka clientEureka; //it is not loading the client correctly, I think?
    private ObservableList<InstanceInfo> instances = FXCollections.observableArrayList();
    private HashMap<InstanceInfo, HBox> instancesMap = new HashMap<>();
    private final int maxPlayers = 8;
    public void enterWaitingRoom(User currentUser) {
        user = currentUser;

    }
    /**
     * method to update the observable list only with new changes
     * Unfishinshed
     */
    private void addChangesToList(){//in high likelihood I will not be able to implement this correctly in time
        List<InstanceInfo> changes = clientEureka.fillList();
        for (InstanceInfo i : changes) {
            if (instancesMap.containsKey(i)) {

            }
            if(!instancesMap.containsKey(i)){

            }

        }
    }
    @FXML
    void goToLobby(ActionEvent event) {
        //ping first?
    }

    /**
     * Updates individual Hboxes with lobbies
     * @param hbox
     * @param i
     */
    private void updateHbox(HBox hbox, InstanceInfo i) {
        Label label1 = new Label(i.getMetadata().get("host-name"));
        Label label2 = new Label(i.getMetadata().get("current-players" ) + "/"+maxPlayers);
        Label label3 = new Label(i.getMetadata().get("active-game"));
        Label label4 = new Label(i.getIPAddr());
        VBox vbox = new VBox();
        vbox.getChildren().addAll(label1, label2, label3, label4);
        hbox.getChildren().add(vbox);
        hbox.setStyle("-fx-background-color: #bf1616");
        hbox.setStyle("-fx-border-color: #16bf8c");
        hbox.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            HBox hbox2 = (HBox) event.getSource();
            VBox v = (VBox) hbox2.getChildren().get(0);
            Label ip = (Label) v.getChildren().get(3);
            System.out.println(ip.getText());
            //ping host
        });

    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(clientEureka != null) {
            instances.addListener(new ListChangeListener<InstanceInfo>() {
                @Override
                public void onChanged(Change<? extends InstanceInfo> c) {
                    while (c.next()) {
                        if (c.wasAdded()) {
                            for (InstanceInfo addedInstance : c.getAddedSubList()) {//if elements are added to the list
                                HBox hbox = new HBox();
                                updateHbox(hbox, addedInstance);
                                instancesMap.put(addedInstance, hbox);
                                LobbyVbox.getChildren().add(hbox);
                            }
                        } else if (c.wasRemoved()) { //if elements were removed from the list, currently unused?
                            for (InstanceInfo removedInstance : c.getRemoved()) {
                                HBox hbox = instancesMap.remove(removedInstance);
                                if (hbox != null) {
                                    LobbyVbox.getChildren().remove(hbox);
                                }
                            }
                        } else if (c.wasPermutated())//check if this is needed
                            ;
                        else if (c.wasUpdated()) {//currently impossible to reach until I update the lobby refresh
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

            });
            List<InstanceInfo> temp = clientEureka.fillList();
            if (temp != null) { //making sure that the list is not null before you try to make the observable list access it
                instances.setAll(clientEureka.fillList());
            }
        }
        //refreshes lobbies every second
        new Thread(() -> {
            while (true) {
                // later on add a way for lobby's that have the same metadata to stay instead of getting cleared
                if(clientEureka != null) {
                    List<InstanceInfo> latest = clientEureka.fillList();
                    if(latest != null) {
                        Platform.runLater(() -> {
                            LobbyVbox.getChildren().clear();
                            instances.setAll(latest);
                        });
                    }
                }
                else
                    System.out.println("Waiting for Eureka");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();//add to other interrupted exceptions
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }
}
