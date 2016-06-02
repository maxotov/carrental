<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8"  %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cr" uri="http://denissov.kz/carrental/jsp/jstl" %>
<fmt:bundle basename="resource.i18nText">
<html>
<head>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <title><fmt:message key="page.order.view.title"/></title>
</head>
<body>
<c:import url="../common/header.jsp" charEncoding="utf-8"/>
<div align="center">
<c:choose>
<c:when test="${not empty requestScope.order}">
<table class="order_view">
<tr>
    <td colspan="2">
        <h2><fmt:message key="page.order.label.order.view"/> <b>${requestScope.order.id}</b></h2>
    </td>
</tr>
<tr>
    <td colspan="2">
        <c:choose>
            <c:when test="${requestScope.order.status eq 'NEW'}">
                <fmt:message key="page.order.label.order.new"/>
            </c:when>
            <c:when test="${requestScope.order.status eq 'ACCEPT'}">
                <fmt:message key="page.order.label.order.accept"/>
            </c:when>
            <c:when test="${requestScope.order.status eq 'DENIED'}">
                <fmt:message key="page.order.label.order.denied"/>
            </c:when>
            <c:when test="${requestScope.order.status eq 'OPEN'}">
                <fmt:message key="page.order.label.order.open"/>
            </c:when>
            <c:when test="${requestScope.order.status eq 'CLOSE'}">
                <fmt:message key="page.order.label.order.close"/>
            </c:when>
        </c:choose>
        <br/>
        <br/>
    </td>
</tr>
<cr:checkAccess user="${sessionScope.user}" access="order.admin">
    <tr>
        <td colspan="2" align="center">
            <strong><fmt:message key="message.order.status.change"/></strong></td>
    </tr>
    <tr>
        <td colspan="2">
            <form name="frmChangStatus" action="/controller" method="POST">
                <input type="hidden" name="action" value="order_change_status">
                <input type="hidden" name="order_id" value="${requestScope.order.id}">
                <select name="order_status" onChange="frmChangStatus.submit();" class="list">
                    <option value=""></option>
                    <option value="NEW">
                        <fmt:message key="page.order.label.order.new"/>
                    </option>
                    <option value="ACCEPT">
                        <fmt:message key="page.order.label.order.accept"/>
                    </option>
                    <option value="DENIED">
                        <fmt:message key="page.order.label.order.denied"/>
                    </option>
                    <option value="OPEN">
                        <fmt:message key="page.order.label.order.open"/>
                    </option>
                    <option value="CLOSE">
                        <fmt:message key="page.order.label.order.close"/>
                    </option>
                </select>
            </form>
        </td>
    </tr>
</cr:checkAccess>
<tr align="center" valign="top">
    <td><img src="/image/car/${requestScope.order.car.photo}"/></td>
    <td>
        <b><fmt:message key="page.order.label.car.info"/></b>
        <table>
            <tr>
                <td><fmt:message key="page.cars.label.regNumber"/></td>
                <td>${requestScope.order.car.regNumber}</td>
            </tr>
            <tr>
                <td><fmt:message key="page.cars.label.name"/></td>
                <td>${requestScope.order.car.name}</td>
            </tr>
            <tr>
                <td><fmt:message key="page.cars.label.model"/></td>
                <td>${requestScope.order.car.model.name}</td>
            </tr>
            <tr>
                <td><fmt:message key="page.cars.label.vendor"/></td>
                <td>${requestScope.order.car.model.vendor.name}</td>
            </tr>
            <tr>
                <td><fmt:message key="page.cars.label.price"/></td>
                <td> ${requestScope.order.car.price}</td>
            </tr>
        </table>

    </td>
