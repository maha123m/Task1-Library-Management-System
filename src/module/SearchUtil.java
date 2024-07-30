package module;
import java.util.ArrayList;
import java.util.List;

public class SearchUtil {
    public static List<Book> searchBook(List<Book> catalog, int searchBy, String keyword) {
        List<Book> results = new ArrayList<>();
        keyword = keyword.trim().toLowerCase(); // Trim any leading or trailing spaces
        try {
            switch (searchBy) {
                case 1: // Search by book ID
                    int bookId = Integer.parseInt(keyword);
                    for (Book book : catalog) {
                        if (book.getId() == bookId) {
                            results.add(book);
                        }
                    }
                    break;
                case 2: // Search by book name
                    for (Book book : catalog) {
                        if (book.getBookName().equalsIgnoreCase(keyword)) {
                            results.add(book);
                        }
                    }
                    break;
                case 3: // Search by author name
                    for (Book book : catalog) {
                        if (book.getAuthor().getPersonName().equalsIgnoreCase(keyword)) {
                            results.add(book);
                        }
                    }
                    break;
                default:
                    System.out.println("Invalid search criteria.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input format for search.");
        }
        return results;
    }
}
