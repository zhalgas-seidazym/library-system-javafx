package g.controllers;

import g.classes.Book;
import g.classes.ConnectToDB;
import g.classes.Instruments;
import g.classes.Proxy;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddBook {

    @FXML
    private Button add;

    @FXML
    private TextField amount;

    @FXML
    private TextField author;

    @FXML
    private Button back;

    @FXML
    private TextArea description;

    @FXML
    private TextField genre;

    @FXML
    private TextField name;

    private Book curBook = null;

    public void initialize(){
        back.setOnAction(actionEvent -> {
            back.getScene().getWindow().hide();
        });

        add.setOnAction(actionEvent -> {

            boolean ch = checkBookExists();
            if(ch){
                curBook.setDBAmount(curBook.getAmount() + Integer.parseInt(amount.getText()));
                curBook.setDBAvailable(curBook.getAvailable() + Integer.parseInt(amount.getText()));
                Instruments.showAlert("Success", "Existing books added successfully", Alert.AlertType.INFORMATION);
                add.getScene().getWindow().hide();
            }
            else {
                if("".equals(name.getText()) || "".equals(author.getText()) ||
                        "".equals(description.getText()) || "".equals(genre.getText()) ||
                        "".equals(amount.getText())
                ){
                    Instruments.showAlert("Invalid Inputs", "Enter correct inputs and try again!!!", Alert.AlertType.ERROR);
                }
                else {
                    Book book = new Book(Proxy.getMaxBookId() + 1, name.getText(), author.getText(),
                            genre.getText().split(","), description.getText(),
                            Integer.parseInt(amount.getText()), Integer.parseInt(amount.getText()));
                    addBookToDB(book);
                    Proxy.setMaxBookId(Proxy.getMaxBookId() + 1);
                    add.getScene().getWindow().hide();
                }
            }
        });
    }

    private void addBookToDB(Book book) {
        String query = "INSERT INTO Books (book_id, name, author, genre, description, amount, available) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            Connection connection = ConnectToDB.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, book.getBookId());
            preparedStatement.setString(2, book.getName());
            preparedStatement.setString(3, book.getAuthor());
            java.sql.Array sqlArray = connection.createArrayOf("VARCHAR", book.getGenre());
            preparedStatement.setArray(4, sqlArray);
            preparedStatement.setString(5, book.getDescription());
            preparedStatement.setInt(6, book.getAmount());
            preparedStatement.setInt(7, book.getAvailable());

            preparedStatement.executeUpdate();
            Proxy.getBooks().put(book.getBookId(), book);

            for (String str: book.getGenre()) {
                Proxy.getLibrary().getChildren().get(str).getChildren().put(book.getName(), book);
            }
            Proxy.getBooks().put(book.getBookId(), book);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean checkBookExists(){
        for (Book book: Proxy.getBooks().values()) {
            if(book.getName().equals(name.getText())){
                curBook = book;
                return true;
            }
        }
        return false;
    }
}
