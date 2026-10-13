import java.math.BigDecimal;

public class Part {
    private final String name;
    private final BigDecimal cost;

    public Part(String name, BigDecimal cost) {
        if (name == null || name.isBlank()) {
            throw new CarServiceException("Part name cannot be empty");
        }
        if (cost == null || cost.compareTo(BigDecimal.ZERO) < 0) {
            throw new CarServiceException("Cost cannot be null or negative");
        }
        this.name = name;
        this.cost = cost;
    }

    public BigDecimal getCost() { return cost; }
    public String getName() { return name; }
}
