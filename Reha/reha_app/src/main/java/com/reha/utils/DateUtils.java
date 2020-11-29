package com.reha.utils;

import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DateUtils {
    public static List<String> getDaysList() {
        List<String> daysList = new ArrayList<String>();
        for (DayOfWeek d : DayOfWeek.values()) {
            daysList.add(d.getDisplayName(TextStyle.FULL, Locale.getDefault()));
        }
        return daysList;
    }
}
