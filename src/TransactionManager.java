import java.util.ArrayList;

public class TransactionManager {
    public ArrayList<Transaction> transactions;

    public TransactionManager() {
        // Initialize the transaction manager
        transactions = new ArrayList<>();
        // Load transactions from the default file
        TransactionIO transactionIO = new TransactionIO("data/default.csv");
        // Load the array with transactions from the file
        transactions.addAll(transactionIO.loadTransactions());
    }

    public void viewTransactions() {
        // Display all transactions
        System.out.println("TRANSACTIONS\n");
        System.out.printf("%-15s %-20s %-10s %-10s%n", "Date", "Description", "Amount", "Type");
        System.out.println("------------------------------------------------------");
        for (Transaction transaction : transactions) {
            System.out.printf("%-15s %-20s %-10.2f %-10s%n",
                    transaction.getDate(),
                    transaction.getDescription(),
                    transaction.getAmount(),
                    transaction.getType());
        }

    }
}
