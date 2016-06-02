<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:bundle basename="resource.i18nText">
    <html>
    <head>
        <link rel="stylesheet" type="text/css" href="/css/style.css">
        <title><fmt:message key="page.cars.title"/></title>
    </head>
    <body>
    <c:import url="../common/header.jsp" charEncoding="utf-8"/>
    <div align="center">
        <c:if test="${not empty requestScope.cars}">
            <table border="0">
                <c:forEach items="${requestScope.cars}" var="currentCar">
                    <tr class="car">
                        <td>
                            <a href="/controller?action=order_prepare&car_id=${currentCar.id}">
                                <img src="/image/car/${currentCar.photo}"/>
                            </a>
                        </td>
                        <td valign="top">
                            <table>
                                <tr>
                                    <td><b><fmt:message key="page.cars.label.name"/></b></td>
                                    <td><b>${currentCar.name}</b></td>
                                </tr>
                                <tr>
                                    <td><fmt:message key="page.cars.label.regNumber"/></td>
                                    <td>${currentCar.regNumber}</td>
                                </tr>
                                <tr>
                                    <td><fmt:message key="page.cars.label.model"/></td>
                                    <td>${currentCar.model.name}</td>
                                </tr>
                                <tr>
                                    <td><fmt:message key="page.cars.label.vendor"/></td>
                                    <td>${currentCar.model.vendor.name}</td>
                                </tr>
                                <tr>
                                    <td><fmt:message key="page.cars.label.price"/></td>
                                    <td> ${currentCar.price}</td>
                                </tr>
                                <tr>
                                    <td colspan="2" align="left">
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </c:if>
    </div>
    <c:import url="../common/footer.jsp" charEncoding="utf-8"/>
    </body>
    </html>
</fmt:bundle>