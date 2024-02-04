package g.controllers;

import g.classes.Book;
import g.classes.BookOrder;
import g.classes.Instruments;
import g.classes.Proxy;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

public class AboutBook {

    @FXML
    private Text author;

    @FXML
    private Text available;

    @FXML
    private Text description;

    @FXML
    private Text genre;

    @FXML
    private ImageView img;

    @FXML
    private Text name;

    @FXML
    private Button take;

    public void initialize(){
        take.setOnMouseEntered(event ->{
            take.setStyle("-fx-end-margin: 10; -fx-background-color: #368183; -fx-text-fill: azure;");
        });

        take.setOnMouseExited(event ->{
            take.setStyle("-fx-end-margin: 10; -fx-background-color: cadetblue; -fx-text-fill: azure;");
        });

    }

    public void showAboutBook(Book book) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/g/about-book.fxml"));

            Stage stage = new Stage();
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.setTitle("About Book");

            AboutBook aboutBookController = fxmlLoader.getController();
            aboutBookController.setData(book);

            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setData(Book book){
        Image image = new Image(getClass().getResourceAsStream("/g/Images/" + book.getName() + ".png"));
        img.setImage(image);
        name.setText(name.getText() + book.getName());
        author.setText(author.getText() + book.getAuthor());
        for(int i = 0; i < book.getGenre().length; i++){
            if(i == book.getGenre().length - 1) genre.setText(genre.getText() + book.getGenre()[i]);
            else genre.setText(genre.getText() + book.getGenre()[i] + ", ");
        }
        description.setText(description.getText() + book.getDescription());
        available.setText(available.getText() + book.getAvailable());
        take.setStyle("-fx-end-margin: 10; -fx-start-margin: 10;  -fx-background-color: cadetblue; -fx-text-fill: azure;");


        take.setOnAction(actionEvent -> {
            if(book.getAvailable() > 0){
                BookOrder curOrder = new BookOrder(Proxy.getMaxBookOrderId() + 1, book.getBookId(),
                        Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now().plusWeeks(1)));
                Proxy.getStudentInSystem().addDBBookOrder(curOrder);
                Proxy.setMaxBookOrderId(Proxy.getMaxBookOrderId() + 1);
                book.setDBAvailable(book.getAvailable() - 1);
                take.getScene().getWindow().hide();
            }
            else {
                Instruments.showAlert("Available error", "There is no such available book!!!", Alert.AlertType.ERROR);
            }
        });
    }
}
