package g.controllers;

import g.classes.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.*;


public class StudentMain {

    @FXML
    private Label allBooksLabel;

    @FXML
    private FlowPane flowPane;

    @FXML
    private MenuButton genreMenu;

    @FXML
    private Label historyLabel;

    @FXML
    private MenuButton nameMenu;

    @FXML
    private ImageView searchButton;

    @FXML
    private TextField searchField;

    @FXML
    private Label shelvesLabel;

    @FXML
    private MenuItem signOutMenu;

    @FXML
    private CheckBox subscribed;

    private Student student;

    public void initialize(){
        student = Proxy.getStudentInSystem();
        nameMenu.setText(student.getName() + " " + student.getSurname());
        subscribed.setSelected(student.isLibrarySubscribed());
        genreMenu();
        postBook(Proxy.getBooks().values());

        allBooksLabel.setOnMousePressed(event -> {
            clearPage();
            searchField.setText("");
            postBook(Proxy.getBooks().values());
        });

        signOutMenu.setOnAction(event ->{
            Instruments.loadPage("student-sign-in.fxml", "Student Sign In Page");
            Proxy.setStudentInSystem(null);
            searchButton.getScene().getWindow().hide();
        });

        subscribed.setOnAction(event ->{
            student.setDBLibrarySubscribed(subscribed.isSelected());
            if (Proxy.getSubscribers().getSubscribedStudents().contains(student)
                    && !subscribed.isSelected()){
                Proxy.getSubscribers().removeStudent(student);
            }
            else if (!Proxy.getSubscribers().getSubscribedStudents().contains(student)
                    && subscribed.isSelected()){
                Proxy.getSubscribers().addStudent(student);
            }
        });

        shelvesLabel.setOnMousePressed(event ->{
            clearPage();
            searchField.setText("");
            postReadingBook(Proxy.getStudentInSystem().getBookOrders().values());
        });

        historyLabel.setOnMousePressed(event -> {
            clearPage();
            searchField.setText("");
            ArrayList<Book> books = new ArrayList<>();
            for (History h: Proxy.getStudentInSystem().getHistories().values()) {
                books.add(Proxy.getBooks().get(h.getBookId()));
            }
            postBook(books);
        });

        searchButton.setOnMousePressed(mouseEvent -> {
            clearPage();
            Book cur = null;
            for (Book book: Proxy.getBooks().values()) {
                if(book.getName().equals(searchField.getText())){
                    cur = book;
                }
            }
            if(cur == null){
                Instruments.showAlert("Invalid name", "There is no such book!!!", Alert.AlertType.ERROR);
            }
            else {
                postBook(List.of(cur));
            }
        });



    }

    private void genreMenu() {
        genreMenu.getItems().clear();

        for (String genre : Proxy.getLibrary().getChildren().keySet()) {
            MenuItem genreItem = new MenuItem(genre);
            genreItem.setOnAction(event -> handleGenreSelection(genre));
            genreMenu.getItems().add(genreItem);
        }
    }

    private void handleGenreSelection(String genre) {
        searchField.setText("");
        BookClassify classify = (BookClassify) Proxy.getLibrary().getChildren().get(genre);
        List<Book> books = new ArrayList<>((Collection) classify.getChildren().values());
        clearPage();
        postBook(books);
    }

    private void postBook(Collection<Book> books) {
        try {
            for (Book book : books) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/g/book-cover.fxml"));
                VBox vBox = fxmlLoader.load();
                BookCover bookCover = fxmlLoader.getController();
                bookCover.setData(book);
                flowPane.getChildren().add(vBox);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void postReadingBook(Collection<BookOrder> bookOrders){
        try {
            for (BookOrder order : bookOrders) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/g/taken-book-cover.fxml"));
                VBox vBox = fxmlLoader.load();
                TakenBookCover bookCover = fxmlLoader.getController();
                bookCover.setData(order);
                flowPane.getChildren().add(vBox);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearPage(){
        flowPane.getChildren().clear();
    }
}
