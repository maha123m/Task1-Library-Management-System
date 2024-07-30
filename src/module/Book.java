package module;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Book extends AbstractBaseEntity {
    //encapsulation data
    private String bookName;
    private Author author;//composition
    private boolean availabilityStatus;

    //Default Constructor
    public Book(){
        super(0);
        this.availabilityStatus=false;
    }

    //Parameterize Constructor
    public Book(int Id,String bookName,Author author){
        super(Id);
        this.bookName = bookName.trim();
        this.author=author;
        this.availabilityStatus=false;
    }

    //Getters for class attribute
    public String getBookName() {
        return bookName;
    }

    public Author getAuthor() {
        return author;
    }

    public boolean isAvailabilityStatus() {
        return availabilityStatus;
    }

    //setters for class attribute
    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public void setAvailabilityStatus(boolean availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
    }

    public ArrayList<Book> displayBooks() {
        ArrayList<Book> books = new ArrayList<>();
        FileInputStream file = null;
        Scanner scan = null;
        try {
            File fileObj = new File("books.txt");
            if (!fileObj.exists()) {
                System.out.println("File not found at: " + fileObj.getAbsolutePath());
                return books;
            }
            file = new FileInputStream(fileObj);
            scan = new Scanner(file);
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                if (line.trim().isEmpty()) {
                    continue; // Skip empty lines
                }
                String[] data = line.split(",", 4); // Limit to 4 to include availability
                if (data.length < 4) {
                    System.err.println("Invalid line format: " + line);
                    continue; // Skip lines that don't have all required fields
                }
                int bookId = Integer.parseInt(data[0].trim());
                String bookName = data[1].trim();
                String bookAuthor = data[2].trim();
                boolean availability = Boolean.parseBoolean(data[3].trim()); // Assuming availability is boolean
                Author author = new Author();
                author.setPersonName(bookAuthor);
                Book book = new Book(bookId, bookName, author);
                book.setAvailabilityStatus(availability);
                books.add(book);
            }
        } catch (FileNotFoundException ex) {
            System.err.println("File not found: " + ex.getMessage());
        } catch (NumberFormatException ex) {
            System.err.println("Error parsing number: " + ex.getMessage());
        } finally {
            if (scan != null) {
                scan.close();
            }
            if (file != null) {
                try {
                    file.close();
                } catch (IOException ex) {
                    System.err.println("Error closing file: " + ex.getMessage());
                }
            }
        }
        return books;
    }

    // Override toString() method
    @Override
    public String toString() {
        return "module.Book{" +
                "bookName='" + bookName + '\'' +
                ", author=" + author +
                ", availabilityStatus=" + availabilityStatus +
                '}';
    }
}
