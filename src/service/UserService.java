package service;
import dao.BookDAO;
import dao.TransactionDAO;
import java.util.Scanner;

public class UserService {
    TransactionDAO dao = new TransactionDAO();
    BookDAO bookDAO = new BookDAO();
    Scanner sc = new Scanner(System.in);
    public void issue(int uid, int bid) {
        try {
            if (!bookDAO.isBookExists(bid)) {
                System.out.println("Book Not Found");
                return;
            }
            if (!bookDAO.isBookAvailable(bid)) {
                System.out.println("Book Out of Stock");
                return;
            }
            dao.issueBook(uid, bid);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public void returnBook(int uid) {
        try {
            System.out.println("\nYOUR ISSUED BOOKS:");
            dao.showIssuedBooks(uid);

            System.out.print("\nEnter Book ID to Return: ");
            int bid = sc.nextInt();

            if (!dao.isBookIssued(uid, bid)) {
                System.out.println("This book is not issued by you");
                return;
            }
            double fine = dao.returnBook(uid, bid);
            System.out.println("Fine Amount: ₹" + fine);
            double remaining = fine;
            while (remaining > 0) {
                System.out.print("Enter Payment: ");
                double pay = sc.nextDouble();
                if (pay <= 0) {
                    System.out.println("Invalid amount");
                    continue;
                }
                remaining -= pay;
                if (remaining > 0) {
                    System.out.println("Remaining: ₹" + remaining);
                }
                if (remaining < 0) {
                    System.out.println("Refund: ₹" + Math.abs(remaining));
                }
            }
            System.out.println("Payment Completed");
            System.out.println("Book Returned Successfully");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
