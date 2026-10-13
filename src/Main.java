import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Launching ===\n");
        CarService manager = new CarService();

        System.out.println("--- Scenario 1: Successful ---");
        Client client = new Client("Oleh", "+380991234567");
        Car car = new Car("VIN123456789", "Toyota ", "Corolla", 2002, client);

        manager.registerCar(car);
        Mechanic mechanic = new Mechanic("Michael");
        ServicePost post = new ServicePost("Post №1");

        Order order = manager.createOrder(car);
        System.out.println("New order: " + order.getId());

        Job oilChange = new Job("Oil change", new BigDecimal("500.00"), 1);
        Job diagnostics = new Job("Diagnostics", new BigDecimal("300.00"), 2);
        order.addJob(oilChange);
        order.addJob(diagnostics);
        order.addPart(new Part("Oil", new BigDecimal("1200.00")));

        order.approve();
        order.assignResources(mechanic, post);
        order.startExecution();

        order.completeJob(oilChange);
        order.completeJob(diagnostics);
        order.completeOrder();

        System.out.println("Order complete. Total cost: " + order.getTotalCost() + " грн\n");

        testNegativeScenario("Attempt to approve an empty order", () -> {
            manager.createOrder(car).approve();
        });

        testNegativeScenario("Attempt to launch without mechanic ", () -> {
            Order order2 = manager.createOrder(car);
            order2.addJob(new Job("Washing", new BigDecimal("200"), 1));
            order2.approve();
            order2.startExecution();
        });

        testNegativeScenario("Attempt to assign a busy mechanic", () -> {
            Order active = manager.createOrder(car);
            active.addJob(new Job("Repair", new BigDecimal("1000"), 3));
            active.approve();
            active.assignResources(mechanic, post);
            active.startExecution();

            Order newOrder = manager.createOrder(car);
            newOrder.addJob(new Job("Diagnostics", new BigDecimal("400"), 1));
            newOrder.approve();
            newOrder.assignResources(mechanic, post);
        });

        testNegativeScenario("Attempt to change completed order", () -> {
            order.addJob(new Job("Job", new BigDecimal("100"), 1));
        });
    }

    private static void testNegativeScenario(String description, Runnable action) {
        System.out.println("--- Negative: " + description + " ---");
        try {
            action.run();
            System.out.println("[ERROR] Scenario running");
        } catch (CarServiceException e) {
            System.out.println("[OK - Expected error] " + e.getMessage() + "\n");
        }
    }
}