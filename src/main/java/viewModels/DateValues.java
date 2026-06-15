package viewModels;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Representa DateValues na aplicação.
 */
final class DateValues {
    private static final DateTimeFormatter SQLITE_TIMESTAMP = DateTimeFormatter
        .ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    /**
     * Cria uma nova instância.
     */
    private DateValues() {
    }

    /**
     * Executa a operação atStartOfDay.
     *
     * @param date valor usado pela operação
     * @return resultado da operação
     */
    static String atStartOfDay(LocalDate date) {
        return timestamp(date.atStartOfDay());
    }

    /**
     * Executa a operação timestamp.
     *
     * @param dateTime valor usado pela operação
     * @return resultado da operação
     */
    static String timestamp(LocalDateTime dateTime) {
        return SQLITE_TIMESTAMP.format(dateTime);
    }
}
