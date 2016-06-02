<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:bundle basename="resource.i18nText">
    <html>
    <head>
        <link rel="stylesheet" type="text/css" href="/css/style.css">
        <title><fmt:message key="page.login.title"/></title>
    </head>
    <body>
    <c:import url="../common/header.jsp" charEncoding="utf-8"/>
    <div class="login">
        <fmt:message key="page.login.label.welcome"/>
        <br/>
        <br/>
        <form name="frmLogin" action="/controller" method="POST" >
            <input type="hidden" name="action" value="login">
            <fmt:message key="page.login.label.login"/>  <input type="text" name="login" class="input">
            <fmt:message key="page.login.label.password"/>  <input type="password" name="password" class="input">
            <br/>
            <br/>
            <input type="submit" value="<fmt:message key="page.login.button.login"/>" class="button"/>
        </form>
    </div>
    <c:import url="../common/footer.jsp" charEncoding="utf-8"/>
    </body>
    </html>
</fmt:bundle>