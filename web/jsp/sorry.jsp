<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:bundle basename="resource.i18nText">
    <html>
    <head>
        <link rel="stylesheet" type="text/css" href="/css/style.css">
        <title><fmt:message key="page.sorry.title"/></title>
    </head>
    <body>
    <c:import url="common/header.jsp" charEncoding="utf-8"/>
    <div class="warning">
        <fmt:message key="message.error.application"/>
        <br/>
        <img src="/image/img_sorry.png">
        <br/>
        <a href="javascript:document.location.reload();">
            <fmt:message key="page.sorry.text.try.again"/>
        </a>
    </div>
    <c:import url="common/footer.jsp" charEncoding="utf-8"/>
    </body>
    </html>
</fmt:bundle>