public class Car {
    private String vin;
    private String brand;
    private String model;
    private int year;
    private Client owner;

    public Car (String vin, String brand, Client owner) {
        if (vin == null || vin.isBlank()) {
            throw new CarServiceException("VIN cannot be empty.")
        }
        this.vin = vin;
        this.brand = brand;
        this.owner = owner;
    }
    public String getVin() { return vin; }
}