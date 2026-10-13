import java.math.BigDecimal;

public class Job {
    private final String description;
    private final BigDecimal cost;
    private final int durationHours;
    private boolean isCompleted = false;

    public Job(String description, BigDecimal cost, int durationHours) {
        if (description == null || description.isBlank()) {
            throw new CarServiceException("Description cannot be empty");
        }
        if (cost == null || cost.compareTo(BigDecimal.ZERO) < 0) {
            throw new CarServiceException("Cost cannot be null or negative");
        }
        if (durationHours < 0) {
            throw new CarServiceException("Duration cannot be negative");
        }
        this.description = description;
        this.cost = cost;
        this.durationHours = durationHours;
    }

    public BigDecimal getCost() { return cost; }
    public boolean isCompleted() { return isCompleted; }

    public void markAsCompleted() {
        this.isCompleted = true;
    }
}
