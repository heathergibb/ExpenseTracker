import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Scanner;

public class InputHelper {
    public static int getValidatedMenuChoice(Scanner scanner, int min, int max) {
        /* Get a validated choice from the user within the specified range */
        
        boolean validInput = false; // Flag to check if the input is valid
        int choice = 0; // Variable to store the user's choice

        while (!validInput) {
            // Prompt the user to select an option
            System.out.printf("Please select an option (%d-%d): ", min, max);
            String input = scanner.nextLine().trim(); // Read the input from the user and trim whitespace
            
            try {
                choice = Integer.parseInt(input); // Try to parse the input as an integer

                if (choice < min || choice > max) { // Check if the choice is within the valid range
                    System.out.printf("Invalid choice. Please select a number between %d and %d: ", min, max);
                } else {
                    validInput = true; // If valid, set the flag to true
                    return choice; // Return the valid choice
                }
            } catch (NumberFormatException e) { // Catch any exceptions if parsing fails
                System.out.print("Invalid input. Please enter a number: ");
            }
        }

        return choice; // Return the valid choice after the loop ends
    }

    public static LocalDate promptForDate(Scanner scanner, String prompt) {
        /* Prompt the user for a date and return it as a LocalDate object */
        
        LocalDate date = null; // Initialize date to null
        boolean validDate = false; // Flag to check if the date is valid

        while (!validDate) {
            System.out.print(prompt);
            String dateInput = scanner.nextLine().trim(); // Read the input and trim whitespace
            
            try {
                date = LocalDate.parse(dateInput); // Try to parse the input as a LocalDate
                validDate = true; // If parsing is successful, set the flag to true
            } catch (Exception e) { // Catch any exceptions if parsing fails
                System.out.println("Invalid date format. Please enter a valid date in YYYY-MM-DD format.");
            }
        }

        return date; // Return the valid date
    }

    public static String promptForDescription(Scanner scanner, String prompt, int maxLength) {
        /* Prompt the user for a description and return it */
        
        String description = ""; // Initialize description to an empty string
        boolean validDescription = false; // Flag to check if the description is valid

        while (!validDescription) {
            System.out.print(prompt);
            description = scanner.nextLine().trim(); // Read the input and trim whitespace
            
            if (description.length() > 0 && description.length() <= maxLength) { // Check if the description is valid
                validDescription = true; // If valid, set the flag to true
            } else {
                System.out.printf("Description must be between 1 and %d characters. Please try again.%n", maxLength);
            }
        }

        return description; // Return the valid description
    }
    
    public static BigDecimal promptForDollarAmount(Scanner scanner, String prompt, boolean allowNegative) {
        /* Prompt the user for an amount and return it as a BigDecimal */
        
        BigDecimal amount = null; // Initialize amount to null
        
        while (amount == null) { // Loop until a valid amount is entered}) {
            System.out.print(prompt);
            String amountInput = scanner.nextLine().trim(); // Read the input and trim whitespace
            
            // Fix inputs like .5 to 0.5
            if (amountInput.startsWith(".")) {
                amountInput = "0" + amountInput; 
            }

            try {
                BigDecimal parsedAmount = new BigDecimal(amountInput); // Try to parse the input as a BigDecimal
                
                // Reject if more than one decimal point
                if (parsedAmount.scale() > 2) {
                    System.out.println("Amount cannot have more than two decimal places. Please enter a valid amount.");
                    continue; // Skip to the next iteration if invalid
                }

                // Reject if negative and not allowed
                if (!allowNegative && parsedAmount.compareTo(BigDecimal.ZERO) < 0) {
                    System.out.println("Amount cannot be negative. Please enter a valid amount.");
                    continue; // Skip to the next iteration if invalid
                }

                // Set valid amount to 2 decimal places
                amount = parsedAmount.setScale(2, RoundingMode.HALF_UP); // Convert amount to 2 decimal places
                
            } catch (NumberFormatException e) { // Catch any exceptions if parsing fails
                System.out.println("Invalid amount format. Please enter a valid numeric value.");
            }
        }

        return amount; // Return the valid amount
    }

}
