<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:bundle basename="resource.i18nText">
    <html>
    <head>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <style type="text/css">
    </style>
    <title><fmt:message key="page.main.title"/></title>
    </head>
    <body>
    <c:import url="common/header.jsp" charEncoding="utf-8"/>
    <div align="center">
    <table width="622px">
    <tr>
    <td><img src="/image/logo_img.jpg"/></td>
    </tr>
    <tr>
    <td><fmt:message key="page.main.text.info"/></td>
    </tr>
    </table>
    </div>
    <c:import url="common/footer.jsp" charEncoding="utf-8"/>
    </body>
    </html>
</fmt:bundle>
