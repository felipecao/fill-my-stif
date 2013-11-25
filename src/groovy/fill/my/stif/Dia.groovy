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
    LocalDateTime inicioAlmoco
    LocalDateTime fimAlmoco
    LocalDateTime inicioPadrao
    LocalDateTime fimPadrao

    Relatorio relatorio

    Dia(String line, Relatorio r, String horaInicioPadrao, String horaFimPadrao,
                        String horaAlmocoInicioPadrao, String horaAlmocoFimPadrao){

        relatorio = r
        instantiateFromText(line, r)

        if (ehDiaUtilSemEntradas()){
            entrada1 = LocalDateTime.parse(diaAsString + " " + horaInicioPadrao, Formatting.dateTimeFormatter)
            saida1 = LocalDateTime.parse(diaAsString + " " + horaAlmocoInicioPadrao, Formatting.dateTimeFormatter)
            entrada2 = LocalDateTime.parse(diaAsString + " " + horaAlmocoFimPadrao, Formatting.dateTimeFormatter)
            saida2 = LocalDateTime.parse(diaAsString + " " + horaFimPadrao, Formatting.dateTimeFormatter)
        }

        inicioPadrao = LocalDateTime.parse(diaAsString + " " + horaInicioPadrao, Formatting.dateTimeFormatter)
        fimPadrao = LocalDateTime.parse(diaAsString + " " + horaFimPadrao, Formatting.dateTimeFormatter)
        inicioAlmoco = LocalDateTime.parse(diaAsString + " " + horaAlmocoInicioPadrao, Formatting.dateTimeFormatter)
        fimAlmoco = LocalDateTime.parse(diaAsString + " " + horaAlmocoFimPadrao, Formatting.dateTimeFormatter)
    }

    private boolean ehDiaUtilSemEntradas(){
        return (diaSemana && !entrada1 && !entrada2 && !saida1 && !saida2)
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

    private List<String> getNotas(){

        if (ehDiaUtilSemEntradas()){ // so preciso gerar notas para dias em que houve alguma entrada / saida registrada
            return []
        }

        def notas = []

        if (entrada1 && !saida1 && !entrada2 && !saida2){

            // ex.: |06|D|LIVR| 07:42 |       |       |       |
            if (entrada1.isBefore(inicioPadrao)){
                notas << "${format(entrada1)}-${format(inicioAlmoco)} - ${JUSTIFICATIVA}"
                notas << "${format(fimAlmoco)}-${format(fimPadrao)} - ${JUSTIFICATIVA}"
            }

            // ex.: |06|D|LIVR| 09:42 |       |       |       |
            else if (entrada1.isAfter(inicioPadrao) && entrada1.isBefore(inicioAlmoco)){
                notas << "${format(inicioPadrao)}-${format(entrada1)} - ${JUSTIFICATIVA}"
                notas << "${format(entrada1)}-${format(inicioAlmoco)} - ${JUSTIFICATIVA}"
                notas << "${format(fimAlmoco)}-${format(fimPadrao)} - ${JUSTIFICATIVA}"
            }

            // ex.: |06|D|LIVR| 12:35 |       |       |       |
            else if (entrada1.isAfter(inicioPadrao) && entrada1.isAfter(inicioAlmoco) && entrada1.isBefore(fimAlmoco)){
                notas << "${format(inicioPadrao)}-${format(inicioAlmoco)} - ${JUSTIFICATIVA}"
                notas << "${format(entrada1)}-${format(fimPadrao)} - ${JUSTIFICATIVA}"
            }

            // ex.: |06|D|LIVR| 15:42 |       |       |       |
            else if (entrada1.isAfter(inicioPadrao) && entrada1.isAfter(inicioAlmoco) && entrada1.isAfter(fimAlmoco) && entrada1.isBefore(fimPadrao)){
                notas << "${format(inicioPadrao)}-${format(inicioAlmoco)} - ${JUSTIFICATIVA}"
                notas << "${format(fimAlmoco)}-${format(entrada1)} - ${JUSTIFICATIVA}"
                notas << "${format(entrada1)}-${format(fimPadrao)} - ${JUSTIFICATIVA}"
            }

            // ex.: |06|D|LIVR| 19:42 |       |       |       |
            else if (entrada1.isAfter(inicioPadrao) && entrada1.isAfter(inicioAlmoco) && entrada1.isAfter(fimAlmoco) && entrada1.isAfter(fimPadrao)){
                notas << "${format(inicioPadrao)}-${format(inicioAlmoco)} - ${JUSTIFICATIVA}"
                notas << "${format(fimAlmoco)}-${format(entrada1)} - ${JUSTIFICATIVA}"
            }

        }

        if (entrada1 && saida1 && !entrada2 && !saida2){

            // ex.: |03|Q|HL05| 07:42 | 13:34 |       |       |
            if (entrada1.isBefore(inicioPadrao)){
                // do nothing
            }

            // ex.: |06|D|LIVR| 09:42 | 18:42 |       |       |
            if (entrada1.isAfter(inicioPadrao)){
                notas << "${format(inicioPadrao)}-${format(entrada1)} - ${JUSTIFICATIVA}"
            }

            // ex.: |06|D|LIVR| 09:42 | 15:42 |       |       |
            if (saida1.isBefore(fimPadrao)){
                notas << "${format(saida1)}-${format(fimPadrao)} - ${JUSTIFICATIVA}"
            }

            // ex.: |06|D|LIVR| 09:42 | 19:42 |       |       |
            if (saida1.isAfter(fimPadrao)){
                // do nothing
            }

        }

        return notas
    }

    private String subTipoDoDia(Dia d){

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
        def ret =  "|${numeroDia}|${siglaDia}|${escala}" +
                "| ${format(entrada1)} " +
                "| ${format(saida1)} " +
                "| ${format(entrada2)} " +
                "| ${format(saida2)} " +
                "| ${difPht}  " +
                "| ${subtipo}  " +
                "|  ${aAjustar}  " +
                "|${difPht2}|${subtipo2}|${aAjustar2}|${balanco}|${pa}|${obs}|${peso}|${regime}|"

        if (notas){
            ret += "\n"
            notas.each {
                ret += it + "\n"
            }
        }

        return ret
    }

    private String format(def dateTime){
        return dateTime ? dateTime?.toLocalTime()?.toString(TIME_PATTERN) : BLANK_TIME
    }
}
