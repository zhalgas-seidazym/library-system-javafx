package g.classes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import static g.classes.Instruments.executeQuery;

public class Proxy {
    private static HashMap<Integer, Student> students;
    private static List<Manager> managers;
    private static Subscribers subscribers;
    private static Library library;
    private static HashMap<Integer, Book> books;
    private static Student studentInSystem;
    private static Manager managerInSystem;
    private static int maxStudentId = 0;
    private static int maxBookId = 0;
    private static int maxBookOrderId = 0;
    private static int maxHistoryId = 0;

    public static Student getStudentInSystem() {
        return studentInSystem;
    }

    public static void setStudentInSystem(Student studentInSystem) {
        Proxy.studentInSystem = studentInSystem;
    }

    public static Manager getManagerInSystem() {
        return managerInSystem;
    }

    public static void setManagerInSystem(Manager managerInSystem) {
        Proxy.managerInSystem = managerInSystem;
    }

    public static int getMaxStudentId() {
        if(maxStudentId == 0) getStudents();
        return maxStudentId;
    }

    public static void setMaxStudentId(int maxStudentId) {
        Proxy.maxStudentId = maxStudentId;
    }

    public static int getMaxBookId() {
        if(maxBookId == 0) getBooks();
        return maxBookId;
    }

    public static void setMaxBookId(int maxBookId) {
        Proxy.maxBookId = maxBookId;
    }

    public static int getMaxBookOrderId() {
        return maxBookOrderId;
    }

    public static void setMaxBookOrderId(int maxBookOrderId) {
        Proxy.maxBookOrderId = maxBookOrderId;
    }

    public static int getMaxHistoryId() {
        return maxHistoryId;
    }

    public static void setMaxHistoryId(int maxHistoryId) {
        Proxy.maxHistoryId = maxHistoryId;
    }

    public static HashMap<Integer,Student> getStudents() {
        if(students == null){
            students = DataFromDB.getStudents();
        }
        return students;
    }

    public static List<Manager> getManagers() {
        if(managers == null){
            managers = DataFromDB.getManagers();
        }
        return managers;
    }

    public static Subscribers getSubscribers() {
        if(subscribers == null){
            subscribers = new Subscribers();
            for (Student st: getStudents().values()) {
                if(st.isLibrarySubscribed()){
                    subscribers.addStudent(st);
                }
            }
        }
        return subscribers;
    }

    public static Library getLibrary() {
        library = new BookClassify("Library");
        for (Book book: getBooks().values()) {
            String[] arr = book.getGenre();
            for (String gnr: arr) {
                library.getChildren().computeIfAbsent(gnr, k -> new BookClassify(gnr))
                        .getChildren().put(book.getName(), book);
            }
        }

        return library;
    }

    public static HashMap<Integer, Book> getBooks() {
        if(books == null){
            books = DataFromDB.getBooks();
        }
        return books;
    }
}


class DataFromDB {

    static HashMap<Integer, Student> getStudents() {
        HashMap<Integer, Student> students = new HashMap<>();

        String query = "SELECT student_id, name, surname, email, subscribed, password FROM Students order by student_id";
        ResultSet stud = Instruments.executeQuery(query);

        try {
            while (stud.next()) {
                int studentId = stud.getInt("student_id");
                String name = stud.getString("name");
                String surname = stud.getString("surname");
                String email = stud.getString("email");
                boolean subscribed = stud.getBoolean("subscribed");
                String password = stud.getString("password");

                Student student = new Student(studentId, name, surname, email, password, subscribed);

                loadBookOrders(student, studentId);
                loadHistories(student, studentId);

                students.put(student.getStudentId(), student);
                Proxy.setMaxStudentId(studentId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return students;
    }

    private static void loadBookOrders(Student student, int studentId) {
        String subQuery = "select order_id, book_id, taken_date, due_date" +
                " from BookOrders where student_id = " + studentId + " order by order_id";
        ResultSet order = Instruments.executeQuery(subQuery);

        try {
            while (order.next()) {
                BookOrder bookOrder = new BookOrder(order.getInt("order_id"),
                        order.getInt("book_id"), order.getDate("taken_date"),
                        order.getDate("due_date"));
                student.addBookOrder(bookOrder);
                Proxy.setMaxBookOrderId(Math.max(bookOrder.getOrderId(), Proxy.getMaxBookOrderId()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void loadHistories(Student student, int studentId) {
        String subQuery = "select history_id, book_id from Histories where student_id = " + studentId + " order by history_id";
        ResultSet history = Instruments.executeQuery(subQuery);

        try {
            while (history.next()) {
                History history1 = new History(history.getInt("history_id"),
                        history.getInt("book_id"));
                student.addHistory(history1);
                Proxy.setMaxHistoryId(Math.max(history1.getHistoryId(), Proxy.getMaxHistoryId()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static List<Manager> getManagers() {
        List<Manager> managers = new ArrayList<>();

        String query = "SELECT manager_id, username, password FROM Managers order by manager_id";
        ResultSet managerResultSet = Instruments.executeQuery(query);

        try {
            while (managerResultSet.next()) {
                int managerId = managerResultSet.getInt("manager_id");
                String username = managerResultSet.getString("username");
                String password = managerResultSet.getString("password");

                Manager manager = new Manager(managerId, username, password);
                managers.add(manager);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return managers;
    }

    public static HashMap<Integer, Book> getBooks() {
        HashMap<Integer, Book> books = new HashMap<>();

        String query = "SELECT book_id, name, author, genre, description, amount, available FROM Books order by book_id";
        ResultSet bookResultSet = Instruments.executeQuery(query);

        try {
            while (bookResultSet.next()) {
                int bookId = bookResultSet.getInt("book_id");
                String bookName = bookResultSet.getString("name");
                String author = bookResultSet.getString("author");
                String[] genre = (String[]) bookResultSet.getArray("genre").getArray();
                String description = bookResultSet.getString("description");
                int amount = bookResultSet.getInt("amount");
                int available = bookResultSet.getInt("available");

                Book book = new Book(bookId, bookName, author, genre, description, amount, available);
                books.put(book.getBookId(), book);
                Proxy.setMaxBookId(bookId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
    }

}

