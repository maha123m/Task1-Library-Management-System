package module;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Librarian extends Person {
    //encapsulation used (using private access modifiers)
    private List<Book> catalog; // Assuming a list of books in the library's catalog
    private List<Member> members;

    // Default Constructor
    public Librarian() {
        super(); // Call superclass constructor
        this.catalog = new ArrayList<>();
        this.members = new ArrayList<>();
    }

    // Parameterized Constructor
    public Librarian(int Id,String personName, String email, String phoneNumber, String address, String employeeID) {
        super(Id,personName, email, phoneNumber, address); // Call superclass constructor
        this.catalog = new ArrayList<>();
    }

    //Getter for class Field

    public List<Book> getCatalog() {
        return catalog;
    }
    public List<Member> getMembers() {
        return members;
    }

    //Setters for class field

    public void setCatalog(List<Book> catalog) {
        this.catalog = catalog;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }

    @Override
    public String toString() {
        return "module.Librarian{" +
                "personName='" + getPersonName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", phoneNumber='" + getPhoneNumber() + '\'' +
                ", address='" + getAddress() + '\'' +
                '}';
    }

    // Method to find the last bookId in the books.txt file
    private static int findLastBookId() throws IOException {
        int lastBookId = 0;
        FileInputStream fileInputStream = null;
        Scanner scanner = null;
        try {
            // Open the file for reading
            fileInputStream = new FileInputStream("books.txt");
            scanner = new Scanner(fileInputStream);
            // Read each line to find the last bookId
            while (scanner.hasNextLine()) {
                String[] data = scanner.nextLine().split(",", 4); // Limit to 4 to include availability
                lastBookId = Integer.parseInt(data[0].trim());
            }
        } finally {//finally:used to ensure resource are closed properly after the file operations complete
            // Close the scanner
            if (scanner != null) {
                scanner.close();
            }
            // Close the input stream
            if (fileInputStream != null) {
                fileInputStream.close();
            }
        }
        return lastBookId;
    }

    // Method to check if a book already exists in the catalog
    private boolean bookExistsInCatalog(String bookName, String authorName) {
        for (Book book : catalog) {
            if (book.getBookName().equalsIgnoreCase(bookName) && book.getAuthor().getPersonName().equalsIgnoreCase(authorName)) {
                return true;
            }
        }
        return false;
    }

    // Method to check if a book already exists in the file
    private static boolean bookExistsInFile(String bookName, String authorName) throws IOException {
        FileInputStream fileInputStream = null;
        Scanner scanner = null;
        try {
            // Open the file for reading
            fileInputStream = new FileInputStream("books.txt");
            scanner = new Scanner(fileInputStream);
            // Read each line to check if the book exists
            while (scanner.hasNextLine()) {
                String[] data = scanner.nextLine().split(",", 4); // Limit to 4 to include availability
                if (data[1].trim().equalsIgnoreCase(bookName) && data[2].trim().equalsIgnoreCase(authorName)) {
                    return true;
                }
            }
        } finally {
            // Close the scanner
            if (scanner != null) {
                scanner.close();
            }
            // Close the input stream
            if (fileInputStream != null) {
                fileInputStream.close();
            }
        }
        return false;
    }

    // Method to Add New Book
    public void addNewBook(String bookName, String authorName) {
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;
        try {
            // Open the file in append mode create it if it doesn't exist
            File fileObj = new File("books.txt");
            if (!fileObj.exists()) {
                fileObj.createNewFile();
            }
            // Check if the book already exists in the catalog
            if (bookExistsInFile(bookName, authorName)) {
                System.out.println("The book \"" + bookName + "\" by " + authorName + " already exists in the catalog.");
                return;
            }
            fileWriter = new FileWriter(fileObj, true); // Append mode
            bufferedWriter = new BufferedWriter(fileWriter);
            // Find the last bookId in the file
            int lastBookId = findLastBookId();
            // Generate the new bookId
            int newBookId = lastBookId + 1;
            // Create the new book entry
            String newBookEntry = newBookId + ", " + bookName + ", " + authorName + ", true";
            // Write the new book entry to the file
            bufferedWriter.newLine();
            bufferedWriter.write(newBookEntry);
            // Success message
            System.out.println("Book added successfully:");
            System.out.println(newBookEntry);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // Close the writers
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
                if (fileWriter != null) {
                    fileWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Method to load books from file into catalog
    public void loadBooksFromFile() {
        FileInputStream fileInputStream = null;
        Scanner scanner = null;
        try {
            catalog.clear(); // Clear existing catalog
            File fileObj = new File("books.txt");
            if (!fileObj.exists()) {
                System.out.println("File not found at: " + fileObj.getAbsolutePath());
                return;
            }
            fileInputStream = new FileInputStream(fileObj);
            scanner = new Scanner(fileInputStream);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
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
                boolean availability = Boolean.parseBoolean(data[3].trim());
                Author author = new Author();
                author.setPersonName(bookAuthor);
                Book book = new Book(bookId, bookName, author);
                book.setAvailabilityStatus(availability);
                catalog.add(book);
            }
        } catch (IOException ex) {
            System.err.println("Error reading file: " + ex.getMessage());
        } finally {
            if (scanner != null) {
                scanner.close();
            }
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException ex) {
                    System.err.println("Error closing file: " + ex.getMessage());
                }
            }
        }
    }

    // Method to remove a book from the catalog based on its ID
    public void removeBookFromCatalog(int bookId) {
        Book bookToRemove = null;
        for (Book book : catalog) {
            if (book.getId() == bookId) {
                bookToRemove = book;
                break;
            }
        }
        if (bookToRemove != null) {
            catalog.remove(bookToRemove);
            System.out.println("Book with ID " + bookId + " removed from the catalog.");
        } else {
            System.out.println("Book with ID " + bookId + " not found in the catalog.");
        }
    }

    // Method to search for member
    public List<Member> searchMember(List<Member> members, int searchBy, String keyword) {
        List<Member> results = new ArrayList<>();
        keyword = keyword.trim().toLowerCase(); // Trim any leading or trailing spaces
        switch (searchBy) {
            case 1: // Search by member ID
                for (Member member : members) {
                    if (member.getId() == Integer.parseInt(keyword)) {
                        results.add(member);
                    }
                }
                break;
            case 2: // Search by member name
                for (Member member : members) {
                    if (member.getPersonName().equalsIgnoreCase(keyword)) {
                        results.add(member);
                    }
                }
                break;
            default:
                System.out.println("Invalid search criteria.");
        }
        return results;
    }

    public void saveBooksToFile(ArrayList<Book> books) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("books.txt"))) {
            for (Book book : books) {
                writer.write(book.getId() + "," + book.getBookName() + "," + book.getAuthor().getPersonName() + "," + book.isAvailabilityStatus());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("An error occurred while saving books: " + e.getMessage());
        }
    }
}











