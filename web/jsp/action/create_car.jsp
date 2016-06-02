<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:bundle basename="resource.i18nText">
    <html>
    <head>
        <link rel="stylesheet" type="text/css" href="/css/style.css">
        <script type="text/javascript" src="/js/jquery.js"></script>
        <script type="text/javascript" src="/js/app.js"></script>
        <title><fmt:message key="page.car.create.title"/></title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
    <%request.setCharacterEncoding("utf-8");%>
    <%response.setCharacterEncoding("utf-8");%>
    <c:import url="../common/header.jsp" charEncoding="utf-8"/>
    <div class="register">
        <fmt:message key="page.car.create.title"/>
        <br/>
        <br/>
        <form name="frmLogin" action="${pageContext.servletContext.contextPath}/addcarservlet" method="POST" enctype="multipart/form-data">
            <input type="hidden" name="action" value="car_create">
            <table border="0">
                <tr>
                    <td><fmt:message key="page.cars.label.regNumber"/></td>
                    <td><input type="text" name="regNumber" class="input"></td>
                </tr>
                <tr>
                    <td><fmt:message key="page.cars.label.vendor"/></td>
                    <td><select id="vendorType">
                        <option></option>
                        <c:forEach var="vendor" items="${vendors}">
                           <option value="${vendor.id}">${vendor.name}</option>
                        </c:forEach>
                    </select></td>
                </tr>
                <tr>
                    <td><fmt:message key="page.cars.label.model"/></td>
                    <td><select id="modelType" name="modelId">
                        <option></option>
                    </select></td>
                </tr>
                <tr>
                    <td><fmt:message key="page.cars.label.name"/></td>
                    <td><input type="text" name="carName" class="input"></td>
                </tr>
                <tr>
                    <td><fmt:message key="page.cars.label.price"/></td>
                    <td><input type="text" name="price" class="input"></td>
                </tr>
                <tr>
                    <td><fmt:message key="page.cars.label.photo"/></td>
                    <td><input type="file" name="filename"></td>
                </tr>
            </table>
            <br/>
            <input type="submit" value="<fmt:message key="page.order.button.damage.create"/>" class="button"/>
        </form>
    </div>
    <c:import url="../common/footer.jsp" charEncoding="utf-8"/>
    </body>
    </html>
</fmt:bundle>
