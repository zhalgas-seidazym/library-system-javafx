package g.controllers;

import g.classes.Book;
import g.classes.Instruments;
import g.classes.Proxy;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class BookCover {

    @FXML
    private Label author;

    @FXML
    private VBox cover;

    @FXML
    private ImageView img;

    @FXML
    private Label name;

    private final String[] colors = new String[]{"cadetblue", "#008B8B", "LIGHTSEAGREEN", "MEDIUMTURQUOISE", "TEAL"};

    private Book curBook = null;

    public void setData(Book book){
        Image image = new Image(getClass().getResourceAsStream("/g/Images/" + book.getName() + ".png"));
        author.setText(book.getAuthor());
        img.setImage(image);
        name.setText(book.getName());
        curBook = book;
        setStyles();

        name.setOnMouseEntered(event -> {
            name.setUnderline(true);
            name.setStyle("-fx-text-fill: #fda743");
        });

        name.setOnMouseExited(mouseEvent -> {
            name.setStyle("-fx-text-fill: azure;");
            name.setUnderline(false);
        });

        name.setOnMousePressed(event ->{
            new AboutBook().showAboutBook(curBook);
        });
    }

    public void setStyles(){
        name.setStyle("-fx-text-fill: azure;");
        author.setStyle("-fx-text-fill: azure;");
        String color = colors[(int) (Math.random() * colors.length)];
        cover.setStyle("-fx-background-color: " + color + "; -fx-background-radius: 10");
    }

}
