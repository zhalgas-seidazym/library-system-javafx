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

public class TakenAboutBook {

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
    private Text dates;

    @FXML
    private Text name;

    @FXML
    private Button returnBook;

    public void initialize(){
        returnBook.setOnMouseEntered(event ->{
            returnBook.setStyle("-fx-end-margin: 10; -fx-background-color: #368183; -fx-text-fill: azure;");
        });

        returnBook.setOnMouseExited(event ->{
            returnBook.setStyle("-fx-end-margin: 10; -fx-background-color: cadetblue; -fx-text-fill: azure;");
        });

    }

    public void showAboutBook(BookOrder order) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/g/taken-about-book.fxml"));

            Stage stage = new Stage();
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.setTitle("About Book");

            TakenAboutBook aboutBookController = fxmlLoader.getController();
            aboutBookController.setData(order);

            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setData(BookOrder order){
        Book book = Proxy.getBooks().get(order.getBookId());
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
        dates.setText(dates.getText() + order.getTakenDate() + " - " + order.getDueTo());
        returnBook.setStyle("-fx-end-margin: 10; -fx-start-margin: 10;  -fx-background-color: cadetblue; -fx-text-fill: azure;");


        returnBook.setOnAction(actionEvent -> {
            Proxy.getStudentInSystem().removeDBBookOrder(order);
            book.setDBAvailable(book.getAvailable() + 1);
            returnBook.getScene().getWindow().hide();
        });
    }
}
