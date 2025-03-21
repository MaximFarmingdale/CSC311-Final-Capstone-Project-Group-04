module com.example.csc311finalcapstoneprojectgroup04 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.net.http;


    opens com.example.csc311finalcapstoneprojectgroup04 to javafx.fxml;
    exports com.example.csc311finalcapstoneprojectgroup04;
}