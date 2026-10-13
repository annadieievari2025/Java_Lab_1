import java.math.BigDecimal;

public class Part {
    private String name;
    private final BigDecimal cost;

    public Part (String name, BigDecimal cost) {
        if (cost.compareTo(BigDecimal.ZERO) <0) {
            throw new CarServiceException("Cost cannot be negative");
        }
        this.name = name;
        this.cost = cost;
    }

    public BigDecimal getCost() { return cost; }
    public String getName() { return name; }
}