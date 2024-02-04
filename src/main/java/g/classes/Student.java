package g.classes;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class Student {
    private final int studentId;
    private final String name;
    private final String surname;
    private final String email;
    private String password;
    private boolean librarySubscribed;
    private HashMap<Integer,BookOrder> bookOrders;
    private HashMap<Integer,History> histories;

    public Student(int studentId, String name, String surname, String email, String password, boolean librarySubscribed) {
        this.studentId = studentId;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.librarySubscribed = librarySubscribed;
        this.email = email;
        bookOrders = new HashMap<>();
        histories = new HashMap<>();
    }

    public int getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void changePassword(String password){
        setPassword(password);
        String sql = "UPDATE Students " +
                "SET password = " + password +
                " WHERE student_id = " + getStudentId();
        Instruments.executeUpdate(sql);
    }

    public HashMap<Integer, BookOrder> getBookOrders() {
        return bookOrders;
    }

    public HashMap<Integer, History> getHistories() {
        return histories;
    }

    public boolean isLibrarySubscribed() {
        return librarySubscribed;
    }

    public void setLibrarySubscribed(boolean librarySubscribed) {
        this.librarySubscribed = librarySubscribed;
    }

    public void setDBLibrarySubscribed(boolean librarySubscribed){
        setLibrarySubscribed(librarySubscribed);
        String sql = "UPDATE Students " +
                "SET subscribed =  " + librarySubscribed +
                " WHERE student_id = " + getStudentId();
        Instruments.executeUpdate(sql);
    }

    public void addBookOrder(BookOrder bookOrder){
        bookOrders.put(bookOrder.getOrderId(), bookOrder);
    }

    public void addDBBookOrder(BookOrder bookOrder){
        addBookOrder(bookOrder);
        String order = "INSERT INTO BookOrders (order_id, student_id, book_id, taken_date, due_date) " +
                "VALUES (?, ?, ?, ?, ?)";

        try {
            Connection connection = ConnectToDB.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(order);

            preparedStatement.setInt(1, bookOrder.getOrderId());
            preparedStatement.setInt(2, getStudentId());
            preparedStatement.setInt(3, bookOrder.getBookId());
            preparedStatement.setDate(4, bookOrder.getTakenDate());
            preparedStatement.setDate(5, bookOrder.getDueTo());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeDBBookOrder(BookOrder bookOrder){
        bookOrders.remove(bookOrder.getOrderId());
        String sql = "DELETE FROM BookOrders " +
                "WHERE order_id = " + bookOrder.getOrderId();
        Instruments.executeUpdate(sql);
        History history = new History(Proxy.getMaxHistoryId() + 1, bookOrder.getBookId());
        Proxy.setMaxHistoryId(Proxy.getMaxHistoryId() + 1);
        addDBHistory(history);
    }

    public void addHistory(History history){
        histories.put(history.getHistoryId(), history);
    }

    public void addDBHistory(History history){
        addHistory(history);
        String sql = "INSERT INTO Histories (history_id, student_id, book_id) " +
                "VALUES (" + history.getHistoryId() + ", " + getStudentId() + ", " + history.getBookId() + ")";
        Instruments.executeUpdate(sql);
    }

    public String getOverdueBooksList() {
        String details = "You need to return books: \n";
        for (BookOrder od : getOverdueBookOrders()) {
            details += Proxy.getBooks().get(od.getBookId()).getName() + "\n";
        }
        return details.equals("You need to return books: \n") ? null : details;
    }


    public ArrayList<BookOrder> getOverdueBookOrders(){
        ArrayList<BookOrder> overdue = new ArrayList<>();
        for (BookOrder od: bookOrders.values()) {
            if(od.getDueTo().compareTo(Date.valueOf(LocalDate.now())) < 0){
                overdue.add(od);
            }
        }
        return overdue;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", librarySubscribed=" + librarySubscribed +
                ", bookOrders=" + bookOrders +
                ", histories=" + histories +
                '}';
    }
}
