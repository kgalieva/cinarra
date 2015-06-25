package com.cinarra.auction.common.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;

import static java.time.DayOfWeek.MONDAY;
import static java.time.DayOfWeek.SUNDAY;
import static java.util.Locale.FRANCE;

public class Utils {

    public static long getDay(Long millis) {
        Instant instant = Instant.ofEpochMilli(millis);
        LocalDate date = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
        return date.toEpochDay();
    }

    public static LocalDate firstDayOfWeek(LocalDate day) {
        TemporalField fieldISO = WeekFields.of(FRANCE).dayOfWeek();
        return day.with(fieldISO, MONDAY.getValue());
    }

    public static LocalDate lastDayOfWeek(LocalDate day) {
        TemporalField fieldISO = WeekFields.of(FRANCE).dayOfWeek();
        return day.with(fieldISO, SUNDAY.getValue());
    }
}
