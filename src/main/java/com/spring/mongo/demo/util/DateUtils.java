package com.spring.mongo.demo.util;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    private final static String DEFAULT_DATE_PATTERN = "yyyyMMdd_HHmm";

    public static String getDateAsString(LocalDateTime dateTime) {
        DateTimeFormatter formatter  = DateTimeFormatter.ofPattern(DEFAULT_DATE_PATTERN);
        return dateTime.format(formatter);
    }
}
