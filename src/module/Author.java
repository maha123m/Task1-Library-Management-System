package module;

import java.util.ArrayList;
import java.util.List;

public class Author extends Person {
    //encapsulation used (using private access modifiers)
    private List<Book> books;

    //Default Constructor
    public Author(){
        super();// Call superclass constructor
        this.books=new ArrayList<>();
    }

    // Parameterized Constructor
    public Author(int Id,String personName, String email, String phoneNumber, String address) {
        super(Id,personName, email, phoneNumber, address); // Call superclass constructor
        this.books = new ArrayList<>();
    }

    // Constructor with just personName
    public Author(String personName) {
        super(0, personName, "", "", ""); // Initialize with default values
        this.books = new ArrayList<>();
    }

    //Getters for class attribute
    public List<Book> getBooks() {
        return books;
    }

    //Setters for class attribute
    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "module.Author{" +
                "personName='" + getPersonName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", phoneNumber='" + getPhoneNumber() + '\'' +
                ", address='" + getAddress() + '\'' +
                ", books=" + books +
                '}';
    }
}
