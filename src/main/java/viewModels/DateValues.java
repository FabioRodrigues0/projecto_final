package viewModels;

import java.sql.Timestamp;
import java.time.LocalDate;

final class DateValues {

    private DateValues() {
    }

    static Timestamp atStartOfDay(LocalDate date) {
        return Timestamp.valueOf(date.atStartOfDay());
    }
}
