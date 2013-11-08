package fill.my.stif

import org.joda.time.format.DateTimeFormat

import org.joda.time.format.DateTimeFormatter

import static fill.my.stif.Intervalo.*
import static fill.my.stif.Subtipo.*
import static fill.my.stif.Formatting.*
import org.apache.commons.lang.StringUtils

class FillerService {

    static transactional = false

    File fillIn(InputStream is, String horaInicioPadrao, String horaFimPadrao) {
        List<String> lines = streamAsList(is)
        def linhasDebug = []
        File fileOut = new File("stif.txt")
        FileWriter outFile = new FileWriter(fileOut);
        PrintWriter out = new PrintWriter(outFile);
        Report r = Report.fromLines(lines, horaInicioPadrao, horaFimPadrao)
        int cnt = 0

        lines.each { ln ->

            if(Day.isDay(ln)){
                ln = replace(ln, r.dias[cnt++])
            }

            out.println(ln)
            linhasDebug << ln
        }

        out.close()

        return fileOut
    }

    List<String> streamAsList(InputStream is){
        def reader = new BufferedReader(new InputStreamReader(is))
        def list = []
        reader.eachLine { line ->
            list << line
        }
        return list
    }

    String replace(String line, Day d){
        StringBuilder sb = new StringBuilder();

        sb.append(line.substring(0, ENTRADA1.inicio))
                .append(d.entrada1 ? d.entrada1?.toLocalTime()?.toString(TIME_PATTERN) : BLANK_TIME)
                .append(line.substring(ENTRADA1.fim, SAIDA1.inicio))
                .append(d.saida1 ? d.saida1?.toLocalTime()?.toString(TIME_PATTERN) : BLANK_TIME)
                .append(line.substring(SAIDA1.fim, ENTRADA2.inicio))
                .append(d.entrada2 ? d.entrada2?.toLocalTime()?.toString(TIME_PATTERN) : BLANK_TIME)
                .append(line.substring(ENTRADA2.fim, SAIDA2.inicio))
                .append(d.saida2 ? d.saida2?.toLocalTime()?.toString(TIME_PATTERN) : BLANK_TIME)
                .append(line.substring(SAIDA2.fim, SUBTIPO.inicio))
                .append(d.subtipo)
                .append(line.substring(SUBTIPO.fim, line.length()))

        return sb.toString()
    }

}


