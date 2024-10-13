package g;

import g.classes.Instruments;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        Instruments.loadPage("student-sign-in.fxml", "Student Sign In Page");
    }

}

