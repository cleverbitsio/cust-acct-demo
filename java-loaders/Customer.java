import java.io.Serializable;
import java.util.ArrayList;

public class Customer implements Serializable{

    private static final long serialVersionUID = 1L;
    private String groupID;
    private String firstName, lastName;
    private ArrayList<String> emails = new ArrayList<>();
    private ArrayList<Integer> accounts = new ArrayList<>();

    public Customer(String groupID, String firstName, String lastName,
            ArrayList<String> emails, ArrayList<Integer> accounts) {
        this.groupID = groupID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emails = emails;
        this.accounts = accounts;
    }
    
    public Customer(String groupID, String firstName, String lastName, String [] emails, int [] accounts) {
        this.groupID = groupID;
        this.firstName = firstName;
        this.lastName = lastName;
        for (String email : emails) {
            this.emails.add(email);
        }
        for (int account : accounts) {
            this.accounts.add(account);
        }
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public ArrayList<String> getEmails() {
        return emails;
    }

    public void setEmails(ArrayList<String> emails) {
        this.emails = emails;
    }

    public ArrayList<Integer> getAccounts() {
        return accounts;
    }

    public void setAccounts(ArrayList<Integer> accounts) {
        this.accounts = accounts;
    }

//    public String getFirstName() {
//        return firstName;
//    }
//
//    public void setFirstName(String firstName) {
//        this.firstName = firstName;
//    }
//
//    public String getLastName() {
//        return lastName;
//    }
//
//    public void setLastName(String lastName) {
//        this.lastName = lastName;
//    }

}