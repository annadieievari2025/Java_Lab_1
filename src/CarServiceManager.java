import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CarServiceManager {
    private final Set<String> registeredVins = new HashSet<>();
    private final List<Car> cars = new ArrayList<>();

    public void registerCar(Car car) {
        if (registeredVins.contains(car.getVin())) {
            throw new CarServiceException("Car with VIN " + car.getVin() + " is already registered");
        }
        registeredVins.add(car.getVin());
        cars.add(car);
    }

    public Order createOrder(Car car) {
        if (!registeredVins.contains(car.getVin())) {
            throw new CarServiceException("Car with VIN " + car.getVin() + " is not registered");
        }
        return new Order(car);
    }
}