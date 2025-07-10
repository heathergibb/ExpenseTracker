import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class TransactionManager {
    public ArrayList<Transaction> transactions;
    private String filePath; 

    public TransactionManager(String filePath) {
        // Initialize the transaction manager
        transactions = new ArrayList<>();
        this.filePath = filePath; // Set the file path for the transactions CSV file

        // Load the array with transactions from the file
        transactions.addAll(TransactionIO.loadTransactions(filePath));
    }

    public void addTransaction(LocalDate date, String category, BigDecimal amount, TransactionType type) {
        /* Add an expense or income transaction to the list */
               
        Transaction newTransaction = new Transaction(date, category, amount, type);

        // Add the new transaction to the transactions array
        transactions.add(newTransaction);

        // Save the transaction to the file
        TransactionIO.addTransaction(newTransaction, filePath);
    }
    
    public void viewTransactions() {
        // Display all transactions

        System.out.println("TRANSACTIONS\n");
        System.out.printf("%-15s %-20s %-10s %-10s%n", "Date", "Category", "Amount", "Type");
        System.out.println("------------------------------------------------------");
        // for each transaction in the array, print the details
        for (Transaction transaction : transactions) {
            System.out.printf("%-15s %-20s %-10.2f %-10s%n",
                    transaction.getDate(),
                    transaction.getCategory(),
                    transaction.getAmount(),
                    transaction.getType());
        }
    }

    public void generateReport(boolean isSummary, DateRangeOption selected, LocalDate dateStart, LocalDate dateEnd) {
        /* Generate an income/expense report */
        
        // Check if there are no transactions
        if (transactions.isEmpty()) {
            System.out.println("------------------------------------------------------");
            System.out.println("INCOME/EXPENSE REPORT");
            System.out.println("------------------------------------------------------");
            System.out.println("No transactions available.");
            return; // Exit the method if there are no transactions
        }

        System.out.printf("INCOME/EXPENSE REPORT - %s", selected.getDescription());
                
        // Print the date range based if custom range is selected
        if (selected == DateRangeOption.CUSTOM_RANGE) {
            System.out.printf("\n(%s to %s)", dateStart, dateEnd);
        }

        System.out.println("\n------------------------------------------------------");
        
        BigDecimal incomeTotal = BigDecimal.ZERO;
        BigDecimal expenseTotal = BigDecimal.ZERO;

        if (isSummary) {

            for (Transaction transaction : transactions) {
                if (transaction.getDate().isBefore(dateStart) || transaction.getDate().isAfter(dateEnd)) {
                    continue; // Skip transactions outside the date range
                }
                
                if (transaction.getType() == TransactionType.INCOME) {
                    incomeTotal = incomeTotal.add(transaction.getAmount());
                } else if (transaction.getType() == TransactionType.EXPENSE) {
                    expenseTotal = expenseTotal.add(transaction.getAmount());
                }
            }
            System.out.printf("\nTotal Income - $%.2f%n", incomeTotal);
            System.out.printf("Total Expenses - $%.2f%n", expenseTotal);
        }
        else {
            Map<String, BigDecimal> incomeMap = new TreeMap<>(); // use TreeMap to sort by category
            Map<String, BigDecimal> expenseMap = new TreeMap<>();

            for (Transaction transaction : transactions) {
                if (transaction.getDate().isBefore(dateStart) || transaction.getDate().isAfter(dateEnd)) {
                    continue; // Skip transactions outside the date range
                }
                  
                if (transaction.getType() == TransactionType.INCOME) {
                    incomeMap.put(transaction.getCategory().toUpperCase(), incomeMap.getOrDefault(transaction.getCategory().toUpperCase(), BigDecimal.ZERO).add(transaction.getAmount()));
                    incomeTotal = incomeTotal.add(transaction.getAmount());
                } else if (transaction.getType() == TransactionType.EXPENSE) {
                    expenseMap.put(transaction.getCategory().toUpperCase(), expenseMap.getOrDefault(transaction.getCategory().toUpperCase(), BigDecimal.ZERO).add(transaction.getAmount()));
                    expenseTotal = expenseTotal.add(transaction.getAmount());
                }
            }
            
            System.out.printf("\n%-20s %-15s%n", "Income", "Amount");
            System.out.println("-------------------------------------");
    
            for (Map.Entry<String, BigDecimal> entry : incomeMap.entrySet()) {
                System.out.printf("%-20s $%-15.2f%n", entry.getKey(), entry.getValue());
            }

            System.out.printf("\n%-20s $%-15.2f%n","Total Income", incomeTotal);

            System.out.printf("\n%-20s %-15s%n", "Expenses", "Amount");
            System.out.println("-------------------------------------");
    
            for (Map.Entry<String, BigDecimal> entry : expenseMap.entrySet()) {
                System.out.printf("%-20s $%-15.2f%n", entry.getKey(), entry.getValue());
            }

            System.out.printf("\n%-20s $%-15.2f%n","Total Expenses", expenseTotal);
            
        }
    }

}
