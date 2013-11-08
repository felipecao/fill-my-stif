package fill.my.stif

import org.springframework.web.multipart.commons.CommonsMultipartFile
import grails.validation.Validateable

class HomeController {

    def fillerService

    def index() {
        render(view: 'create')
    }

    def upload(StifCommand cmd){

        if (cmd.hasErrors()) {
            flash.message = 'Horarios padrao devem ser informados'
            redirect(action: 'index')
            return
        }

        CommonsMultipartFile f = request.getFile('myFile')
        if (f.empty) {
            flash.message = 'Cade o arquivo do STIF?'
            render(view: 'create')
            return
        }

        File stif = fillerService.fillIn(f.inputStream, cmd.horaInicio, cmd.horaFim)
        sendFile(stif)
    }

    def sendFile(File stif){
        response.setContentType("application/octet-stream")
        response.setHeader("Content-disposition", "filename=${stif.name}")
        response.outputStream << stif.bytes
        return
    }
}

@Validateable
class StifCommand {
    String horaInicio
    String horaFim

    static constraints = {
        horaInicio(blank: false, minSize: 5)
        horaFim(blank: false, minSize: 5)
    }
}
