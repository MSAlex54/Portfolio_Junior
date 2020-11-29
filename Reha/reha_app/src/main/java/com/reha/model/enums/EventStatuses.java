package com.reha.model.enums;

public enum EventStatuses {
    SCHEDULED("Scheduled"),
    CANCELLED("Cancelled"),
    DONE("Done");

    private final String title;

    EventStatuses(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}