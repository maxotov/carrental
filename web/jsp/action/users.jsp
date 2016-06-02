<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<fmt:bundle basename="resource.i18nText">
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <html>
    <head>
        <link rel="stylesheet" type="text/css" href="/css/jquery-ui.min.css">
        <link rel="stylesheet" type="text/css" href="/css/style.css">
        <script type="text/javascript" src="/js/jquery.js"></script>
        <script type="text/javascript" src="/js/jquery-ui.js"></script>
        <script type="text/javascript" src="/js/app.js"></script>
        <title><fmt:message key="page.users.title"/></title>
    </head>
    <body>
    <c:import url="../common/header.jsp" charEncoding="utf-8"/>
    <div align="center">
        <c:if test="${not empty sessionScope.user}">
            <cr:checkAccess user="${sessionScope.user}" access="users.view">
                <c:if test="${not empty requestScope.users}">
                    <table border="1" class="table">
                        <tr>
                            <td><b><fmt:message key="page.users.label.id"/></b></td>
                            <td><b><fmt:message key="page.users.label.login"/></b></td>
                            <td><b><fmt:message key="page.users.label.lastName"/></b></td>
                            <td><b><fmt:message key="page.users.label.firstName"/></b></td>
                            <td><b><fmt:message key="page.users.label.middleName"/></b></td>
                            <td><b><fmt:message key="page.users.label.email"/></b></td>
                            <td><b><fmt:message key="page.users.label.telephone"/></b></td>
                            <td><b><fmt:message key="page.users.label.iin"/></b></td>
                            <td><b><fmt:message key="page.users.label.category"/></b></td>
                            <td><b><fmt:message key="page.users.label.access"/></b></td>
                        </tr>
                        <c:forEach items="${requestScope.users}" var="currentUser">
                            <tr>
                                <td>${currentUser.id}</td>
                                <td>${currentUser.login}</td>
                                <td>${currentUser.lastName}</td>
                                <td>${currentUser.firstName}</td>
                                <td>${currentUser.middleName}</td>
                                <td>${currentUser.email}</td>
                                <td>${currentUser.telephone}</td>
                                <td>${currentUser.iin}</td>
                                <td>${currentUser.category}</td>
                                <td>
                                    <ul>
                                    <c:forEach items="${currentUser.accesses}" var="access">
                                        <li id="${currentUser.id}_${access.id}"><c:out value="${access.name}"/>
                                            <input type="button" size="30" title="<fmt:message key="page.user.delete.access"/>" class="delete_access" onclick="dialog_delete(${access.id},${currentUser.id})"/></li>
                                    </c:forEach>
                                        </ul>
                                    <p align="center"><button class="button" onclick="open_dialog(${currentUser.id})"><fmt:message key="page.user.add.access"/></button></p>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </c:if>
            </cr:checkAccess>
        </c:if>
    </div>
    <div id="dialog" title="<fmt:message key="page.user.add.access"/>" style="display: none;">
        <form action="/controller" method="post">
            <fieldset>
                <input type="hidden" name="action" value="add_access">
                <input type="hidden" name="id_user" id="id_user"/>
                <label><fmt:message key="page.user.select.access"/></label>
                <select name="id_access">
                    <c:forEach items="${accesses}" var="access">
                        <option value="${access.id}">${access.name}</option>
                    </c:forEach>
                </select>
                <input type="submit" value="<fmt:message key="page.user.add.access"/>"/>
            </fieldset>
        </form>
    </div>
    <div id="dialog_delete" title="<fmt:message key="page.user.delete.access"/>" style="display: none;">
        <form action="/controller" method="post">
                <input type="hidden" name="action" value="delete_access">
                <input type="hidden" name="id_user" id="idUser"/>
                <input type="hidden" name="id_access" id="idAccess"/>
                <p><fmt:message key="page.user.delete.access.question"/></p>
                <input type="submit" value="<fmt:message key="page.user.delete.access.answer"/>"/>
        </form>
    </div>
    <c:import url="../common/footer.jsp" charEncoding="utf-8"/>
    </body>
    </html>
</fmt:bundle>