package fill.my.stif

/**
 * Created with IntelliJ IDEA.
 * User: felipe
 * Date: 11/7/13
 * Time: 0:38
 * To change this template use File | Settings | File Templates.
 */
enum Intervalo {

    DATA_INICIO_RELATORIO(23, 33),
    DATA_FIM_RELATORIO(36, 46),
    DIA(1, 3),
    SIGLA_DIA(4, 5),
    ESCALA(6, 10),
    ENTRADA1(12, 17),
    SAIDA1(20, 25),
    ENTRADA2(28, 33),
    SAIDA2(36, 41),
    DIF_PHT(44, 49),
    SUBTIPO(53, 57),
    A_AJUSTAR(62, 67),
    BALANCO(96, 102),
    DIAS_TRABALHADOS(15, 77),
    CABECALHO(0, DIAS_TRABALHADOS.inicio),
    RODAPE(DIAS_TRABALHADOS.fim, 135)

    int inicio
    int fim

    private Intervalo(int inicio, int fim) {
        this.inicio = inicio
        this.fim = fim
    }
}
