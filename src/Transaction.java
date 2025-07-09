import java.math.BigDecimal;
import java.time.LocalDate;

public class Transaction {
    private LocalDate date;
    private String description;
    private BigDecimal amount;
    private TransactionType type;

    public Transaction(LocalDate date, String description, BigDecimal amount, TransactionType type) {
        this.date = date;
        this.description = description;
        this.amount = amount;
        this.type = type;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public TransactionType getType() {
        return type;
    }
    
    public String toCsvLine() {
        /* Convert the transaction to a CSV line format */
        return String.format("%s,%s,%.2f,%s", date, description, amount, type);
    }
}
