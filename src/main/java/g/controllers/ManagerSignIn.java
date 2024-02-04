package g.controllers;

import g.classes.Instruments;
import g.classes.Manager;
import g.classes.Proxy;
import g.classes.Student;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ManagerSignIn {

    @FXML
    private Button backButton;

    @FXML
    private TextField nameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private CheckBox show;

    @FXML
    private Button signInButton;

    public void initialize(){
        setStyle();

        backButton.setOnAction(event -> {
            Instruments.loadPage("student-sign-in.fxml", "Student Sign In Page");
            backButton.getScene().getWindow().hide();
        });

        show.setOnAction(actionEvent -> {
            if (show.isSelected()) {
                passwordField.setPromptText(passwordField.getText());
                passwordField.setText("");
            } else {
                passwordField.setText(passwordField.getPromptText());
                passwordField.setPromptText("");
            }
        });

        signInButton.setOnAction(event -> {
            boolean ch = false;
            Manager cur = null;
            for (Manager m: Proxy.getManagers()) {
                if(m.getUsername().equals(nameField.getText())) {
                    if(m.getPassword().equals(passwordField.getText())) {
                        ch = true;
                        cur = m;
                    }
                }
            }
            if(ch) {
                Proxy.setManagerInSystem(cur);
                Instruments.loadPage("manager-main.fxml", "Manager Main Page");
                signInButton.getScene().getWindow().hide();
            }
            else{
                Instruments.showAlert("Login Error", "Invalid username or password", Alert.AlertType.ERROR);
            }
        });
    }

    private void setStyle(){

        backButton.setOnMouseExited(event -> {
            backButton.setStyle("-fx-background-color: AZURE; -fx-background-insets: 2; -fx-background-radius: 5;");
        });
        backButton.setOnMouseEntered(event -> {
            backButton.setStyle("-fx-background-color: #959a9a; -fx-background-insets: 2; -fx-background-radius: 5;");
        });

        signInButton.setOnMouseExited(event -> {
            signInButton.setStyle("-fx-background-color: cadetblue; -fx-background-insets: 2; -fx-background-radius: 5;");
        });
        signInButton.setOnMouseEntered(event -> {
            signInButton.setStyle("-fx-background-color: #2F6F72FF; -fx-background-insets: 2; -fx-background-radius: 5;");
        });
    }

}
