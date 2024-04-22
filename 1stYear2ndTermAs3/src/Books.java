import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Books {
    private String id;
    private String bookType;
    private LocalDate deadline = null;
    private LocalDate borrowTime =null;
    private List<Books> booksList= new ArrayList<>();

    private List<String> borrowedBooks = new ArrayList<>();
    private List<String> readInLib= new ArrayList<>();

    public Books() {
    }
    public Books(String id, String bookType, LocalDate deadline){
        this.id= id ;
        this.bookType=bookType;
        this.deadline=deadline ;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public LocalDate getBorrowTime() {
        return borrowTime;
    }

    public void setBorrowTime(LocalDate borrowTime) {
        this.borrowTime = borrowTime;
    }

    public String getId() {
        return id;
    }

    public String getBookType() {
        return bookType;
    }

    public List<Books> getBooksList() {
        return booksList;
    }

    public List<String> getBorrowedBooks() {
        return borrowedBooks;
    }

    public List<String> getReadInLib() {
        return readInLib;
    }

}
