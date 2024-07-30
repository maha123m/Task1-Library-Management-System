package module;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Member memberManager = new Member(); // Create an instance of Member class for member operations
        Book bookManager = new Book(); // Create an instance of Book class for book operations
        Librarian librarian = new Librarian(); // Create an instance of Librarian for library operations
        ArrayList<Member> members = memberManager.displayMembers(); // Retrieves a list of members from a file named "members.txt" and stores it in an ArrayList named members
        ArrayList<Book> books = bookManager.displayBooks(); // Retrieves a list of books from a file named "books.txt" and stores it in an ArrayList named books
        Scanner reader = new Scanner(System.in);
        while (true) {
            System.out.println("-------------------------------------");
            System.out.println();
            System.out.println("Welcome to Library Management System");
            System.out.println("1. Librarian");
            System.out.println("2. Member");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int userType = getUserInput(reader, 1, 3); // Read user type input
            switch (userType) {
                case 1:
                    librarianMenu(reader, librarian, members, books);
                    break;
                case 2:
                    memberMenu(reader,memberManager,books);
                    break;
                case 3:
                    System.out.println("Exiting the system. Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Please enter a valid option");
            }
        }
    }

    // This Method to ensure user enter valid input when the user choice from menu
    private static int getUserInput(Scanner reader, int min, int max) {
        int input = -1;//to ensure the while loop runs at least once
        while (true) {
            try {
                input = reader.nextInt();
                if (input >= min && input <= max) {
                    break;
                } else {
                    System.out.println("Please enter a number between " + min + " and " + max);
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number between " + min + " and " + max);
                reader.next(); // Clear invalid input
            }
        }
        return input;
    }

    //this is the librarian menu
    private static void librarianMenu(Scanner reader, Librarian librarian, ArrayList<Member> members, ArrayList<Book> books) {
        while (true) {
            System.out.println("-------------------------------------");
            System.out.println();
            System.out.println("Librarian Menu");
            System.out.println("1. Display all Members");
            System.out.println("2. Display all books");
            System.out.println("3. Add Book ");
            System.out.println("4. Remove Book ");
            System.out.println("5. Search Book");
            System.out.println("6. Search Member");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");
            int option = getUserInput(reader, 1, 7); // Read user input
            try {
                switch (option) {
                    case 1:
                        printMemberList(members);
                        break;
                    case 2:
                        printBookList(books);
                        break;
                    case 3:
                        addNewBook(librarian, reader);
                        break;
                    case 4:
                        removeBook(librarian, reader);
                        break;
                    case 5:
                        searchBook(reader, books);
                        break;
                    case 6:
                        searchMember(reader, members, librarian);
                        break;
                    case 7:
                        return; // Return to main menu
                    default:
                        System.out.println("Please enter a valid option");
                }
            } catch (InputMismatchException e) {
                System.out.println("Enter a valid option");
            }
        }
    }

    //this is the member menu
    private static void memberMenu(Scanner reader,Member member, ArrayList<Book> books) {
        while (true) {
            System.out.println("-------------------------------------");
            System.out.println();
            System.out.println("Member Menu");
            System.out.println("1. Display all books");
            System.out.println("2. Search Book");
            System.out.println("3. Borrow Book");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int option = getUserInput(reader, 1, 4); // Read user input
            try {
                switch (option) {
                    case 1:
                        printBookList(books);
                        break;
                    case 2:
                        searchBook(reader, books);
                        break;
                    case 3:
                        reader.nextLine(); // Consume newline character
                        System.out.println("Enter the name of the book you want to borrow:");
                        String bookName = reader.nextLine();
                        member.borrowBook(bookName, books);
                        break;
                    case 4:
                        return; // Return to main menu
                    default:
                        System.out.println("Please enter a valid option");
                }
            } catch (InputMismatchException e) {
                System.out.println("Enter a valid option");
                reader.next(); // Clear invalid input
            }
        }
    }

    // Display all Members from members.txt
    private static void printMemberList(ArrayList<Member> members) {
        for (Member member : members) {
            System.out.println("Member Detail\nMember ID: " + member.getId() + "\nName: " + member.getPersonName() + "\nEmail: " + member.getEmail() + "\nPhone: " + member.getPhoneNumber() + "\nAddress: " + member.getAddress() + "\n");
        }
    }

    // Display all Books from books.txt
    private static void printBookList(ArrayList<Book> books) {
        for (Book book : books) {
            System.out.println("Book Detail\nBook ID: " + book.getId() +
                    "\nName: " + book.getBookName() +
                    "\nAuthor: " + book.getAuthor().getPersonName() +
                    "\nAvailability: " + (book.isAvailabilityStatus() ? "Available" : "Not Available") +
                    "\n");
        }
    }

    // Add New Book By the Librarian
    private static void addNewBook(Librarian librarian, Scanner reader) {
        reader.nextLine(); // nextLine():scans from the current position until it finds a line separator delimiter(Consume newline character )
        System.out.println("Enter Book Name:");
        String bookName = reader.nextLine();
        System.out.println("Enter Author Name:");
        String authorName = reader.nextLine();
        librarian.addNewBook(bookName, authorName);
    }

    // Method To remove Book From Library
    private static void removeBook(Librarian librarian, Scanner reader) {
        reader.nextLine(); // Consume newline character
        librarian.loadBooksFromFile(); // Ensure catalog is up-to-date
        System.out.println("Enter Book ID to remove:");
        int bookIdToRemove = reader.nextInt();
        librarian.removeBookFromCatalog(bookIdToRemove);
        // Save the updated catalog to the file
        librarian.saveBooksToFile(new ArrayList<>(librarian.getCatalog()));
        System.out.println("Book removed successfully.");
    }

    // This method to search for specific book using book Id or book name or book author
    private static void searchBook(Scanner reader, ArrayList<Book> books) {
        reader.nextLine(); // Consume newline character
        System.out.println("Search by:\n1. Book ID\n2. Book Name\n3. Author Name");
        System.out.print("Enter your choice: ");
        int searchBy = getUserInput(reader, 1, 3);
        reader.nextLine(); // Consume newline character
        System.out.println("Enter search keyword:");
        String keyword = reader.nextLine();
        List<Book> results = SearchUtil.searchBook(books, searchBy, keyword);
        if (results.isEmpty()) {
            System.out.println("No books found for the given criteria.");
        } else {
            for (Book book : results) {
                printBookDetails(book);
            }
        }
    }

    //This Method to print all details for specific book used in searchBook method
    private static void printBookDetails(Book book) {
        System.out.println("Book Detail");
        System.out.println("Book ID: " + book.getId());
        System.out.println("Book Name: " + book.getBookName());
        System.out.println("Author: " + book.getAuthor().getPersonName());
        System.out.println("Availability: " + (book.isAvailabilityStatus() ? "Available" : "Not Available"));
        System.out.println();
    }

    // This method to search for specific member using member id or member name
    private static void searchMember(Scanner reader, ArrayList<Member> members, Librarian librarian) {
        reader.nextLine(); // Consume newline character
        System.out.println("Search by:\n1. Member ID\n2. Member Name\n");
        System.out.print("Enter your choice: ");
        int searchBy = getUserInput(reader, 1, 2);
        reader.nextLine(); // Consume newline character
        if (searchBy == 1) {
            System.out.println("Enter Member ID:");
            int memberId = -1;
            while (true) {
                try {
                    memberId = Integer.parseInt(reader.nextLine());
                    if (memberId >= 1) {
                        break;
                    } else {
                        System.out.println("Invalid input. Please enter an integer value greater than or equal to 1.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter an integer value.");
                }
            }
            List<Member> results = librarian.searchMember(members, searchBy, String.valueOf(memberId));
            if (results.isEmpty()) {
                System.out.println("No member found with ID " + memberId + ".");
            } else {
                printMemberDetails(results.get(0)); // Assuming ID is unique, there should be only one result
            }
        } else if (searchBy == 2) {
            System.out.println("Enter Member Name:");
            String keyword = reader.nextLine();
            List<Member> results = librarian.searchMember(members, searchBy, keyword);
            if (results.isEmpty()) {
                System.out.println("No member found with name \"" + keyword + "\".");
            } else {
                for (Member member : results) {
                    printMemberDetails(member);
                }
            }
        }
    }

    //This Method to print all details for specific member used in searchMember method
    private static void printMemberDetails(Member member) {
        System.out.println("Member Detail");
        System.out.println("Member ID: " + member.getId());
        System.out.println("Name: " + member.getPersonName());
        System.out.println("Email: " + member.getEmail());
        System.out.println("Phone: " + member.getPhoneNumber());
        System.out.println("Address: " + member.getAddress());
        System.out.println();
    }
}






