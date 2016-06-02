<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cr" uri="http://denissov.kz/carrental/jsp/jstl" %>
<fmt:setBundle basename="resource.i18nText" var="text"/>
<div align="center">
    <table width="100%" border="0">
        <tr>
            <td width="15%">
                <c:choose>
                    <c:when test="${not empty sessionScope.user}">
                        ${sessionScope.user.lastName} ${sessionScope.user.firstName} ${sessionScope.user.middleName}
                        <br/>
                        <a href="/controller?action=logout" class="button"><fmt:message key="header.link.text.logout"
                                                                         bundle="${text}"/></a>
                    </c:when>
                    <c:otherwise>
                        <a href="/controller?action=login" class="button"><fmt:message key="header.link.text.login"
                                                                        bundle="${text}"/></a>
                        <a href="/controller?action=user_create" class="button"><fmt:message key="page.user.create.title"
                                                                                       bundle="${text}"/></a>
                    </c:otherwise>
                </c:choose>
            </td>
            <td width="70%" align="center">
                <a href="/controller" class="button"><fmt:message key="header.link.text.main" bundle="${text}"/></a>
                <a href="/controller?action=car_view" class="button"><fmt:message key="header.link.text.cars" bundle="${text}"/></a>
                <a href="/controller?action=contact_view" class="button"><fmt:message key="header.link.text.contact"
                                                                       bundle="${text}"/></a>
                <c:if test="${not empty sessionScope.user}">
                    <cr:checkAccess user="${sessionScope.user}" access="users.view">
                        <a href="/controller?action=user_view" class="button"><fmt:message key="header.link.text.users"
                                                                            bundle="${text}"/></a>
                    </cr:checkAccess>
                    <cr:checkAccess user="${sessionScope.user}" access="order.make">
                        <a href="/controller?action=order_view_all" class="button"><fmt:message key="header.link.text.order"
                                                                             bundle="${text}"/></a>
                    </cr:checkAccess>
                    <cr:checkAccess user="${sessionScope.user}" access="order.admin">
                        <a href="/actionservlet?action=car_create" class="button"><fmt:message key="header.link.text.car.add"
                                                                                                bundle="${text}"/></a>
                    </cr:checkAccess>
                </c:if>
            </td>
            <td width="15%" align="right">
                <fmt:bundle basename="resource.languages">
                    <form name="frmLanguage" action="/controller" method="POST">
                        <input type="hidden" name="action" value="change_language">
                        <select name="language" onchange="frmLanguage.submit();" class="button">
                            <option value=""><fmt:message key="header.link.text.change.language" bundle="${text}"/></option>
                            <option value="ru">
                                <fmt:message key="language.ru"/>
                            </option>
                            <option value="en">
                                <fmt:message key="language.en"/>
                            </option>
                            <option value="kk">
                                <fmt:message key="language.kk"/>
                            </option>
                        </select>
                    </form>
                </fmt:bundle>
            </td>
        </tr>
    </table>
    <br/>
    <c:if test="${not empty requestScope.timeMessageInfo}">
        <div align="center" class="info" id="timeMessageInfo">
            <fmt:message key="${requestScope.timeMessageInfo}" bundle="${text}"/>
        </div>
        <script type="text/javascript" src="/js/timer.js"></script>
        <script type="text/javascript" language="javascript">
            hideAfter("timeMessageInfo", 3000);
        </script>
    </c:if>
    <c:if test="${not empty requestScope.timeMessageError}">
        <div align="center" class="error" id="timeMessageError">
            <fmt:message key="${requestScope.timeMessageError}" bundle="${text}"/>
        </div>
        <script type="text/javascript" src="/js/timer.js"></script>
        <script type="text/javascript" language="javascript">
            hideAfter("timeMessageError", 5000);
        </script>
    </c:if>
</div>