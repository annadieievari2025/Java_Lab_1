public class Car {
    private final String vin;
    private final String brand;
    private final String model;
    private final int year;
    private final Client owner;

    public Car(String vin, String brand, String model, int year, Client owner) {
        if (vin == null || vin.isBlank()) {
            throw new CarServiceException("VIN cannot be empty.");
        }
        if (owner == null) {
            throw new CarServiceException("Owner cannot be null.");
        }
        this.vin = vin;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.owner = owner;
    }

    public String getVin() { return vin; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Car)) return false;
        return vin.equals(((Car) o).vin);
    }

    @Override
    public int hashCode() { return vin.hashCode(); }
}
