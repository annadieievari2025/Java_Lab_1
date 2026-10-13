import java.util.HashSet;
import java.util.Set;

public class CarServiceManager {
    private final Set<Car> cars = new HashSet<>();

    public void registerCar(Car car) {
        if (car == null) {
            throw new CarServiceException("Car cannot be null");
        }
        if (!cars.add(car)) {
            throw new CarServiceException("Car with VIN " + car.getVin() + " is already registered");
        }
    }

    public Order createOrder(Car car) {
        if (car == null || !cars.contains(car)) {
            throw new CarServiceException("Car is not registered");
        }
        return new Order(car);
    }
}
