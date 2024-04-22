import java.util.ArrayList;
import java.util.List;

public class Members {
    private String id;
    private String memberType;
    private List<Members> membersList = new ArrayList<>();
    private List<Books> borrowed = new ArrayList<>();
    private List<Books> readLibrary = new ArrayList<>();
    private List<String> extendableBookIds= new ArrayList<>();

    public Members(){

    }
    public Members(String id,String memberType){
        this.id=id;
        this.memberType= memberType;
    }

    public List<Books> getReadLibrary() {
        return readLibrary;
    }

    public List<Members> getMembersList() {
        return membersList;
    }


    public String getMemberType() {
        return memberType;
    }


    public String getId() {
        return id;
    }


    public List<Books> getBorrowed() {
        return borrowed;
    }


    public List<String> getExtendableBookIds() {
        return extendableBookIds;
    }

}
