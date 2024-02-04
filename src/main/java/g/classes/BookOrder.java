package g.classes;

import java.sql.Date;

public class BookOrder {
    private int orderId;
    private int bookId;
    private Date takenDate;
    private Date dueTo;

    public BookOrder(int bookOrderId, int bookId, Date takenDate, Date dueTo) {
        this.orderId = bookOrderId;
        this.bookId = bookId;
        this.takenDate = takenDate;
        this.dueTo = dueTo;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public Date getTakenDate() {
        return takenDate;
    }

    public void setTakenDate(Date takenDate) {
        this.takenDate = takenDate;
    }

    public Date getDueTo() {
        return dueTo;
    }

    public void setDueTo(Date dueTo) {
        this.dueTo = dueTo;
    }
}