</tr>
<tr valign="top">
    <td>
        <table>
            <tr align="center">
                <td colspan=2>
                    <b><fmt:message key="page.order.label.client.info"/></b>
                </td>
            </tr>
            <tr>
                <td><fmt:message key="page.users.label.lastName"/></td>
                <td><i>${requestScope.order.client.lastName}</i></td>
            </tr>
            <tr>
                <td><fmt:message key="page.users.label.firstName"/></td>
                <td><i>${requestScope.order.client.firstName}</i></td>
            </tr>
            <tr>
                <td><fmt:message key="page.users.label.middleName"/></td>
                <td><i>${requestScope.order.client.middleName}</i></td>
            </tr>
            <tr>
                <td><fmt:message key="page.users.label.email"/></td>
                <td><i>${requestScope.order.client.email}</i></td>
            </tr>
            <tr>
                <td><fmt:message key="page.users.label.telephone"/></td>
                <td><i>${requestScope.order.client.telephone}</i></td>
            </tr>
            <tr>
                <td><fmt:message key="page.users.label.iin"/></td>
                <td><i>${requestScope.order.client.iin}</i></td>
            </tr>
            <tr>
                <td><fmt:message key="page.users.label.category"/></td>
                <td><i>${requestScope.order.client.category}</i></td>
            </tr>
        </table>
    </td>
    <td align="center">
        <table>
            <tr align="center">
                <td>
                    <b><fmt:message key="page.order.label.period"/></b>
                </td>
            </tr>
            <tr>
                <td>
                    <b><fmt:message key="page.order.label.period.begin"/></b>
                </td>
            </tr>
            <tr>
                <td>
                        ${requestScope.order.beginRent}
                </td>
            </tr>
            <tr>
                <td>
                    <b><fmt:message key="page.order.label.period.end"/></b>
                </td>
            </tr>
            <tr>
                <td>
                        ${requestScope.order.endRent}
                </td>
            </tr>
            <tr>
                <td align="center">
                    <b>
                        <fmt:message key="page.order.label.total"/><br/>
                            ${requestScope.order.price} <fmt:message key="page.order.label.currency"/>
                    </b>
                </td>
            </tr>
        </table>
    </td>

</tr>
<c:if test="${not empty requestScope.order.damages}">
    <tr>
        <td colspan="2">
            <table align="center">
                <tr>
                    <td><b><fmt:message key="page.order.label.damage.description"/></b></td>
                    <td><b><fmt:message key="page.order.label.damage.price"/></b></td>
                    <td><b><fmt:message key="page.order.label.damage.status"/></b></td>
                    <cr:checkAccess user="${sessionScope.user}" access="order.admin">
                        <td><b><fmt:message key="page.order.label.damage.status.change"/></b></td>
                    </cr:checkAccess>
                </tr>
                <c:forEach items="${requestScope.order.damages}" var="damage">
                    <tr>
                        <td valign="top">${damage.description}</td>
                        <td valign="top">${damage.price}</td>
                        <td valign="top">
                            <c:choose>
                                <c:when test="${damage.repaired}">
                                    <fmt:message key="page.order.label.damage.repaired.true"/>
                                </c:when>
                                <c:otherwise>
                                    <fmt:message key="page.order.label.damage.repaired.false"/>
                                </c:otherwise>
                            </c:choose>

                        </td>
                        <cr:checkAccess user="${sessionScope.user}" access="order.admin">
                            <td valign="top">
                                <form name="frmDamageRepaired${damage.id}" action="/controller" method="POST">
                                    <input type="hidden" name="action" value="damage_change_status">
                                    <input type="hidden" name="damage_id" value="${damage.id}">
                                    <select name="damage_status" onChange="frmDamageRepaired${damage.id}.submit();"
                                            class="list">
                                        <option></option>
                                        <option value="true"><fmt:message
                                                key="page.order.label.damage.repaired.true"/></option>
                                        <option value="false"><fmt:message
                                                key="page.order.label.damage.repaired.false"/></option>
                                    </select>
                                </form>
                            </td>
                            <td valign="top">
                                <a href="/controller?action=damage_delete&damage_id=${damage.id}">
                                    <img src="/image/icon_button_delete_small.png">
                                </a>
                            </td>
                        </cr:checkAccess>
                    </tr>
                </c:forEach>
            </table>
        </td>
    </tr>
</c:if>
<cr:checkAccess user="${sessionScope.user}" access="order.admin">
    <tr>
        <td colspan="2">
            <form name="frmDamageCreate" action="/controller">
                <input type="hidden" name="action" value="damage_create">
                <input type="hidden" name="order_id" value="${requestScope.order.id}">
                <input type="text" class="input" name="damage_description"><input type="text" class="input" name="damage_price">
                <input type="submit" value="<fmt:message key="page.order.button.damage.create"/>">
            </form>
        </td>
    </tr>
</cr:checkAccess>
</table>
</c:when>
<c:otherwise>

</c:otherwise>
</c:choose>
<c:import url="../common/footer.jsp" charEncoding="utf-8"/>
</div>
</body>
</html>
</fmt:bundle>