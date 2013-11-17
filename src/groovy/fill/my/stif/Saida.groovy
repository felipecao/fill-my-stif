package fill.my.stif

/**
 * Created with IntelliJ IDEA.
 * User: felipe
 * Date: 11/17/13
 * Time: 17:33
 * To change this template use File | Settings | File Templates.
 */
class Saida {
    File fileOut
    FileWriter outFile
    PrintWriter out

    Saida() {
        fileOut = new File("stif.txt")
        outFile = new FileWriter(fileOut)
        out = new PrintWriter(outFile)
    }

    void append(String line){
        out.println(line + ArquivoTexto.SEPARADOR_DIAS)
    }

    void put(List<String> conteudo){
        conteudo.each {
            out.print(it)
        }
    }

    File getFile(){
        out.close()
        return fileOut
    }
}
