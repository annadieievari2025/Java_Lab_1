import java.math.BigDecimal;

public class Job {
    private String description;
    private BigDecimal cost;
    private int durationHours;
    private boolean isCompleted = false;

    public Job(String description, BigDecimal cost, int durationHours) {
        if (cost.compareTo(BigDecimal.ZERO) < 0) {
            throw new CarServiceException("Cost cannot be negative");
        }
        if (durationHours < 0) {
            throw new CarServiceException("Duration cannot be negative");
        }
    }

    public BigDecimal getCost() { return cost; }
    public boolean isCompleted() { return isCompleted; }

    public void markAsCompleted () {
        this.isCompleted = true;
    }
}