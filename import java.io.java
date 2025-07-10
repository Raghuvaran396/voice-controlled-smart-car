import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class LoginLogoutTracker {
    private static final String LOG_FILE = "login_logout_log.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean loggedIn = false;
        String username = "";

        while (true) {
            System.out.println("\n1. Login\n2. Logout\n3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // consume newline

            switch (choice) {
                case 1:
                    if (loggedIn) {
                        System.out.println("Already logged in as " + username);
                    } else {
                        System.out.print("Enter username: ");
                        username = scanner.nextLine();
                        logEvent(username, "LOGIN");
                        loggedIn = true;
                    }
                    break;

                case 2:
                    if (loggedIn) {
                        logEvent(username, "LOGOUT");
                        loggedIn = false;
                        username = "";
                    } else {
                        System.out.println("No user currently logged in.");
                    }
                    break;

                case 3:
                    if (loggedIn) {
                        logEvent(username, "LOGOUT");  // auto logout on exit
                    }
                    System.out.println("Exiting...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void logEvent(String username, String eventType) {
        try (FileWriter fw = new FileWriter(LOG_FILE, true)) {
            LocalDateTime timestamp = LocalDateTime.now();
            String formattedTime = timestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            fw.write(String.format("%s - %s - %s\n", formattedTime, username, eventType));
            System.out.println(eventType + " recorded at " + formattedTime);
        } catch (IOException e) {
            System.out.println("Error writing to log file: " + e.getMessage());
        }
    }
}