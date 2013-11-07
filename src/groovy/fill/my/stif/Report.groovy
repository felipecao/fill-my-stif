package fill.my.stif

import org.joda.time.LocalDate

/**
 * Created with IntelliJ IDEA.
 * User: felipe
 * Date: 11/7/13
 * Time: 0:15
 * To change this template use File | Settings | File Templates.
 */
class Report {
    LocalDate inicioRelatorio
    LocalDate fimRelatorio
    List<Day> dias = []

    static Report fromLines(List<String> lines, String horaInicioPadrao, String horaFimPadrao){
        def linhaPeriodo = lines[10]

        Report r = new Report()

        r.inicioRelatorio = LocalDate.parse(linhaPeriodo.substring(23, 33), Formatting.dateFormatter)
        r.fimRelatorio = LocalDate.parse(linhaPeriodo.substring(36, 46), Formatting.dateFormatter)

        for(i in 15..77){
            if (i % 2 == 0 && Day.isDay(lines[i])){
                r.add(Day.fromLine(lines[i], r, horaInicioPadrao, horaFimPadrao))
            }
        }

        return r
    }

    void add(Day d){
        dias << d
    }
}
