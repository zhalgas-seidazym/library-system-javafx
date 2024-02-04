package g.controllers;

import g.classes.Instruments;
import g.classes.Proxy;
import g.classes.Student;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class ManagerMain {

    @FXML
    private Button add;

    @FXML
    private Text books;

    @FXML
    private TextArea message;

    @FXML
    private Text name;

    @FXML
    private Button notify;

    @FXML
    private Button send;

    @FXML
    private Button signOut;

    @FXML
    private Text refresh;

    @FXML
    private TextField title;

    public void initialize(){
        setStyle();
        name.setText(Proxy.getManagerInSystem().getUsername());
        books.setText(Proxy.getBooks().size() + "");

        send.setOnAction(event -> {
            if("".equals(title.getText()) || "".equals(message.getText()))
                Instruments.showAlert("Invalid inputs", "Please correct your inputs and try again!", Alert.AlertType.ERROR);
            else {
                send(title.getText(), message.getText());
                title.setText("");
                message.setText("");
            }
        });

        notify.setOnAction(event -> {
            for (Student st: Proxy.getStudents().values()) {
                System.out.println(st.getOverdueBooksList());
                if(st.getOverdueBooksList() != null) {
                    Instruments.sendMessage(st.getEmail(), "Notifaction About Dates", st.getOverdueBooksList());
                }
            }
        });

        add.setOnAction(event -> {
            Instruments.loadPage("add-book.fxml", "Add Book...");
        });

        refresh.setOnMousePressed(event -> {
            books.setText(Proxy.getBooks().size() + "");
        });

        signOut.setOnAction(actionEvent -> {
            Instruments.loadPage("student-sign-in.fxml", "Student Sign In");
            Proxy.setManagerInSystem(null);
            signOut.getScene().getWindow().hide();
        });
    }

    private void send(String title, String details){
        Proxy.getSubscribers().notifyStudents(title, details);
    }

    private void setStyle(){
        signOut.setOnMouseEntered(event -> {
            signOut.setStyle("-fx-background-color: #2C6F72FF; -fx-background-insets: 3; -fx-background-radius: 5;");
        });

        signOut.setOnMouseExited(event -> {
            signOut.setStyle("-fx-background-color: cadetblue;-fx-background-insets: 3; -fx-background-radius: 5;");
        });

        refresh.setOnMouseEntered(event -> {
            refresh.setUnderline(true);
        });

        refresh.setOnMouseExited(event -> {
            refresh.setUnderline(false);
        });

    }

}
