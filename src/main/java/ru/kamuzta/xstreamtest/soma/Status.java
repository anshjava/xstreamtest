package ru.kamuzta.xstreamtest.soma;

public enum Status {
    NEW("NEW"),
    QUEUED("QUEUED"),
    INPROGRESS("INPROGRESS"),
    COMPLETED("COMPLETED");

    private String statusName;

    Status(String statusName) {
        this.statusName = statusName;
    }

    public String getStatusName() {
        return statusName;
    }

    @Override
    public String toString() {
        return getStatusName();
    }
}
