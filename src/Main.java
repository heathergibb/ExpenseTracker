import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Create a scanner object to read user input from the console
        Scanner scanner = new Scanner(System.in);
        TransactionManager transactionManager = new TransactionManager();
        
        int choice;
        // Loop until the user chooses to exit
        do {
            choice = displayMainMenu(scanner);
            handleUserChoice(choice, scanner, transactionManager);
        } while (choice != 5); // Continue until the user selects option 5 to exit

        // Close the scanner to prevent resource leaks
        scanner.close();
    }

    private static int displayMainMenu(Scanner scanner) {  
        // Clear the screen
        clearScreen();

        // Display the main menu
        System.out.println("EXPENSE TRACKER");
        System.out.println();
        System.out.println("Main Menu");
        System.out.println("--------------------");
        System.out.println("1. Add Expense");
        System.out.println("2. Add Income");    
        System.out.println("3. View Transactions");
        System.out.println("4. View Summary Report");
        System.out.println("5. Exit");
        System.out.println("--------------------");
        // Prompt the user to select an option
        System.out.print("Please select an option (1-5): ");
    
        int choice = 0; // Initialize with an invalid value
        // Ensure the user inputs a valid integer
        do {
            if (!scanner.hasNextInt()) { // Check if the next input is an integer
                System.out.print("Invalid input. Please enter a number: ");
                scanner.next(); // Clear the invalid input
            } else { // If the input is an integer, read it
                choice = scanner.nextInt();
                if (choice < 1 || choice > 5) {
                    System.out.print("Invalid choice. Please select a number between 1 and 5: ");
                }
            }
        } while (choice < 1 || choice > 5); // Repeat until a valid choice is made

        // Return the user's valid choice 
        return choice;
    }

    private static void handleUserChoice(int choice, Scanner scanner, TransactionManager transactionManager) {
        // Handle the user's choice based on the menu selection
        switch (choice) {
            case 1:
                // Call method to add an expense
                System.out.println("Adding Expense...");
                break;
            case 2:
                // Call method to add income
                System.out.println("Adding Income...");
                break;
            case 3:
                // Call method to view transactions
                clearScreen();
                transactionManager.viewTransactions();
                scanner.nextLine(); // Consume the newline character left by nextInt()
                System.out.println("\nPress Enter to continue...");
                scanner.nextLine(); // Wait for user input to continue
                break;
            case 4:
                // Call method to view summary report
                System.out.println("Viewing Summary Report...");
                break;
            case 5:
                // Exit the application
                System.out.println("\nExiting the application. Goodbye!");
                break;
        }
    }

    public static void clearScreen() {
        // Clear the console screen
        System.out.print("\033[H\033[2J"); // \033[H moves the cursor to the top left corner, \033[2J clears the screen
        System.out.flush();
    }
}
