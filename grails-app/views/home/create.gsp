<%@ page import="fill.my.stif.Home" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'home.label', default: 'Home')}"/>
    <title><g:message code="default.create.label" args="[entityName]"/></title>
</head>

<body>
<a href="#create-home" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
                                                             default="Skip to content&hellip;"/></a>

<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]"/></g:link></li>
    </ul>
</div>

<div id="create-home" class="content scaffold-create" role="main">
    <h1><g:message code="default.create.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <g:hasErrors bean="${homeInstance}">
        <ul class="errors" role="alert">
            <g:eachError bean="${homeInstance}" var="error">
                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message
                        error="${error}"/></li>
            </g:eachError>
        </ul>
    </g:hasErrors>
    <g:uploadForm action="upload">
        <fieldset class="form">
            <g:render template="form"/>
        </fieldset>

        <fieldset class="form">
            <legend>Arquivo enviado pelo STIF</legend>
            <input type="file" name="myFile" />
        </fieldset>

        <fieldset class="buttons">
            <g:submitButton name="upload" class="save"
                            value="${message(code: 'default.button.create.label', default: 'Enviar')}"/>
        </fieldset>
    </g:uploadForm>
</div>
</body>
</html>
