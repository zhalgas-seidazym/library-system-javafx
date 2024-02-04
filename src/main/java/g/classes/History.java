package g.classes;

public class History {
    private int historyId;
    private int bookId;

    public History(int historyId, int bookId) {
        this.historyId = historyId;
        this.bookId = bookId;
    }

    public int getHistoryId() {
        return historyId;
    }

    public void setHistoryId(int historyId) {
        this.historyId = historyId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }
}
