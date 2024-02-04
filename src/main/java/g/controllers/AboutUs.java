package g.controllers;

import g.classes.Instruments;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class AboutUs {

    @FXML
    private Button backbutton;

    public void initialize(){
        backbutton.setOnAction(actionEvent -> {
            Instruments.loadPage("student-sign-in.fxml", "Student Sign In Page");
            backbutton.getScene().getWindow().hide();
        });

        backbutton.setOnMouseEntered(event -> {
            backbutton.setStyle("-fx-background-insets: 2;-fx-background-color: #348789; -fx-background-radius: 5;");
        });

        backbutton.setOnMouseExited(event -> {
            backbutton.setStyle("-fx-background-insets: 2;-fx-background-color: CADETBLUE; -fx-background-radius: 5;");
        });
    }

}
