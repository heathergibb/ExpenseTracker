import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TransactionIO {
    private String filePath;

    public TransactionIO(String filePath) {
        this.filePath = filePath;
    }

    public List<Transaction> loadTransactions() {
        /* Load the transactions from the file into the transactions Array */

        List<Transaction> transactions = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Date,")) continue; // Skip header line

                String[] parts = line.split(",");
                if (parts.length == 4) {
                    LocalDate date = LocalDate.parse(parts[0].trim());
                    String description = parts[1].trim();
                    BigDecimal amount = new BigDecimal(parts[2].trim());
                    TransactionType type = TransactionType.valueOf(parts[3].trim().toUpperCase());
                    
                    // Add the transaction to the list
                    transactions.add(new Transaction(date, description, amount, type));
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading transactions: " + e.getMessage());
        }

        return transactions;
    }

    public void addTransaction(Transaction transaction) {
        /* Add the new transaction to the file */

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(transaction.toCsvLine());
            writer.newLine(); // Add a new line after writing the transaction

            System.out.println("Transaction saved successfully.");
        } catch (IOException e) {
            System.err.println("Error saving transaction: " + e.getMessage());
        }
    }
}
