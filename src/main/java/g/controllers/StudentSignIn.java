package g.controllers;

import g.classes.Instruments;
import g.classes.Proxy;
import g.classes.Student;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class StudentSignIn {

    @FXML
    private Button aboutUsButton;

    @FXML
    private Label forgotPassword;

    @FXML
    private CheckBox show;

    @FXML
    private Button managerSignInButton;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button studentSignInButton;

    @FXML
    private Button studentSignUpButton;

    @FXML
    private TextField usernameField;

    public void initialize(){
        setStyle();

        aboutUsButton.setOnAction(action ->{
            Instruments.loadPage("about-us.fxml", "About Us");
            aboutUsButton.getScene().getWindow().hide();
        });

        studentSignUpButton.setOnAction(actionEvent -> {
            Instruments.loadPage("student-sign-up.fxml", "Student Sign Up Page");
            studentSignUpButton.getScene().getWindow().hide();
        });

        managerSignInButton.setOnAction(actionEvent -> {
            Instruments.loadPage("manager-sign-in.fxml", "Manager Sign In Page");
            managerSignInButton.getScene().getWindow().hide();
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

        studentSignInButton.setOnAction(event -> {
            boolean ch = false;
            for (Student s: Proxy.getStudents().values()) {
                if(s.getEmail().equals(usernameField.getText())) {
                    if(s.getPassword().equals(passwordField.getText())) {
                        ch = true;
                        Proxy.setStudentInSystem(s);
                    }
                }
            }
            if(ch) {
                Instruments.loadPage("student-main.fxml", "Student Main Page");
                studentSignInButton.getScene().getWindow().hide();
            }
            else{
                Instruments.showAlert("Login Error", "Invalid username or password", Alert.AlertType.ERROR);
            }
        });

        forgotPassword.setOnMousePressed(mouseEvent -> {
            Instruments.loadPage("forgot-password.fxml", "Forgot Password");
        });
    }

    private void setStyle(){

        studentSignUpButton.setOnMouseEntered(event -> {
            studentSignUpButton.setStyle("-fx-background-color: #257578; -fx-background-insets: 2; -fx-background-radius: 5;");
        });

        studentSignUpButton.setOnMouseExited(event -> {
            studentSignUpButton.setStyle("-fx-background-color: CADETBLUE; -fx-background-insets: 2; -fx-background-radius: 5;");
        });

        forgotPassword.setOnMouseEntered(action -> {
            forgotPassword.setStyle("-fx-text-fill: azure");
        });

        forgotPassword.setOnMouseExited(action -> {
            forgotPassword.setStyle("-fx-text-fill: black");
        });

        studentSignInButton.setOnMouseEntered(event -> {
            studentSignInButton.setStyle("-fx-background-color: #cccece; -fx-background-insets: 2; -fx-background-radius: 5;");
        });

        studentSignInButton.setOnMouseExited(event -> {
            studentSignInButton.setStyle("-fx-background-color: Azure; -fx-background-insets: 2; -fx-background-radius: 5;");
        });

        managerSignInButton.setOnMouseEntered(event -> {
            managerSignInButton.setStyle("-fx-background-color: #cccece; -fx-background-insets: 2; -fx-background-radius: 5;");
        });

        managerSignInButton.setOnMouseExited(event -> {
            managerSignInButton.setStyle("-fx-background-color: Azure; -fx-background-insets: 2; -fx-background-radius: 5;");
        });

        aboutUsButton.setOnMouseEntered(event -> {
            aboutUsButton.setStyle("-fx-background-color: #348789; -fx-background-radius: 10;");
        });

        aboutUsButton.setOnMouseExited(event -> {
            aboutUsButton.setStyle("-fx-background-color: CADETBLUE; -fx-background-radius: 10;");
        });
    }

}
