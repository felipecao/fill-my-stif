package fill.my.stif

/**
 * Created with IntelliJ IDEA.
 * User: felipe
 * Date: 11/7/13
 * Time: 21:47
 * To change this template use File | Settings | File Templates.
 */
public enum Subtipo {
    ST_2006("2006"), ATENCAO("HEY!")

    String texto

    Subtipo(String texto) {
        this.texto = texto
    }
}