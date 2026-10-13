public class Mechanic {
    private final String name;
    private boolean isBusy = false;

    public Mechanic(String name) {
        this.name = name;
    }

    public boolean isBusy() { return isBusy; }

    public void assignWork() {
        if (isBusy) {
            throw new CarServiceException("Mechanic is busy");
        }
        this.isBusy = true;
    }
    public void releaseFromWork() {
        this.isBusy = false;
    }
}