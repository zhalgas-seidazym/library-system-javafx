package g;

import g.classes.Book;
import g.classes.ConnectToDB;
import g.classes.Instruments;
import g.controllers.AboutBook;
import g.controllers.StudentSignUp;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.ConnectException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;

public class Main extends Application {

    @Override
    public void start(Stage stage){
        Instruments.loadPage("student-sign-in.fxml", "Student Sign In Page");
    }

}

