public class Client {
    private String name;
    private String phone;

    public Client(String name, String phone) {
        if (name == null || name.isBlank()) {
            throw new CarServiceException("Name cannot be null or blank");
        }
        this.name = name;
        this.phone = phone;
    }
    public String getName() { return name; }
}
// wefewf