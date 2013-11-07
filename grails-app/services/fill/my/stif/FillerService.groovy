package fill.my.stif

import org.joda.time.format.DateTimeFormat

import org.joda.time.format.DateTimeFormatter

import static fill.my.stif.Intervalo.*
import static fill.my.stif.Formatting.*

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
        println "inicio = ${r.inicioRelatorio} - fim: ${r.fimRelatorio}"

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

        sb.append(line.substring(0, ENTRADA1.inicio-1))
                .append(d.entrada1?.toLocalTime()?.toString(TIME_PATTERN))
                .append(line.substring(ENTRADA1.fim+1, SAIDA1.inicio-1))
                .append(d.saida1?.toLocalTime()?.toString(TIME_PATTERN))
                .append(line.substring(SAIDA1.fim+1, ENTRADA2.inicio-1))
                .append(d.entrada2?.toLocalTime()?.toString(TIME_PATTERN))
                .append(line.substring(ENTRADA2.fim+1, SAIDA2.inicio-1))
                .append(d.saida2?.toLocalTime()?.toString(TIME_PATTERN))
                .append(line.substring(SAIDA2.fim+1, line.length()))

        return sb.toString()
    }

}


