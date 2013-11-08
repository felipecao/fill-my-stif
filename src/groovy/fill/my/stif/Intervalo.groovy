package fill.my.stif

/**
 * Created with IntelliJ IDEA.
 * User: felipe
 * Date: 11/7/13
 * Time: 0:38
 * To change this template use File | Settings | File Templates.
 */
class Intervalo {
    int inicio
    int fim

    Intervalo(int inicio, int fim) {
        this.inicio = inicio
        this.fim = fim
    }

    public static Intervalo DIA = new Intervalo(1, 3)
    public static Intervalo ENTRADA1 = new Intervalo(12, 17)
    public static Intervalo SAIDA1 = new Intervalo(20, 25)
    public static Intervalo ENTRADA2 = new Intervalo(28, 33)
    public static Intervalo SAIDA2 = new Intervalo(36, 41)
    public static Intervalo DATA_INICIO_RELATORIO = new Intervalo(23, 33)
    public static Intervalo DATA_FIM_RELATORIO = new Intervalo(36, 46)
    public static Intervalo SUBTIPO = new Intervalo(53, 57)
}
