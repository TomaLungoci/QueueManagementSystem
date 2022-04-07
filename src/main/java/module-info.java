module com.example.asg2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.asg2 to javafx.fxml;
    exports com.example.asg2;
    exports com.example.asg2.Controller;
    opens com.example.asg2.Controller to javafx.fxml;
    exports com.example.asg2.Model;
    opens com.example.asg2.Model to javafx.fxml;
}