package viewModels;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

final class DateValues {
    private static final DateTimeFormatter SQLITE_TIMESTAMP = DateTimeFormatter
        .ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    private DateValues() {
    }

    static String atStartOfDay(LocalDate date) {
        return timestamp(date.atStartOfDay());
    }

    static String timestamp(LocalDateTime dateTime) {
        return SQLITE_TIMESTAMP.format(dateTime);
    }
}
