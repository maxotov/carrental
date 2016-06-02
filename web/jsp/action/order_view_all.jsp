<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
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
        <c:when test="${not empty requestScope.orders}">

        <c:forEach items="${requestScope.orders}" var="order">
        <a href="/controller?action=order_view&order_id=${order.id}">
            <table width="80%" border="0" align="center" cellpadding="1" bgcolor="#94d2ff" class="order_view_all">
                <tr>
                    <td width="14%"><strong>
                        <fmt:message key="page.order.label.order.number"/>
                    </strong></td>
                    <td width="21%">${order.id}</td>
                    <td width="16%"><strong>
                        <fmt:message key="page.cars.label.name"/>
                    </strong></td>
                    <td width="33%">${order.car.name}</td>
                    <td width="33%" rowspan="5">
                        <cr:checkAccess user="${sessionScope.user}" access="order.admin">
                            <a href="/controller?action=order_delete&order_id=${order.id}">
                                <img src="/image/icon_button_delete.png">
                            </a>
                        </cr:checkAccess>
                    </td>
                </tr>
                <tr>
                    <td><strong>
                        <fmt:message key="page.order.label.period.begin"/>
                    </strong></td>
                    <td>${order.beginRent}</td>
                    <td><strong>
                        <fmt:message key="page.cars.label.model"/>
                    </strong></td>
                    <td>${order.car.model.name}</td>
                </tr>
                <tr>
                    <td><strong>
                        <fmt:message key="page.order.label.period.end"/>
                    </strong></td>
                    <td>${order.endRent}</td>
                    <td><strong>
                        <fmt:message key="page.cars.label.vendor"/>
                    </strong></td>
                    <td>${order.car.model.vendor.name}</td>
                </tr>
                <tr>
                    <td><strong>
                        <fmt:message key="page.order.label.order.create.time"/>
                    </strong></td>
                    <td>${order.createTime}</td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                </tr>
                <tr>
                    <td><strong>
                        <fmt:message key="page.order.label.order.status"/>
                    </strong></td>
                    <td colspan="3">
                        <c:choose>
                            <c:when test="${order.status eq 'NEW'}">
                                <fmt:message key="page.order.label.order.new"/>
                            </c:when>
                            <c:when test="${order.status eq 'ACCEPT'}">
                                <fmt:message key="page.order.label.order.accept"/>
                            </c:when>
                            <c:when test="${order.status eq 'DENIED'}">
                                <fmt:message key="page.order.label.order.denied"/>
                            </c:when>
                            <c:when test="${order.status eq 'OPEN'}">
                                <fmt:message key="page.order.label.order.open"/>
                            </c:when>
                            <c:when test="${order.status eq 'CLOSE'}">
                                <fmt:message key="page.order.label.order.close"/>
                            </c:when>
                        </c:choose>
                    </td>
                </tr>
                <cr:checkAccess user="${sessionScope.user}" access="order.admin">
                    <tr>
                        <td colspan="5">
                            <p>${order.client.lastName} ${order.client.firstName} ${order.client.middleName} ${order.client.telephone} ${order.client.email} </p>
                        </td>
                    </tr>
                </cr:checkAccess>
                <tr>
                    <td>
                        <c:if test="${not empty order.damages}">
                            <img src="/image/icon_damage.gif"/>
                        </c:if>
                    </td>
                    <td>&nbsp;</td>
                    <td></td>
                    <td><strong>
                        <fmt:message key="page.order.label.total"/>
                    </strong></td>
                    <td><strong>${order.price}</strong> <fmt:message key="page.order.label.currency"/></td>
                </tr>
            </table>
        </a>
        <br>
        <br>
        </c:forEach>
        </c:when>

        <c:otherwise>
            <fmt:message key="message.info.you.do.not.have.orders"/>
        </c:otherwise>
        </c:choose>
            <c:import url="../common/footer.jsp" charEncoding="utf-8"/>
    </body>
    </body>
    </html>
</fmt:bundle>