package components;

import java.io.File;
import java.nio.file.Path;
import java.text.Normalizer;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class DocumentoImportacao {

    private static final Pattern DATE_PATTERN = Pattern
        .compile("\\b(\\d{1,2})[/-](\\d{1,2})[/-](\\d{2,4})\\b");
    private static final Pattern MONTH_DATE_PATTERN = Pattern
        .compile(
            "(?i)(january|february|march|april|may|june|july|august|september|october|november|december|jan|feb|mar|apr|jun|jul|aug|sep|sept|oct|nov|dec)\\.?\\s+\\d{1,2},\\s*\\d{4}"
        );
    private static final Pattern MONEY_TOKEN_PATTERN = Pattern
        .compile(
            "(?i)(?:€|eur|euros)\\s*\\d+(?:[.,]\\d{1,2})?|\\d+(?:[.,]\\d{1,2})?\\s*(?:€|eur|euros)"
        );
    private static final Pattern MONEY_WITH_CONTEXT_PATTERN = Pattern
        .compile(
            "(?i)(?:€\\s*)?(\\d+(?:[.,]\\d{1,2})?)\\s*(?:€|eur|euros)|(?:custo|preco|preço|valor|total|mensal|anual)[^\\d€]*(?:€\\s*)?(\\d+(?:[.,]\\d{1,2})?)"
        );

    private DocumentoImportacao() {
    }

    public static String titulo(File file, String content) {
        return linhas(content)
            .stream()
            .filter(line -> line.length() >= 3)
            .filter(line -> !isPagination(line))
            .filter(line -> !DATE_PATTERN.matcher(line).find())
            .filter(line -> !line.matches(".*\\d{5,}.*"))
            .findFirst()
            .orElseGet(() -> nomeSemExtensao(file));
    }

    public static String texto(String content) {
        return content == null ? "" : content.trim();
    }

    public static Optional<LocalDate> primeiraData(String content) {
        return datas(content).stream().findFirst();
    }

    public static Optional<LocalDate> segundaData(String content) {
        List<LocalDate> dates = datas(content);
        return dates.size() > 1 ? Optional.of(dates.get(1)) : Optional.empty();
    }

    public static Optional<LocalDate> ultimaData(String content) {
        List<LocalDate> dates = datas(content);
        return dates.isEmpty() ? Optional.empty() : Optional.of(dates.get(dates.size() - 1));
    }

    public static boolean temPeriodoAnual(String content) {
        List<LocalDate> dates = datas(content);
        for (int i = 0; i < dates.size(); i++) {
            for (int j = i + 1; j < dates.size(); j++) {
                long days = Math.abs(ChronoUnit.DAYS.between(dates.get(i), dates.get(j)));
                if (days >= 330 && days <= 400) {
                    return true;
                }
            }
        }
        return false;
    }

    public static Optional<Double> primeiroValor(String content) {
        Matcher matcher = MONEY_WITH_CONTEXT_PATTERN.matcher(content == null ? "" : content);
        while (matcher.find()) {
            String token = matcher.group(1) != null ? matcher.group(1) : matcher.group(2);
            if (!matcher.group().contains("€") && !token.matches("\\d+[.,]\\d{1,2}")) {
                continue;
            }
            token = token.replace(',', '.');
            try {
                double value = Double.parseDouble(token);
                if (value > 0) {
                    return Optional.of(value);
                }
            } catch (NumberFormatException ignored) {
                // Continua a procurar um valor utilizavel.
            }
        }
        return Optional.empty();
    }

    public static Optional<Double> valorPorEtiquetaNumerico(String content, String... labels) {
        List<String> lines = linhas(content);
        for (String label : labels) {
            String normalizedLabel = normalizar(label);
            for (String line : lines) {
                if (!normalizar(line).startsWith(normalizedLabel)) {
                    continue;
                }

                Optional<Double> value = ultimoValorMonetario(line);
                if (value.isPresent()) {
                    return value;
                }
            }
        }
        return Optional.empty();
    }

    public static Optional<Double> valorTotalFatura(String content) {
        List<String> lines = linhas(content);
        Optional<Double> dueValue = Optional.empty();
        Optional<Double> totalValue = Optional.empty();

        for (String line : lines) {
            String normalized = normalizar(line);

            if (
                normalized.contains("amount due") || normalized.contains("total due") || normalized
                    .contains("balance due") || normalized.contains("valor a pagar") || normalized
                        .contains("total a pagar") || normalized.matches(".*\\bdue\\b.*")
            ) {
                dueValue = ultimoValorMonetario(line);
            }

            if (
                normalized.startsWith("total ") || normalized.equals("total") || normalized
                    .startsWith("subtotal ")
            ) {
                totalValue = ultimoValorMonetario(line);
            }
        }

        return dueValue.isPresent() ? dueValue : totalValue;
    }

    public static Optional<String> descricaoFatura(String content) {
        List<String> lines = linhas(content);

        for (int i = 0; i < lines.size(); i++) {
            String normalizedLine = normalizar(lines.get(i));
            if (!normalizedLine.startsWith("description")) {
                continue;
            }

            for (int j = i + 1; j < lines.size(); j++) {
                String candidate = lines.get(j);
                String normalizedCandidate = normalizar(candidate);
                if (
                    normalizedCandidate.contains("subtotal") || normalizedCandidate
                        .contains("total") || normalizedCandidate.contains("tax")
                ) {
                    break;
                }

                if (!DATE_PATTERN.matcher(candidate).find() && !MONTH_DATE_PATTERN
                    .matcher(candidate)
                    .find()) {
                    return Optional.of(candidate);
                }
            }
        }

        return Optional.empty();
    }

    public static boolean pareceFatura(String content) {
        String normalized = normalizar(content);
        return normalized.contains("invoice") || normalized.contains("fatura") || normalized
            .contains("description") || normalized.contains("amount due") || normalized
                .contains("total due") || normalized.contains("total a pagar");
    }

    public static Optional<String> valorPorEtiqueta(String content, String... labels) {
        List<String> normalizedLabels = Arrays
            .stream(labels)
            .map(DocumentoImportacao::normalizar)
            .toList();

        return linhas(content).stream().filter(line -> {
            String normalizedLine = normalizar(line);
            return normalizedLabels.stream().anyMatch(normalizedLine::contains);
        })
            .map(line -> line.replaceFirst("^[^:：-]+[:：-]\\s*", "").trim())
            .filter(value -> !value.isBlank())
            .findFirst();
    }

    public static <E extends Enum<E>> E categoria(Class<E> enumType, String content, E fallback) {
        String normalized = normalizar(content);

        for (E value : enumType.getEnumConstants()) {
            if ("NONE".equals(value.name())) {
                continue;
            }

            String candidate = normalizar(value.name().replace('_', ' '));
            if (normalized.contains(candidate)) {
                return value;
            }
        }

        return fallback;
    }

    private static List<LocalDate> datas(String content) {
        String value = content == null ? "" : content;
        Matcher numericMatcher = DATE_PATTERN.matcher(value);
        List<LocalDate> numericDates = numericMatcher
            .results()
            .map(match -> parseDate(match.group()))
            .flatMap(Optional::stream)
            .toList();

        Matcher monthMatcher = MONTH_DATE_PATTERN.matcher(value);
        List<LocalDate> monthDates = monthMatcher
            .results()
            .map(match -> parseMonthDate(match.group()))
            .flatMap(Optional::stream)
            .toList();

        return java.util.stream.Stream
            .concat(numericDates.stream(), monthDates.stream())
            .distinct()
            .toList();
    }

    private static Optional<LocalDate> parseDate(String value) {
        for (String pattern : List.of("d/M/uuuu", "d-M-uuuu", "d/M/uu", "d-M-uu")) {
            try {
                return Optional.of(LocalDate.parse(value, DateTimeFormatter.ofPattern(pattern)));
            } catch (DateTimeParseException ignored) {
                // Tenta o proximo formato.
            }
        }
        return Optional.empty();
    }

    private static Optional<LocalDate> parseMonthDate(String value) {
        for (
            String pattern : List.of("MMMM d, uuuu", "MMM d, uuuu", "MMMM dd, uuuu", "MMM dd, uuuu")
        ) {
            try {
                return Optional
                    .of(
                        LocalDate
                            .parse(
                                value.replace(".", ""),
                                DateTimeFormatter.ofPattern(pattern, Locale.ENGLISH)
                            )
                    );
            } catch (DateTimeParseException ignored) {
                // Tenta o proximo formato.
            }
        }
        return Optional.empty();
    }

    private static Optional<Double> ultimoValorMonetario(String line) {
        Matcher matcher = MONEY_TOKEN_PATTERN.matcher(line);
        Double value = null;

        while (matcher.find()) {
            String token = matcher
                .group()
                .replace("€", "")
                .replaceAll("(?i)eur|euros", "")
                .trim()
                .replace(',', '.');
            try {
                value = Double.parseDouble(token);
            } catch (NumberFormatException ignored) {
                // Continua a procurar outro valor na mesma linha.
            }
        }

        return Optional.ofNullable(value);
    }

    private static List<String> linhas(String content) {
        return Arrays
            .stream((content == null ? "" : content).split("\\R"))
            .map(String::trim)
            .filter(line -> !line.isBlank())
            .filter(line -> !isPagination(line))
            .toList();
    }

    private static boolean isPagination(String line) {
        String normalized = normalizar(line);
        return normalized.matches("^(page|pagina|pag)\\s+\\d+\\s+(of|de)\\s+\\d+$") || normalized
            .matches("^\\d+\\s*/\\s*\\d+$");
    }

    private static String nomeSemExtensao(File file) {
        if (file == null) {
            return "";
        }

        String name = Path.of(file.getName()).getFileName().toString();
        int dot = name.lastIndexOf('.');
        return dot > 0 ? name.substring(0, dot) : name;
    }

    private static String normalizar(String value) {
        String normalized = Normalizer.normalize(value == null ? "" : value, Normalizer.Form.NFD);
        return normalized
            .replaceAll("\\p{M}", "")
            .toLowerCase(Locale.ROOT)
            .replace('_', ' ')
            .trim();
    }
}
