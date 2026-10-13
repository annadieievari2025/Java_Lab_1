import javax.smartcardio.CardException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Order {
    private static int idCounter = 1;

    private final int id;
    private final Car car;
    private final List<Job> jobs = new ArrayList<>();
    private final List<Part> parts = new ArrayList<>();
    private OrderStatus status;

    private Mechanic mechanic;
    private ServicePost post;

    public Order(Car car) {
        this.id = idCounter++;
        this.car = car;
        this.status = OrderStatus.CREATED;
    }

    public int getId() {
        return id;
    }

    public List<Job> getJobs() {
        return Collections.unmodifiableList(jobs);
    }

    public void addJob(Job job) {
        if (status != OrderStatus.CREATED) {
            throw new CarServiceException("Cannot add job to order in status " + status);
        }
        jobs.add(job);
    }

    public void addPart(Part part) {
        if (status != OrderStatus.CREATED && status != OrderStatus.IN_PROGRESS) {
            throw new CarServiceException("Cannot add part to order in status " + status);
        }
    }

    public void approve () {
        if (status != OrderStatus.CREATED) {
            throw new CarServiceException("Cannot approve order in status " + status);
        }
        if (jobs.isEmpty()) {
            throw new CarServiceException("Cannot approve order with no jobs");
        }
        this.status = OrderStatus.APPROVED;
    }

    public void assignResources(Mechanic mechanic, ServicePost post) {
        if (status != OrderStatus.APPROVED) {
            throw new CarServiceException("Cannot assign resources to order in status " + status);
        }

        mechanic.assignWork();
        post.occupy();

        this.mechanic = mechanic;
        this.post = post;
    }

    public void startExecution() {
        if (status != OrderStatus.APPROVED) {
            throw new CarServiceException("Cannot start execution in status " + status);
        }
        if (this.mechanic == null || this.post == null) {
            throw new CarServiceException("Cannot start execution without resources");
        }
        this.status = OrderStatus.IN_PROGRESS;
    }
    public void completeJob (Job job) {
        if (status != OrderStatus.IN_PROGRESS) {
            throw new CarServiceException("Cannot complete job in status " + status);
        }
        if (!jobs.contains(job)) {
            throw new CarServiceException("Cannot complete job that is not in the order");
        }
        job.markAsCompleted();
    }

    public void releaseResources() {
        if (this.mechanic != null) {
            this.mechanic.releaseFromWork();
        }
        if (this.post != null) {
            this.post.release();
        }
    }

    public BigDecimal getTotalCost() {
        BigDecimal total = BigDecimal.ZERO;
        for (Job job : jobs) total = total.add(job.getCost());
        for (Part part : parts) total = total.add(part.getCost());
        return total;
    }
}