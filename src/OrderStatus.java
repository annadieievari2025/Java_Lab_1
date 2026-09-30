public enum OrderStatus {
    CREATED, DIAGNOSED, APPROVED, IN_PROGRESS, COMPLETED, CANCELED;

    public boolean canBeChanged() {
        return this != COMPLETED && this != CANCELED;
    }

    public boolean canTransitionTo(OrderStatus nextStatus) {
        return this.canBeChanged() && nextStatus.canBeChanged();
    }

    public boolean isFinal() {
        return this == COMPLETED || this == CANCELED;
    }

    public boolean isActive() {
        return this != COMPLETED && this != CANCELED;
    }
}