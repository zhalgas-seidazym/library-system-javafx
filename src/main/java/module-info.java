module g.l {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.mail;

    opens g to javafx.fxml;
    exports g;
    exports g.classes;
    opens g.classes to javafx.fxml;
    opens g.controllers to javafx.fxml;
}