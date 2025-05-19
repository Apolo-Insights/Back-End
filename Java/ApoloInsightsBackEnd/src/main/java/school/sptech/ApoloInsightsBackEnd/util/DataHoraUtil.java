package school.sptech.ApoloInsightsBackEnd.util;

import java.time.Duration;
import java.time.format.DateTimeFormatter;

public class DataHoraUtil {
    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

    public static String formatarDuracao(Duration duracao) {
        long horas = duracao.toHours();
        long minutos = duracao.toMinutes() % 60;
        return String.format("%02d:%02d", horas, minutos);
    }
}
