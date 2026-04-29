package apolov2.insightsApolo.v2.infrastructure.util;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DataHoraUtil {
    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

    public static String formatarDuracao(Duration duracao) {
        long horas = duracao.toHours();
        long minutos = duracao.toMinutes() % 60;
        return String.format("%02d:%02d", horas, minutos);
    }

    public static String formatarData(LocalDate data) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return data.format(formatter);
    }

    public static Duration parseISO8601(String duracao) {
        // Espera uma string no formato HH:mm (ex: "01:30")
        LocalTime tempo = LocalTime.parse(duracao, formatter);
        return Duration.between(LocalTime.MIN, tempo);
    }
}
