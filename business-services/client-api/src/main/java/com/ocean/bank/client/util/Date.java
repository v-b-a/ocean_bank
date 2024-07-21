package com.ocean.bank.client.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Date {
    public static LocalDate parseDate(String date) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }
}
