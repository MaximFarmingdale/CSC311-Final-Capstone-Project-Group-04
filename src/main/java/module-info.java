module com.example.csc311finalcapstoneprojectgroup04 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.csc311finalcapstoneprojectgroup04 to javafx.fxml;
    exports com.example.csc311finalcapstoneprojectgroup04;
}