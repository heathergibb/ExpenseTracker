import java.math.BigDecimal;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        /* Call functions to display the menu and handle the user's choice */
        
        String filePath = getDefaultFilePath(); // Get the default file path for transactions csv file
        
        TransactionManager transactionManager = new TransactionManager(filePath); // Create an instance of TransactionManager to manage transactions
        Scanner scanner = new Scanner(System.in); // Create a scanner object to read user input from the console
        int choice;

        do {
            displayMainMenu(); // Display the main menu
            choice = InputHelper.getValidatedMenuChoice(scanner, 1, 6); // get the user's choice
            handleUserChoice(choice, scanner, transactionManager); // Handle the user's choice based on the menu selection
        } while (choice != 6); // Continue until the user selects option 6 to exit

        scanner.close(); // Close the scanner to prevent resource leaks
    }

    private static String getDefaultFilePath() {
        /* Get the default file path for transactions csv file
         * The default file path is in .../ExpenseTracker/data/default.csv
         */
        
        String currentDir = System.getProperty("user.dir"); // Get the current working directory
        
        // if the current working directory is /src, then strip the src from the path
        if (currentDir.endsWith("src")) {
            currentDir = currentDir.substring(0, currentDir.length() - 3); // Remove the last 4 characters i.e. /src
        }

        return Paths.get(currentDir, "data", "default.csv").toString(); // Construct the default file path
    }
    private static void displayMainMenu() {  
        /* Display the Main Menu */

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
        System.out.println("5. View Detailed Report");
        System.out.println("6. Exit");
        System.out.println("--------------------");
    }

    private static void handleUserChoice(int choice, Scanner scanner, TransactionManager transactionManager) {
        /* Handle the user's choice based on the menu selection */
        
        switch (choice) {
            case 1: // Add Expense
                clearScreen();
                System.out.println("ADD EXPENSE");
                System.out.println("------------------------------------------------------");
                LocalDate date = InputHelper.promptForDate(scanner, "Enter the expense date (YYYY-MM-DD): "); // Prompt for the date
                String description = InputHelper.promptForDescription(scanner, "Enter the description: ", 80);
                BigDecimal amount = InputHelper.promptForDollarAmount(scanner, "Enter the amount: $", true); // Prompt for the amount
                
                transactionManager.addTransaction(date, description, amount, TransactionType.EXPENSE);
                
                System.out.println("\nPress Enter to continue...");
                scanner.nextLine(); // Wait for user input to continue
                break;
            case 2: // Add Income
                clearScreen();
                System.out.println("ADD INCOME");
                System.out.println("------------------------------------------------------");
                date = InputHelper.promptForDate(scanner, "Enter the income date (YYYY-MM-DD): "); // Prompt for the date
                description = InputHelper.promptForDescription(scanner, "Enter the description: ", 80);
                amount = InputHelper.promptForDollarAmount(scanner, "Enter the amount: $", false); // Prompt for the amount
                
                transactionManager.addTransaction(date, description, amount, TransactionType.INCOME);
                
                System.out.println("\nPress Enter to continue...");
                scanner.nextLine(); // Wait for user input to continue
                break;
            case 3: // View Transactions
                clearScreen();
                transactionManager.viewTransactions();
                System.out.println("\nPress Enter to continue...");
                scanner.nextLine(); // Wait for user input to continue
                break;
            case 4: // View Summary Report
                clearScreen();
                displayReportMenu(); // Display the report menu

                int dateChoice = InputHelper.getValidatedMenuChoice(scanner, 1, DateRangeOption.getMaxNumber()); // Get the user's choice for date range
                DateRangeOption selected = DateRangeOption.fromNumber(dateChoice); // Get the selected date range option

                LocalDate dateStart = getDateStart(scanner, dateChoice); // Get the start date based on the user's choice
                LocalDate dateEnd = getDateEnd(scanner, dateChoice); // Get the end date based on the user's choice
                
                clearScreen(); 
                transactionManager.generateReport(true, selected, dateStart, dateEnd); // Generate the report with the selected date range
                
                System.out.println("\nPress Enter to continue...");
                scanner.nextLine(); // Wait for user input to continue
                break;
            case 5: // View Detailed Report
                clearScreen();  
                displayReportMenu(); // Display the report menu
                
                dateChoice = InputHelper.getValidatedMenuChoice(scanner, 1, DateRangeOption.getMaxNumber()); // Get the user's choice for date range
                selected = DateRangeOption.fromNumber(dateChoice); // Get the selected date range option

                dateStart = getDateStart(scanner, dateChoice); // Get the start date based on the user's choice
                dateEnd = getDateEnd(scanner, dateChoice); // Get the end date based on the user's choice
                
                clearScreen(); 
                transactionManager.generateReport(false, selected, dateStart, dateEnd); // Generate the report with the selected date range
                
                System.out.println("\nPress Enter to continue...");
                scanner.nextLine(); // Wait for user input to continue
                break;
            case 6: // Exit the application
                System.out.println("\nExiting the application. Goodbye!");
                break;
        }
    }

    private static void displayReportMenu() {
        /* Display date range options menu */

        System.out.println("Date Range");
        System.out.println("---------------------");
        for (DateRangeOption number : DateRangeOption.values()) {
            System.out.printf("%d. %s%n", number.getNumber(), number.getDescription()); // Display each date range option
        }
        System.out.println("---------------------");
    }

    private static LocalDate getDateStart(Scanner scanner, int choice) {
        /* Get the start date based on the user's choice */

        LocalDate dateStart = null; // Initialize dateStart to null

        switch (choice) {
            case 1: // All Dates
                dateStart = LocalDate.MIN; // Set to minimum date
                break;
            case 2: // YTD (Year to Date)
                dateStart = LocalDate.now().withDayOfYear(1); // Set to the first day of the current year
                break;
            case 3: // Custom Range
                dateStart = InputHelper.promptForDate(scanner, "Enter start date (YYYY-MM-DD): "); // Prompt for the start date
                break;
        }

        return dateStart; // Return the start date
    }

    private static LocalDate getDateEnd(Scanner scanner, int choice) {
        /* Get the end date based on the user's choice */

        LocalDate dateEnd = null; // Initialize dateEnd to null

        switch (choice) {
            case 1: // All Dates
                dateEnd = LocalDate.MAX; // Set to maximum date
                break;
            case 2: // YTD (Year to Date)
                dateEnd = LocalDate.now(); // Set to the current date
                break;
            case 3: // Custom Range
                dateEnd = InputHelper.promptForDate(scanner, "Enter end date (YYYY-MM-DD): "); // Prompt for the end date
                break;
        }

        return dateEnd; // Return the end date
    }
    public static void clearScreen() {
        /* Clear the console screen */

        System.out.print("\033[H\033[2J"); // \033[H moves the cursor to the top left corner, \033[2J clears the screen
        System.out.flush();
    }
}
