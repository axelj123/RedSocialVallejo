package com.example.conexionVallejo.servicios;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class AgeCalculatorService {

    public static String calculatePostAge(Instant postInstant) {
        Instant currentInstant = Instant.now();
        Duration duration = Duration.between(postInstant, currentInstant);
        return calculateAge(duration);
    }

    public static Map<Integer, String> calculateAnswerAges(List<Instant> answerInstants) {
        Map<Integer, String> answerAges = new HashMap<>();
        Instant currentInstant = Instant.now();
        for (Instant answerInstant : answerInstants) {
            Duration duration = Duration.between(answerInstant, currentInstant);
            String answerAge = calculateAge(duration);
            // Aquí podrías manejar el id del post de respuesta si fuera necesario
            answerAges.put(answerInstant.hashCode(), answerAge);
        }
        return answerAges;
    }

    private static String calculateAge(Duration duration) {
        long seconds = duration.getSeconds();

        if (seconds < 60) {
            return "hace " + seconds + (seconds == 1 ? " segundo" : " segundos");
        } else if (seconds < 3600) {
            long minutes = seconds / 60;
            return "hace " + minutes + (minutes == 1 ? " minuto" : " minutos");
        } else if (seconds < 86400) {
            long hours = seconds / 3600;
            return "hace " + hours + (hours == 1 ? " hora" : " horas");
        } else if (seconds < 172800) {
            return "ayer a las " + formatTime(duration);
        } else {
            return formatDate(duration) + " a las " + formatTime(duration);
        }
    }

    private static String formatDate(Duration duration) {
        Instant now = Instant.now();
        Instant then = now.minus(duration);
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(then, ZoneId.of("America/Lima")); // Zona horaria específica (ej. Lima)

        LocalDate currentDate = LocalDate.now();
        LocalDate thenDate = zonedDateTime.toLocalDate();

        if (currentDate.equals(thenDate)) {
            return "hoy";
        } else if (currentDate.minusDays(1).equals(thenDate)) {
            return "ayer";
        } else {
            return zonedDateTime.format(DateTimeFormatter.ofPattern("dd MMM.", Locale.getDefault()));
        }
    }

    private static String formatTime(Duration duration) {
        Instant now = Instant.now();
        Instant then = now.minus(duration);
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(then, ZoneId.of("America/Lima")); // Zona horaria específica (ej. Lima)
        return zonedDateTime.format(DateTimeFormatter.ofPattern("HH:mm", Locale.getDefault()));
    }
}
