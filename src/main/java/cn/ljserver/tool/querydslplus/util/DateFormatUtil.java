package cn.ljserver.tool.querydslplus.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Date forger
 */
public class DateFormatUtil {
    public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter DATETIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter TIME_MINUTE_FORMAT = DateTimeFormatter.ofPattern("HH:mm");

    public static LocalDateTime parseDateTime(String str) {
        return LocalDateTime.parse(str, DATETIME_FORMAT);
    }

    public static LocalDate parseDate(String str) {
        return LocalDate.parse(str, DATE_FORMAT);
    }

    public static LocalTime parseTime(String str) {
        return LocalTime.parse(str, TIME_MINUTE_FORMAT);
    }
}
