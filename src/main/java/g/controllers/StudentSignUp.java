package g.controllers;

import g.classes.ConnectToDB;
import g.classes.Instruments;
import g.classes.Proxy;
import g.classes.Student;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;


public class StudentSignUp {

    @FXML
    private TextField codeField;

    @FXML
    private TextField emailField;

    @FXML
    private Button loginButton;

    @FXML
    private TextField nameField;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField repeatField;

    @FXML
    private Button sendButton;

    @FXML
    private Button signUpButton;

    @FXML
    private CheckBox show;

    @FXML
    private TextField surnameField;

    private int code;

    public void initialize(){
        setMouseEnterExited();

        loginButton.setOnAction(actionEvent -> {
            Instruments.loadPage("student-sign-in.fxml", "Student Sign In Page");
            loginButton.getScene().getWindow().hide();
        });

        sendButton.setOnAction(actionEvent -> {
            if(checkUser()){
                Instruments.showAlert("Invalid Email", "User with this email already exists!!!", Alert.AlertType.ERROR);
            }
            else code = Instruments.sendVerificationCode(emailField.getText());
        });

        show.setOnAction(actionEvent -> {
            if (show.isSelected()) {
                passwordField.setPromptText(passwordField.getText());
                passwordField.setText("");
                repeatField.setPromptText(repeatField.getText());
                repeatField.setText("");
            } else {
                passwordField.setText(passwordField.getPromptText());
                passwordField.setPromptText("");
                repeatField.setText(repeatField.getPromptText());
                repeatField.setPromptText("");
            }
        });

        signUpButton.setOnAction(actionEvent -> {
            if (isValidInput()) {
                if(checkUser()){
                    Instruments.showAlert("Invalid Email", "User with this email already exists!!!", Alert.AlertType.ERROR);
                }
                else registerStudent();
            } else {
                Instruments.showAlert("Invalid inputs", "Please check your inputs and try again.", Alert.AlertType.ERROR);
            }
        });

    }

    private void setMouseEnterExited(){
        sendButton.setOnMouseEntered(mouseEvent -> {
            sendButton.setStyle("-fx-background-color: #3c7273");
        });

        sendButton.setOnMouseExited(mouseEvent -> {
            sendButton.setStyle("-fx-background-color: CADETBLUE;");
        });

        signUpButton.setOnMouseEntered(mouseEvent -> {
            signUpButton.setStyle("-fx-background-color: #3c7273");
        });

        signUpButton.setOnMouseExited(mouseEvent -> {
            signUpButton.setStyle("-fx-background-color: CADETBLUE;");
        });

        loginButton.setOnMouseEntered(mouseEvent -> {
            loginButton.setStyle("-fx-background-color: #aab0b0");
        });

        loginButton.setOnMouseExited(mouseEvent -> {
            loginButton.setStyle("-fx-background-color: AZURE;");
        });
    }

    private boolean isValidInput() {
        return !"".equals(nameField.getText()) &&
                !"".equals(passwordField.getText()) &&
                passwordField.getText().equals(repeatField.getText()) &&
                codeField.getText().equals("" + code) &&
                !"".equals(emailField.getText());
    }

    private boolean checkUser(){
        boolean check = false;
        for (Student s: Proxy.getStudents().values()) {
            if(s.getEmail().equals(emailField.getText())) {
                check = true;
                break;
            }
        }
        return check;
    }

    private void registerStudent(){
        Student student = new Student(getNextStudentId(), nameField.getText(), surnameField.getText(),
                emailField.getText(), passwordField.getText(), true);
        String sql = "INSERT INTO Students (student_id, name, surname, email, subscribed, password) " +
                "VALUES (" + student.getStudentId() + ", '" + student.getName() + "', '" +
                student.getSurname() + "', '" + student.getEmail() + "', " +
                student.isLibrarySubscribed() + ", '" + student.getPassword() + "')";
        Instruments.executeUpdate(sql);
        Proxy.getStudents().put(student.getStudentId(), student);
        Proxy.getSubscribers().addStudent(student);

        Instruments.showAlert("Success", "Student registered successfully.", Alert.AlertType.INFORMATION);
        Instruments.loadPage("student-sign-in.fxml", "Student Sign In Page");
        loginButton.getScene().getWindow().hide();
    }

    private int getNextStudentId() {
        Proxy.setMaxStudentId(Proxy.getMaxStudentId() + 1);
        System.out.println(Proxy.getMaxStudentId());
        return Proxy.getMaxStudentId();
    }

}
