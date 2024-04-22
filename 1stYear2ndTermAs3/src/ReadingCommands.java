import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
public class ReadingCommands {
    public ReadingCommands() {}
    FileIO ioObj = new FileIO();
    Members members = new Members();
    Books books = new Books();
    public void borrowingOperation(String bookId, String memberId, String date, String out) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(date, formatter);
        int indexM = Integer.parseInt(memberId)-1;
        int indexB = Integer.parseInt(bookId)-1;
        if (members.getMembersList().get(indexM).getMemberType().equals("S")) {
            if (members.getMembersList().get(indexM).getBorrowed().size() == 2) {
                ioObj.writeToFile(out,"You have exceeded the borrowing limit!",true,true);
            }else{
                books.getBorrowedBooks().add(bookId);
                books.getBooksList().get(indexB).setDeadline(localDate.plusDays(7));
                books.getBooksList().get(indexB).setBorrowTime(localDate);
                members.getMembersList().get(indexM).getBorrowed().add(books.getBooksList().get(indexB));
                members.getMembersList().get(indexM).getExtendableBookIds().add(bookId);
                ioObj.writeToFile(out,String.format("The book [%s] was borrowed by member [%s] at "+date,bookId,memberId),true,true);
            }
        }
        if (members.getMembersList().get(indexM).getMemberType().equals("A")) {
            if (members.getMembersList().get(indexM).getBorrowed().size() == 4) {
                ioObj.writeToFile(out,"You have exceeded the borrowing limit!",true,true);
            } else {
                books.getBorrowedBooks().add(bookId);
                books.getBooksList().get(indexB).setDeadline(localDate.plusDays(14));
                members.getMembersList().get(indexM).getBorrowed().add(books.getBooksList().get(indexB));
                members.getMembersList().get(indexM).getExtendableBookIds().add(bookId);
                books.getBooksList().get(indexB).setBorrowTime(localDate);
                ioObj.writeToFile(out,String.format("The book [%s] was borrowed by member [%s] at "+date,bookId,memberId),true,true);
            }
        }
    }
    public void init(String in,String out) {
        String[] lines = ioObj.readFile(in, true, true);
        int bookCounter = 0;
        int memberCounter = 0;
        int studentNumber=0;int academicNumber=0;int printedNumber=0;int handwrittenNumber=0;
        for (String line : lines) {
            String[] args = line.split("\t");
            String command = args[0];
            switch (command) {
                case "addBook":
                    bookCounter = bookCounter + 1;
                    String bookId = String.valueOf(bookCounter);
                    String bookType = args[1];
                    books.getBooksList().add(new Books(bookId, bookType, null));
                    if(bookType.equals("P")){
                        ioObj.writeToFile(out,String.format("Created new book: Printed [id: %s]",bookId),true,true);
                        printedNumber++;
                    }
                    else {
                        ioObj.writeToFile(out,String.format("Created new book: Handwritten [id: %s]",bookId),true,true);
                        handwrittenNumber++;
                    }
                    break;

                case "addMember":
                    memberCounter = memberCounter + 1;
                    String memberId = String.valueOf(memberCounter);
                    String memberType = args[1];
                    members.getMembersList().add(new Members(memberId, memberType));
                    if(memberType.equals("S")){
                        ioObj.writeToFile(out,String.format("Created new member: Student [id: %s]",memberId),true,true);
                        studentNumber++;
                    }
                    else {
                        ioObj.writeToFile(out,String.format("Created new member: Academic [id: %s]",memberId),true,true);
                        academicNumber++;
                    }
                    break;

                case "borrowBook":
                    int indexBook= Integer.parseInt(args[1])-1;

                    if (books.getBooksList().get(indexBook).getBookType().equals("H")){
                        ioObj.writeToFile(out,"You cannot borrow this book!",true,true);
                    } else {
                        if (books.getBorrowedBooks().contains(args[1])) {
                            ioObj.writeToFile(out, "You cannot borrow this book!", true, true);
                        } else {
                            borrowingOperation(args[1], args[2], args[3],out);
                        }
                    }
                    break;

                case "returnBook":

                    int indexB= Integer.parseInt(args[1])-1;
                    int indexM= Integer.parseInt(args[2])-1;

                    if(books.getBorrowedBooks().contains(args[1])){
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                        LocalDate localDate = LocalDate.parse(args[3], formatter);
                        LocalDate deadline = books.getBooksList().get(indexB).getDeadline();

                        if(localDate.isAfter(deadline)){

                            Period period = Period.between(deadline, localDate);
                            int fee = period.getDays();
                            String msg =String.format("The book [%s] was returned by member [%s] at "+args[3]+" Fee: %d",args[1],args[2],fee);
                            ioObj.writeToFile(out,msg,true, true);
                        }
                        else {
                            String msg =String.format("The book [%s] was returned by member [%s] at "+args[3]+ " Fee: 0",args[1],args[2]);
                            ioObj.writeToFile(out,msg,true, true);

                        }
                        books.getBorrowedBooks().remove(args[1]);
                        books.getBooksList().get(indexB).setDeadline(null);
                        books.getBooksList().get(indexB).setBorrowTime(null);
                        members.getMembersList().get(indexM).getBorrowed().remove(books.getBooksList().get(indexB));
                        members.getMembersList().get(indexM).getExtendableBookIds().remove(args[1]);
                    }
                    else {
                        books.getReadInLib().remove(args[1]);
                        books.getBooksList().get(indexB).setBorrowTime(null);
                        members.getMembersList().get(indexM).getReadLibrary().remove(books.getBooksList().get(indexB));
                        String msg =String.format("The book [%s] was returned by member [%s] at "+args[3]+ " Fee: 0",args[1],args[2]);
                        ioObj.writeToFile(out,msg,true, true);
                    }
                    break;

                case "extendBook":
                    int indexB2= Integer.parseInt(args[1])-1;
                    int indexM2= Integer.parseInt(args[2])-1;
                    if(!members.getMembersList().get(indexM2).getExtendableBookIds().contains(args[1])){
                        ioObj.writeToFile(out,"You cannot extend the deadline!",true, true);
                    }
                    else {
                        members.getMembersList().get(indexM2).getExtendableBookIds().remove(args[1]);
                        LocalDate current = books.getBooksList().get(indexB2).getDeadline();
                        if (current.isAfter(books.getBooksList().get(indexB2).getDeadline())) {
                            ioObj.writeToFile(out,"You cannot extend the deadline!",true, true);
                        } else {
                            if (members.getMembersList().get(indexM2).getMemberType().equals("S")) {
                                books.getBooksList().get(indexB2).setDeadline(current.plusDays(7));

                                ioObj.writeToFile(out,String.format("The deadline of book [%s] was extended by member [%s] at "+args[3],args[1],args[2]),true, true);

                                ioObj.writeToFile(out,String.format("New deadline of book [%s] is "+books.getBooksList().get(indexB2).getDeadline(),args[1]),true, true);

                            } else {
                                books.getBooksList().get(indexB2).setDeadline(current.plusDays(14));
                                ioObj.writeToFile(out,String.format("New deadline of book [%s] is "+books.getBooksList().get(indexB2).getDeadline(),args[1]),true, true);
                            }
                        }
                    }
                    break;

                case "readInLibrary":
                    int indexB3= Integer.parseInt(args[1])-1;
                    int indexM3= Integer.parseInt(args[2])-1;
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate localDate = LocalDate.parse(args[3], formatter);
                    if(books.getBorrowedBooks().contains(args[1])|| books.getReadInLib().contains(args[1])){
                        ioObj.writeToFile(out,"You can not read this book!",true, true);
                    } else {
                        if (members.getMembersList().get(indexM3).getMemberType().equals("S") && books.getBooksList().get(indexB3).getBookType().equals("H")) {
                            ioObj.writeToFile(out,"Students can not read handwritten books!",true, true);
                        } else {
                            books.getReadInLib().add(args[1]);
                            members.getMembersList().get(indexM3).getReadLibrary().add(books.getBooksList().get(indexB3));
                            books.getBooksList().get(indexB3).setBorrowTime(localDate);
                            ioObj.writeToFile(out,String.format("The book [%s] was read in library by member [%s] at "+args[3],args[1],args[2]),true, true);
                        }
                    }
                    break;

                case "getTheHistory":
                    ioObj.writeToFile(out,"History of library:\n",true, true);
                    ioObj.writeToFile(out,String.format("Number of students: %d",studentNumber),true, true);
                    for(Members member: members.getMembersList()){
                        if(member.getMemberType().equals("S")){
                            ioObj.writeToFile(out,String.format("Student [id: %s]",member.getId()),true, true);
                        }
                    }
                    ioObj.writeToFile(out,String.format("\nNumber of academics: %d",academicNumber),true, true);
                    for(Members member: members.getMembersList()){
                        if(member.getMemberType().equals("A")){
                            ioObj.writeToFile(out,String.format("Academic [id: %s]",member.getId()),true, true);
                        }
                    }
                    ioObj.writeToFile(out,String.format("\nNumber of printed books: %d",printedNumber),true, true);
                    for(Books book: books.getBooksList()){
                        if(book.getBookType().equals("P")){
                            ioObj.writeToFile(out,String.format("Printed [id: %s]",book.getId()),true, true);
                        }
                    }
                    ioObj.writeToFile(out,String.format("\nNumber of handwritten books: %d",handwrittenNumber),true, true);
                    for(Books book: books.getBooksList()){
                        if(book.getBookType().equals("H")){
                            ioObj.writeToFile(out,String.format("Handwritten [id: %s]",book.getId()),true, true);
                        }
                    }

                    ioObj.writeToFile(out,String.format("\nNumber of borrowed books: %d",books.getBorrowedBooks().size()),true, true);
                    for(String bookID: books.getBorrowedBooks()){
                        for(Members member : members.getMembersList()){
                            for(Books book : member.getBorrowed()){
                                if(book.getId().equals(bookID)){
                                    LocalDate borrowDate= book.getBorrowTime();
                                    String memberID= String.valueOf(members.getMembersList().indexOf(member)+1);
                                    ioObj.writeToFile(out,String.format("The book [%s] was borrowed by member [%s] at "+borrowDate,bookID,memberID),true, true);
                                }
                            }
                        }
                    }

                    ioObj.writeToFile(out,String.format("\nNumber of books read in library: %d",books.getReadInLib().size()),true, true);
                    for(String bookID: books.getReadInLib()){
                        for(Members member : members.getMembersList()){
                            for(Books book : member.getReadLibrary()){
                                if(book.getId().equals(bookID)){
                                    LocalDate borrowDate= book.getBorrowTime();
                                    String memberID= String.valueOf(members.getMembersList().indexOf(member)+1);
                                    ioObj.writeToFile(out,String.format("The book [%s] was read in library by member [%s] at "+borrowDate,bookID,memberID),true, true);
                                }
                            }
                        }
                    }
            }
        }
    }
}
