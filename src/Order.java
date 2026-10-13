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
        if (car == null) {
            throw new CarServiceException("Car cannot be null");
        }
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
        if (!OrderStatus.CREATED.equals(status)) {
            throw new CarServiceException("Cannot add job to order in status " + status);
        }
        if (job == null) {
            throw new CarServiceException("Job cannot be null");
        }
        jobs.add(job);
    }

    public void addPart(Part part) {
        if (!OrderStatus.CREATED.equals(status) && !OrderStatus.IN_PROGRESS.equals(status)) {
            throw new CarServiceException("Cannot add part to order in status " + status);
        }
        if (part == null) {
            throw new CarServiceException("Part cannot be null");
        }
        parts.add(part);
    }

    public void approve() {
        if (!OrderStatus.CREATED.equals(status)) {
            throw new CarServiceException("Cannot approve order in status " + status);
        }
        if (jobs.isEmpty()) {
            throw new CarServiceException("Cannot approve order with no jobs");
        }
        this.status = OrderStatus.APPROVED;
    }

    public void assignResources(Mechanic mechanic, ServicePost post) {
        if (!OrderStatus.APPROVED.equals(status)) {
            throw new CarServiceException("Cannot assign resources to order in status " + status);
        }

        if (mechanic == null || post == null) {
            throw new CarServiceException("Mechanic and post cannot be null");
        }
        if (this.mechanic != null) {
            throw new CarServiceException("Resources already assigned");
        }
        if (mechanic.isBusy()) throw new CarServiceException("Mechanic is busy");
        if (post.isBusy()) throw new CarServiceException("Service post is busy");
        mechanic.assignWork();
        post.occupy();

        this.mechanic = mechanic;
        this.post = post;
    }

    public void startExecution() {
        if (!OrderStatus.APPROVED.equals(status)) {
            throw new CarServiceException("Cannot start execution in status " + status);
        }
        if (this.mechanic == null || this.post == null) {
            throw new CarServiceException("Cannot start execution without resources");
        }
        this.status = OrderStatus.IN_PROGRESS;
    }

    public void completeJob(Job job) {
        if (!OrderStatus.IN_PROGRESS.equals(status)) {
            throw new CarServiceException("Cannot complete job in status " + status);
        }
        if (!jobs.contains(job)) {
            throw new CarServiceException("Cannot complete job that is not in the order");
        }
        job.markAsCompleted();
    }

    public void completeOrder() {
        if (!OrderStatus.IN_PROGRESS.equals(status)) {
            throw new CarServiceException("Cannot complete order in status " + status);
        }
        for (Job job : jobs) {
            if (!job.isCompleted()) {
                throw new CarServiceException("Cannot complete order: not all jobs are completed");
            }
        }
        this.status = OrderStatus.COMPLETED;
        releaseResources();
    }

    private void releaseResources() {
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