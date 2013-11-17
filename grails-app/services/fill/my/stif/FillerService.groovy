package fill.my.stif

import static fill.my.stif.Intervalo.*

import static fill.my.stif.Formatting.*

class FillerService {

    static transactional = false

    File fillIn(InputStream is, String horaInicioPadrao, String horaFimPadrao,
                String horaAlmocoInicioPadrao, String horaAlmocoFimPadrao) {

        ArquivoTexto texto = new ArquivoTexto(is)
        Relatorio r = new Relatorio(texto, horaInicioPadrao, horaFimPadrao, horaAlmocoInicioPadrao, horaAlmocoFimPadrao)

        return r.spit()
    }

}


