import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/* The purpose of this class is to handle the IO with the external csv file */
public class TransactionIO {
    public static List<Transaction> loadTransactions(String filePath) {
        /* Load the transactions from the file into the transactions Array */

        List<Transaction> transactions = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Date,")) continue; // Skip header line

                String[] parts = line.split(",");
                if (parts.length == 4) {
                    LocalDate date = LocalDate.parse(parts[0].trim());
                    String category = parts[1].trim();
                    BigDecimal amount = new BigDecimal(parts[2].trim());
                    TransactionType type = TransactionType.valueOf(parts[3].trim().toUpperCase());
                    
                    // Add the transaction to the list
                    transactions.add(new Transaction(date, category, amount, type));
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading transactions: " + e.getMessage());
        }

        return transactions;
    }

    public static void addTransaction(Transaction transaction, String filePath) {
        /* Add a new transaction to the file */

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(transaction.toCsvLine());
            writer.newLine(); // Add a new line after writing the transaction

            System.out.println("Transaction saved successfully.");
        } catch (IOException e) {
            System.err.println("Error saving transaction: " + e.getMessage());
        }
    }
}
