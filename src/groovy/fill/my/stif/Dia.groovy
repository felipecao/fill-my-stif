package fill.my.stif

import org.joda.time.LocalDate
import org.joda.time.LocalDateTime
import org.apache.commons.lang.StringUtils
import org.joda.time.DateTimeConstants
import static fill.my.stif.Intervalo.*
import static fill.my.stif.Subtipo.ST_2006
import static fill.my.stif.Subtipo.ATENCAO
import static fill.my.stif.Formatting.*

class Dia {
    String numeroDia = ""
    String siglaDia = ""
    String escala = ""
    String difPht = ""
    String subtipo = ""
    String aAjustar = ""
    String difPht2 = "       "
    String subtipo2 = "       "
    String aAjustar2 = "         "
    String balanco = ""
    String pa = "   "
    String obs = "   "
    String peso = "     "
    String regime = "01"

    LocalDate dia
    String diaAsString
    LocalDateTime entrada1
    LocalDateTime entrada2
    LocalDateTime saida1
    LocalDateTime saida2

    Dia(String line, Relatorio r, String horaInicioPadrao, String horaFimPadrao,
                        String horaAlmocoInicioPadrao, String horaAlmocoFimPadrao){

        instantiateFromText(line, r)

        if (diaSemana){
            if (!entrada1) {
                entrada1 = LocalDateTime.parse(diaAsString + " " + horaInicioPadrao, Formatting.dateTimeFormatter)
            }

            if (!saida1){
                saida1 = LocalDateTime.parse(diaAsString + " " + horaAlmocoInicioPadrao, Formatting.dateTimeFormatter)
            }

            if (!entrada2){
                entrada2 = LocalDateTime.parse(diaAsString + " " + horaAlmocoFimPadrao, Formatting.dateTimeFormatter)
            }

            if (!saida2){
                saida2 = LocalDateTime.parse(diaAsString + " " + horaFimPadrao, Formatting.dateTimeFormatter)
            }
        }
    }

    private void instantiateFromText(String line, Relatorio r){

        String strDia
        String strEntrada1
        String strEntrada2
        String strSaida1
        String strSaida2

        int mes = r.inicioRelatorio.monthOfYear
        int ano = r.inicioRelatorio.year

        strDia = line.substring(DIA.inicio, DIA.fim) + "." + String.format("%01d", mes) + "." + ano
        diaAsString = strDia
        dia = LocalDate.parse(strDia, Formatting.dateFormatter)

        strEntrada1 = line.substring(ENTRADA1.inicio, ENTRADA1.fim)
        if(StringUtils.isNotBlank(strEntrada1)){
            entrada1 = LocalDateTime.parse(strDia + " " + strEntrada1, Formatting.dateTimeFormatter)
        }

        strSaida1 = line.substring(SAIDA1.inicio, SAIDA1.fim)
        if(StringUtils.isNotBlank(strSaida1)){
            saida1 = LocalDateTime.parse(strDia + " " + strSaida1, Formatting.dateTimeFormatter)
        }

        strEntrada2 = line.substring(ENTRADA2.inicio, ENTRADA2.fim)
        if(StringUtils.isNotBlank(strEntrada2)){
            entrada2 = LocalDateTime.parse(strDia + " " + strEntrada2, Formatting.dateTimeFormatter)
        }

        strSaida2 = line.substring(SAIDA2.inicio, SAIDA2.fim)
        if(StringUtils.isNotBlank(strSaida2)){
            saida2 = LocalDateTime.parse(strDia + " " + strSaida2, Formatting.dateTimeFormatter)
        }

        numeroDia = line.substring(DIA.inicio, DIA.fim)
        siglaDia = line.substring(SIGLA_DIA.inicio, SIGLA_DIA.fim)
        escala = line.substring(ESCALA.inicio, ESCALA.fim)
        difPht = line.substring(DIF_PHT.inicio, DIF_PHT.fim)
        aAjustar = line.substring(A_AJUSTAR.inicio, A_AJUSTAR.fim)
        balanco = line.substring(BALANCO.inicio, BALANCO.fim)
        subtipo = subTipoDoDia(this)

    }

    private boolean isDiaSemana() {
        return (dia.getDayOfWeek() != DateTimeConstants.SATURDAY
                && dia.getDayOfWeek() != DateTimeConstants.SUNDAY)
    }

    static boolean isDay(String line){
        return (line.startsWith("|0")
                || line.startsWith("|1")
                || line.startsWith("|2")
                || line.startsWith("|3") )
    }

    private static String subTipoDoDia(Dia d){

        if (!d.diaSemana){
            return BLANK_SUBTYPE
        }

        if(!d.entrada1 && !d.saida1 && !d.entrada2 && !d.saida2){
            return ST_2006.texto
        }

        return ATENCAO.texto
    }

    @Override
    public String toString() {
        return  "|${numeroDia}|${siglaDia}|${escala}" +
                "| ${entrada1 ? entrada1?.toLocalTime()?.toString(TIME_PATTERN) : BLANK_TIME} " +
                "| ${saida1   ? saida1?.toLocalTime()?.toString(TIME_PATTERN)   : BLANK_TIME} " +
                "| ${entrada2 ? entrada2?.toLocalTime()?.toString(TIME_PATTERN) : BLANK_TIME} " +
                "| ${saida2   ? saida2?.toLocalTime()?.toString(TIME_PATTERN)   : BLANK_TIME} " +
                "| ${difPht}  " +
                "| ${subtipo}  " +
                "|  ${aAjustar}  " +
                "|${difPht2}|${subtipo2}|${aAjustar2}|${balanco}|${pa}|${obs}|${peso}|${regime}|";
    }
}
