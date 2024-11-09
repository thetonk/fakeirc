module com.sbaltsas.assignments.networksiichat {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.sbaltsas.assignments.networksiichat to javafx.fxml;
    exports com.sbaltsas.assignments.networksiichat;
}