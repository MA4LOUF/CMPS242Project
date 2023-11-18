import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserService userService = new UserService();

        System.out.print("Do you want to Register (R) or Login (L)? ");
        String choice = scanner.nextLine();

        if (choice.equalsIgnoreCase("R")) {
            System.out.print("Enter your email: ");
            String email = scanner.nextLine();
            System.out.print("Enter a password: ");
            String password = scanner.nextLine();
            System.out.print("Enter a username: ");
            String username = scanner.nextLine();
            try {
                userService.registerUser(email, password, username);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else if (choice.equalsIgnoreCase("L")) {
            System.out.print("Enter your email: ");
            String email = scanner.nextLine();
            System.out.print("Enter a password: ");
            String password = scanner.nextLine();
            try {
                boolean loggedIn = userService.loginUser(email, password);
                if(loggedIn)
                    System.out.println("Welcome to Y");
                else
                    System.out.println("Login failed");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else {
            System.out.println("Invalid choice");
        }
    }
}