<%@ page import="fill.my.stif.Home" %>

<div class="fieldcontain ${hasErrors(bean: homeInstance, field: 'horaInicio', 'error')} ">
    <label for="horaInicio">
        <g:message code="home.horaInicio.label" default="Quando horario de entrada estiver em branco, preencher com ..."/>
    </label>
    <g:textField name="horaInicio" value="08:30"/>
</div>
<div class="fieldcontain ${hasErrors(bean: homeInstance, field: 'horaInicio', 'error')} ">
    <label for="horaInicio">
        <g:message code="home.horaInicio.label" default="Normalmente almoço das ..."/>
    </label>
    <g:textField name="inicioAlmoco" value="12:00"/>
</div>
<div class="fieldcontain ${hasErrors(bean: homeInstance, field: 'horaFim', 'error')} ">
    <label for="horaFim">
        <g:message code="home.horaFim.label" default="... às ..."/>
    </label>
    <g:textField name="fimAlmoco" value="13:00"/>
</div>
<div class="fieldcontain ${hasErrors(bean: homeInstance, field: 'horaFim', 'error')} ">
    <label for="horaFim">
        <g:message code="home.horaFim.label" default="Quando horario de saida estiver em branco, preencher com ..."/>
    </label>
    <g:textField name="horaFim" value="17:30"/>
</div>

