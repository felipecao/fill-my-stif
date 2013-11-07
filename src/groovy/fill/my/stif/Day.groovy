package fill.my.stif

import org.joda.time.LocalDate
import org.joda.time.LocalDateTime
import org.apache.commons.lang.StringUtils
import org.joda.time.DateTimeConstants
import static fill.my.stif.Intervalo.*

/**
 * Created with IntelliJ IDEA.
 * User: felipe
 * Date: 11/7/13
 * Time: 0:16
 * To change this template use File | Settings | File Templates.
 */
class Day {
    LocalDate dia
    String diaAsString
    LocalDateTime entrada1
    LocalDateTime entrada2
    LocalDateTime saida1
    LocalDateTime saida2

    static String horaAlmocoInicioPadrao = "12:00"
    static String horaAlmocoFimPadrao = "13:00"

    static Day fromLine(String line, Report r, String horaInicioPadrao, String horaFimPadrao){
        Day d = instantiateFromText(line, r)

        if (d.diaSemana){
            if (!d.entrada1) {
                d.entrada1 = LocalDateTime.parse(d.diaAsString + " " + horaInicioPadrao, Formatting.dateTimeFormatter)
            }

            if (!d.saida1){
                d.saida1 = LocalDateTime.parse(d.diaAsString + " " + horaAlmocoInicioPadrao, Formatting.dateTimeFormatter)
            }

            if (!d.entrada2){
                d.entrada2 = LocalDateTime.parse(d.diaAsString + " " + horaAlmocoFimPadrao, Formatting.dateTimeFormatter)
            }

            if (!d.saida2){
                d.saida2 = LocalDateTime.parse(d.diaAsString + " " + horaFimPadrao, Formatting.dateTimeFormatter)
            }
        }

        return d
    }

    private static Day instantiateFromText(String line, Report r){
        Day d = new Day()

        String strDia
        String strEntrada1
        String strEntrada2
        String strSaida1
        String strSaida2

        int mes = r.inicioRelatorio.monthOfYear
        int ano = r.inicioRelatorio.year
        strDia = line.substring(DIA.inicio, DIA.fim) + "." + String.format("%01d", mes) + "." + ano
        d.diaAsString = strDia
        d.dia = LocalDate.parse(strDia, Formatting.dateFormatter)

        strEntrada1 = line.substring(ENTRADA1.inicio, ENTRADA1.fim)
        if(StringUtils.isNotBlank(strEntrada1)){
            d.entrada1 = LocalDateTime.parse(strDia + " " + strEntrada1, Formatting.dateTimeFormatter)
        }

        strSaida1 = line.substring(SAIDA1.inicio, SAIDA1.fim)
        if(StringUtils.isNotBlank(strSaida1)){
            d.saida1 = LocalDateTime.parse(strDia + " " + strSaida1, Formatting.dateTimeFormatter)
        }

        strEntrada2 = line.substring(ENTRADA2.inicio, ENTRADA2.fim)
        if(StringUtils.isNotBlank(strEntrada2)){
            d.entrada2 = LocalDateTime.parse(strDia + " " + strEntrada2, Formatting.dateTimeFormatter)
        }

        strSaida2 = line.substring(SAIDA2.inicio, SAIDA2.fim)
        if(StringUtils.isNotBlank(strSaida2)){
            d.saida2 = LocalDateTime.parse(strDia + " " + strSaida2, Formatting.dateTimeFormatter)
        }

        return d
    }

    boolean isDiaSemana() {
        return (dia.getDayOfWeek() != DateTimeConstants.SATURDAY
                && dia.getDayOfWeek() != DateTimeConstants.SUNDAY)
    }

    static boolean isDay(String line){
        return (line.startsWith("|0")
                || line.startsWith("|1")
                || line.startsWith("|2")
                || line.startsWith("|3") )
    }
}
