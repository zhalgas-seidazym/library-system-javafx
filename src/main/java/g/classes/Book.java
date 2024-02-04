package g.classes;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Book implements Library {
    private int bookId;
    private String bookName;
    private String author;
    private String[] genre;
    private String description;
    private int amount;
    private int available;

    public Book(int bookId, String bookName, String author, String[] genre, String description, int amount, int available) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.genre = genre;
        this.author = author;
        this.description = description;
        this.amount = amount;
        this.available = available;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getName() {
        return bookName;
    }

    public void setName(String bookName) {
        this.bookName = bookName;
    }

    public String[] getGenre() {
        return genre;
    }

    public void setGenre(String[] genre) {
        this.genre = genre;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getAmount() {
        return amount;
    }

    public void setDBAmount(int amount) {
        this.amount = amount;
        String sql = "update Books " +
                "set amount = " + amount + " where book_id = " + bookId;
        Instruments.executeUpdate(sql);
    }

    public int getAvailable() {
        return available;
    }

    public void setDBAvailable(int available) {
        this.available = available;
        String sql = "update Books " +
                "set available = " + available + " where book_id = " + bookId;
        Instruments.executeUpdate(sql);
    }


    @Override
    public void add(String str, Library library) {
    }

    @Override
    public HashMap<String, Library> getChildren() {
        return null;
    }
}
