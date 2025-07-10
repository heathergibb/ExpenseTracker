import java.math.BigDecimal;
import java.time.LocalDate;

// This class contains all the details of a transaction
// It includes the date, category, amount, and type of transaction (expense or income)
public class Transaction {
    private LocalDate date;
    private String category;
    private BigDecimal amount;
    private TransactionType type;

    public Transaction(LocalDate date, String category, BigDecimal amount, TransactionType type) {
        this.date = date;
        this.category = category;
        this.amount = amount;
        this.type = type;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getCategory() {
        return category;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public TransactionType getType() {
        return type;
    }
    
    public String toCsvLine() {
        // Convert the transaction to a CSV line format 
        return String.format("%s,%s,%.2f,%s", date, category, amount, type);
    }
}
