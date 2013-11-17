package fill.my.stif

import org.joda.time.LocalDate

import static fill.my.stif.Intervalo.DATA_INICIO_RELATORIO
import static fill.my.stif.Intervalo.DATA_INICIO_RELATORIO
import static fill.my.stif.Intervalo.DATA_FIM_RELATORIO
import static fill.my.stif.Intervalo.DATA_FIM_RELATORIO
import static fill.my.stif.Intervalo.DIAS_TRABALHADOS
import static fill.my.stif.Intervalo.DIAS_TRABALHADOS
import static fill.my.stif.Intervalo.CABECALHO
import static fill.my.stif.Intervalo.RODAPE

/**
 * Created with IntelliJ IDEA.
 * User: felipe
 * Date: 11/17/13
 * Time: 17:31
 * To change this template use File | Settings | File Templates.
 */
class ArquivoTexto {

    final static String SEPARADOR_DIAS = "\n------------------------------------------------------------------------------------------------------------------------"

    private List<String> linhas

    ArquivoTexto(InputStream is) {
        linhas = streamAsList(is)
    }

    private List<String> streamAsList(InputStream is){
        def reader = new BufferedReader(new InputStreamReader(is))
        def list = []
        reader.eachLine { line ->
            list << line
        }
        return list
    }

    LocalDate getInicioRelatorio(){
        def linhaPeriodo = linhas[10]

        return LocalDate.parse(linhaPeriodo.substring(DATA_INICIO_RELATORIO.inicio, DATA_INICIO_RELATORIO.fim),
                Formatting.dateFormatter)
    }

    LocalDate getFimRelatorio(){
        def linhaPeriodo = linhas[10]

        return LocalDate.parse(linhaPeriodo.substring(DATA_FIM_RELATORIO.inicio, DATA_FIM_RELATORIO.fim),
                Formatting.dateFormatter)
    }

    List<String> getDiasTrabalhados(){
        def conjunto = []

        for(i in DIAS_TRABALHADOS.inicio..DIAS_TRABALHADOS.fim){
            if (i % 2 == 0 && Dia.isDay(linhas[i])){
                conjunto << linhas[i]
            }
        }

        return conjunto
    }

    List<String> getCabecalho(boolean quebrarLinhas = true){
        getLines(CABECALHO, quebrarLinhas)
    }

    List<String> getRodape(boolean quebrarLinhas = true){
        getLines(RODAPE, quebrarLinhas)
    }

    private List<String> getLines(def intervalo, boolean quebrarLinhas = true){
        def conjunto = []

        for(i in intervalo.inicio..intervalo.fim){
            conjunto << linhas[i] + (quebrarLinhas ? "\n" : "")
        }

        return conjunto
    }


}
