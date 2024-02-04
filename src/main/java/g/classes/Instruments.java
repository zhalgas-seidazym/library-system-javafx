package g.classes;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;

public class Instruments {

    public static void loadPage(String fxml, String title) {
        try {
            URL url = Instruments.class.getResource("/g/"+fxml);

            Parent root = FXMLLoader.load(Objects.requireNonNull(url));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle(title);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static int sendVerificationCode(String toEmail) {
        return Message.sendVerificationCode(toEmail);
    }

    public static int sendMessage(String toEmail, String title, String mess) {
        return Message.sendMessage(toEmail, title, mess);
    }

    public static void executeUpdate(String query) {
        try {
            Connection connection = ConnectToDB.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(query);

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ResultSet executeQuery(String query) {
        ResultSet resultSet = null;
        try {
            Connection connection = ConnectToDB.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(query);

            resultSet = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

}
