package com.reha.model.enums;

public enum IntervalTypes {
    DAY("Day"),
    WEEK("Week"),
    MONTH("Month");

    private final String title;

    IntervalTypes(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
