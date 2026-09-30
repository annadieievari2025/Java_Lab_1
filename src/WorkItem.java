import java.math.BigDecimal;

public class WorkItem {
    private final String description;
    private final BigDecimal partsCost;
    private final BigDecimal laborCost;
    private WorkStatus status;              // PENDING / DONE

    public WorkItem(String description, BigDecimal partsCost, BigDecimal laborCost) {
        // 1. description: не null і не порожній (після trim)
        // 2. partsCost, laborCost: не null і >= 0
        // 3. якщо все добре — присвоїти поля, status = PENDING
    }

    public BigDecimal getTotalCost() {

    }

    public void markDone() {

    }          // правило: що робити, якщо вже DONE?

    // геттери: getDescription(), getPartsCost(), getLaborCost(), getStatus()
    // toString()
}