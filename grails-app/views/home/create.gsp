<%@ page import="fill.my.stif.Home" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
</head>

<body>

<div id="create-home" class="content scaffold-create" role="main">
    <h1><g:message code="porfavor.preencha" /></h1>
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
            <g:submitButton name="upload" class="save" value="${message(code: 'mandaver')}"/>
        </fieldset>
    </g:uploadForm>
</div>
</body>
</html>
