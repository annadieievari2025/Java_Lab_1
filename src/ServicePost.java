public class ServicePost {
    private final String name;
    private boolean isBusy = false;

    public ServicePost(String name) {
        this.name = name;
    }

    public boolean isBusy() { return isBusy; }

    public void occupy() {
        if (isBusy) {
            throw new CarServiceException("Service post is busy");
        }
        this.isBusy = true;
    }

    public void release() {
        this.isBusy = false;
    }
}