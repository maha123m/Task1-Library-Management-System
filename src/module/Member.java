package module;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Member extends Person {
    //encapsulation used (using private access modifiers)
    private List<Book> borrowedBooks;
    private List<Member> members; // List to hold all members

    //Default Constructor
    public Member(){
        super(); // Call the superclass constructor
        this.borrowedBooks = new ArrayList<>();
        this.members = new ArrayList<>();
    }

    public Member(int id,String personName, String email, String phoneNumber, String address){
        super(id,personName, email, phoneNumber, address);// Call the superclass constructor
        this.borrowedBooks = new ArrayList<>();
        this.members = new ArrayList<>();
    }

    //Getters for class field
    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public List<Member> getMembers() {
        return members;
    }

    //Setters for class field
    public void setBorrowedBooks(List<Book> borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }

    public void borrowBook(String bookName, ArrayList<Book> books) {
        Book bookToBorrow = null;
        for (Book book : books) {
            if (book.getBookName().equalsIgnoreCase(bookName) && book.isAvailabilityStatus()) {
                bookToBorrow = book;
                break;
            }
        }

        if (bookToBorrow != null) {
            bookToBorrow.setAvailabilityStatus(false);
            System.out.println("Book borrowed successfully!");
            System.out.println("Book Name: " + bookToBorrow.getBookName());
            // Add code to log the borrowing details if necessary
        } else {
            System.out.println("The book is either not available or already borrowed.");
        }
    }

    // Method to read members from file
    public ArrayList<Member> displayMembers() {
        ArrayList<Member> members = new ArrayList<>();
        FileInputStream file = null;
        try {
            File fileObj = new File("members.txt");
            if (!fileObj.exists()) {
                System.out.println("File not found at: " + fileObj.getAbsolutePath());
                return members;
            }
            file = new FileInputStream(fileObj);
            Scanner scan = new Scanner(file);
            while (scan.hasNextLine()) {
                String[] data = scan.nextLine().split(",");
                int memberId = Integer.parseInt(data[0].trim());
                String personName = data[1].trim();
                String email = data[2].trim();
                String phoneNumber = data[3].trim();
                String address = data[4].trim();
                Member member = new Member(memberId, personName, email, phoneNumber, address);
                members.add(member);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Member.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (file != null) {
                try {
                    file.close();
                } catch (IOException ex) {
                    Logger.getLogger(Member.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return members;
    }
}
