package fill.my.stif

import org.joda.time.format.DateTimeFormatter
import org.joda.time.format.DateTimeFormat

/**
 * Created with IntelliJ IDEA.
 * User: felipe
 * Date: 11/7/13
 * Time: 0:16
 * To change this template use File | Settings | File Templates.
 */
abstract class Formatting {
    public static DateTimeFormatter dateFormatter = DateTimeFormat.forPattern("dd.MM.yyyy")
    public static DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("dd.MM.yyyy HH:mm")
    public static String TIME_PATTERN = "HH:mm"
    public static String BLANK_TIME = "     "
    public static String BLANK_SUBTYPE = "    "
    public static String JUSTIFICATIVA = "JUSTIFICATIVA"
}
