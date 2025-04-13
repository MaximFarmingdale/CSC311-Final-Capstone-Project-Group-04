module com.example.csc311finalcapstoneprojectgroup04 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.net.http;


    opens com.example.csc311finalcapstoneprojectgroup04 to javafx.fxml;
    exports com.example.csc311finalcapstoneprojectgroup04;
    exports com.example.csc311finalcapstoneprojectgroup04.TCPNetworking;
    opens com.example.csc311finalcapstoneprojectgroup04.TCPNetworking to javafx.fxml;
    exports com.example.csc311finalcapstoneprojectgroup04.Lobby;
    opens com.example.csc311finalcapstoneprojectgroup04.Lobby to javafx.fxml;
    exports com.example.csc311finalcapstoneprojectgroup04.NetworkMessagesandUpdate;
    opens com.example.csc311finalcapstoneprojectgroup04.NetworkMessagesandUpdate to javafx.fxml;
}