package fill.my.stif

import org.joda.time.LocalDate

import static fill.my.stif.Intervalo.ENTRADA1
import static fill.my.stif.Formatting.TIME_PATTERN
import static fill.my.stif.Formatting.BLANK_TIME
import static fill.my.stif.Intervalo.ENTRADA1
import static fill.my.stif.Intervalo.SAIDA1
import static fill.my.stif.Formatting.TIME_PATTERN
import static fill.my.stif.Formatting.BLANK_TIME
import static fill.my.stif.Intervalo.SAIDA1
import static fill.my.stif.Intervalo.ENTRADA2
import static fill.my.stif.Formatting.TIME_PATTERN
import static fill.my.stif.Formatting.BLANK_TIME
import static fill.my.stif.Intervalo.ENTRADA2
import static fill.my.stif.Intervalo.SAIDA2
import static fill.my.stif.Formatting.TIME_PATTERN
import static fill.my.stif.Formatting.BLANK_TIME
import static fill.my.stif.Intervalo.SAIDA2
import static fill.my.stif.Intervalo.SUBTIPO
import static fill.my.stif.Intervalo.SUBTIPO

class Relatorio {
    List<String> cabecalho
    List<String> rodape
    LocalDate inicioRelatorio
    LocalDate fimRelatorio
    List<Dia> dias = []

    Relatorio(ArquivoTexto texto, String horaInicioPadrao, String horaFimPadrao,
           String horaAlmocoInicioPadrao, String horaAlmocoFimPadrao) {

        cabecalho = texto.cabecalho
        rodape = texto.rodape
        inicioRelatorio = texto.inicioRelatorio
        fimRelatorio = texto.fimRelatorio

        texto.diasTrabalhados.each {
            add(new Dia(it, this, horaInicioPadrao, horaFimPadrao, horaAlmocoInicioPadrao, horaAlmocoFimPadrao))
        }
    }

    void add(Dia d){
        dias << d
    }

    File spit(){
        Saida out = new Saida()
        out.put(cabecalho)

        dias.each { dia ->
            out.append(dia.toString())
        }

        out.put(rodape)

        return out.file
    }

    String replace(String line, Dia d){
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
