<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:bundle basename="resource.i18nText">
    <html>
    <head>
        <link rel="stylesheet" type="text/css" href="/css/jquery-ui.min.css">
        <link rel="stylesheet" type="text/css" href="/css/style.css">
        <script type="text/javascript" src="/js/jquery.js"></script>
        <script type="text/javascript" src="/js/jquery-ui.js"></script>
        <script type="text/javascript" src="/js/app.js"></script>
        <title><fmt:message key="page.user.create.title"/></title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
    <%request.setCharacterEncoding("utf-8");%>
    <%response.setCharacterEncoding("utf-8");%>
    <c:import url="../common/header.jsp" charEncoding="utf-8"/>
    <div class="register">
        <fmt:message key="page.login.label.welcome"/>
        <br/>
        <br/>
        <form name="frmLogin" action="/controller" method="POST" >
            <input type="hidden" name="action" value="user_create">
            <table border="0">
            <tr>
                <td><fmt:message key="page.login.label.login"/></td>
                <td><input type="text" name="login" class="input"></td>
            </tr>
            <tr>
                    <td><fmt:message key="page.login.label.password"/></td>
                    <td><input type="password" name="password" class="input"></td>
            </tr>
                <tr>
                    <td><fmt:message key="page.users.label.firstName"/></td>
                    <td><input type="text" name="firstName" class="input"></td>
                </tr>
                <tr>
                    <td><fmt:message key="page.users.label.lastName"/></td>
                    <td><input type="text" name="lastName" class="input"></td>
                </tr>
                <tr>
                    <td><fmt:message key="page.users.label.middleName"/></td>
                    <td><input type="text" name="middleName" class="input"></td>
                </tr>
                <tr>
                    <td>Email</td>
                    <td><input type="text" name="email" class="input"></td>
                </tr>
                <tr>
                    <td><fmt:message key="page.users.label.telephone"/></td>
                    <td><input type="text" name="telephone" placeholder="7775552299" class="input"></td>
                </tr>
                <tr>
                    <td><fmt:message key="page.users.label.iin"/></td>
                    <td><input type="text" name="iin" class="input"></td>
                </tr>
                <tr>
                    <td><fmt:message key="page.users.label.category"/></td>
                    <td><input type="text" name="category" class="input"><img src="/image/help.png" width="16" height="16" id="help" /></td>
                </tr>
                </table>
            <br/>
            <input type="submit" value="<fmt:message key="page.user.create.title"/>" class="button"/>
        </form>
        <div id="dialogHelp" style="display: none;" title="<fmt:message key="message.info.client.notice"/>">
            <p><fmt:message key="message.info.client.prava"/></p>
        </div>
    </div>
    <c:import url="../common/footer.jsp" charEncoding="utf-8"/>
    </body>
    </html>
</fmt:bundle>
