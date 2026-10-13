import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Запуск системи автосервісу ===\n");
        CarServiceManager manager = new CarServiceManager();

        // --- Успішний сценарій ---
        System.out.println("--- Сценарій 1: Успішне виконання замовлення ---");
        Client client = new Client("Анна", "+380991234567");
        Car car = new Car("VIN123456789", "Toyota ", "Corolla", 2002, client);

        manager.registerCar(car);
        Mechanic mechanic = new Mechanic("Олег");
        ServicePost post = new ServicePost("Пост №1");

        Order order = manager.createOrder(car);
        System.out.println("Створено замовлення: " + order.getId());

        Job oilChange = new Job("Заміна мастила", new BigDecimal("500.00"), 1);
        Job diagnostics = new Job("Діагностика", new BigDecimal("300.00"), 2);
        order.addJob(oilChange);
        order.addJob(diagnostics);
        order.addPart(new Part("Мастило", new BigDecimal("1200.00")));

        order.approve();
        order.assignResources(mechanic, post);
        order.startExecution();

        order.completeJob(oilChange);
        order.completeJob(diagnostics);
        order.completeOrder();

        System.out.println("Замовлення завершено. Підсумкова вартість: " + order.getTotalCost() + " грн\n");

        // --- Негативні сценарії ---
        testNegativeScenario("Спроба погодити порожнє замовлення", () -> {
            manager.createOrder(car).approve();
        });

        testNegativeScenario("Спроба запустити без механіка", () -> {
            Order order2 = manager.createOrder(car);
            order2.addJob(new Job("Миття", new BigDecimal("200"), 1));
            order2.approve();
            order2.startExecution();
        });

        testNegativeScenario("Спроба призначити зайнятого механіка", () -> {
            Order active = manager.createOrder(car);
            active.addJob(new Job("Ремонт", new BigDecimal("1000"), 3));
            active.approve();
            active.assignResources(mechanic, post);
            active.startExecution();

            Order newOrder = manager.createOrder(car);
            newOrder.addJob(new Job("Огляд", new BigDecimal("400"), 1));
            newOrder.approve();
            newOrder.assignResources(mechanic, post); // Впаде тут
        });

        testNegativeScenario("Спроба змінити завершене замовлення", () -> {
            order.addJob(new Job("Зайва робота", new BigDecimal("100"), 1));
        });
    }

    private static void testNegativeScenario(String description, Runnable action) {
        System.out.println("--- Негативний: " + description + " ---");
        try {
            action.run();
            System.out.println("[ПОМИЛКА] Сценарій не впав!");
        } catch (CarServiceException e) {
            System.out.println("[ОК - Очікувана помилка] " + e.getMessage() + "\n");
        }
    }
}