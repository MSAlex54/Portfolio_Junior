package com.reha.model.enums;

public enum AssignmentStatuses {
    ASSIGNED("Assigned"),
    CANCELLED("Canceled"),
    DONE("Done");

    private final String title;

    AssignmentStatuses(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
