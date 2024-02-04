package g.controllers;

import g.classes.Instruments;
import g.classes.Proxy;
import g.classes.Student;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ForgotPassword {

    @FXML
    private Button change;

    @FXML
    private TextField email;

    @FXML
    private PasswordField passnew;

    @FXML
    private PasswordField password;

    @FXML
    private PasswordField repeatnew;

    @FXML
    private Button send;

    @FXML
    private CheckBox showNew;

    @FXML
    private CheckBox showOld;

    private Student student;

    public void initialize(){
        showOld.setOnAction(actionEvent -> {
            if (showOld.isSelected()) {
                password.setPromptText(password.getText());
                password.setText("");
            } else {
                password.setText(password.getPromptText());
                password.setPromptText("");
            }
        });

        showNew.setOnAction(actionEvent -> {
            if (showNew.isSelected()) {
                passnew.setPromptText(passnew.getText());
                passnew.setText("");
                repeatnew.setPromptText(repeatnew.getText());
                repeatnew.setText("");
            } else {
                passnew.setText(passnew.getPromptText());
                passnew.setPromptText("");
                repeatnew.setText(repeatnew.getPromptText());
                repeatnew.setPromptText("");
            }
        });

        send.setOnAction(actionEvent -> {
            for(Student st: Proxy.getStudents().values()){
                if(st.getEmail().equals(email.getText())){
                    Instruments.sendMessage(st.getEmail(),
                            "Your current password", st.getPassword());
                    student = st;
                }
            }
        });

        change.setOnAction(actionEvent -> {
            for(Student st: Proxy.getStudents().values()){
                if(st.getEmail().equals(email.getText())){
                    student = st;
                }
            }
            if(!password.getText().equals(student.getPassword())
                    || !passnew.getText().equals(repeatnew.getText())
            ){
                Instruments.showAlert("Invalid inserts!!!",  "Please check your inputs and try again.", Alert.AlertType.ERROR);
            }
            else {
                student.changePassword(passnew.getText());
                passnew.getScene().getWindow().hide();
            }
        });
    }

}
