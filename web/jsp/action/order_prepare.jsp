<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:bundle basename="resource.i18nText">
<html>
<head>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <title><fmt:message key="page.order.prepare.title"/></title>
    <link rel="stylesheet" href="/css/ui-lightness/jquery-ui-1.9.2.custom.min.css"/>
    <script src="/js/jquery-1.8.3.js"></script>
    <script src="/js/jquery-ui-1.9.2.custom.min.js"></script>
    <script src="/js/jquery-ui-timepicker-addon.js"></script>
    <style type="text/css">
            /* css for timepicker */
        .ui-timepicker-div .ui-widget-header {
            margin-bottom: 8px;
        }

        .ui-timepicker-div dl {
            text-align: left;
        }

        .ui-timepicker-div dl dt {
            height: 25px;
            margin-bottom: -25px;
        }

        .ui-timepicker-div dl dd {
            margin: 0 10px 10px 65px;
        }

        .ui-timepicker-div td {
            font-size: 90%;
        }

        .ui-tpicker-grid-label {
            background: none;
            border: none;
            margin: 0;
            padding: 0;
        }

        .ui-timepicker-rtl {
            direction: rtl;
        }

        .ui-timepicker-rtl dl {
            text-align: right;
        }

        .ui-timepicker-rtl dl dd {
            margin: 0 65px 10px 10px;
        }
    </style>
    <script>
        $(function () {
            $.timepicker.regional['current'] = {
                currentText: '<fmt:message key="piker.now"/>',
                closeText: '<fmt:message key="piker.close"/>',
                timeFormat: 'HH:mm',
                timeSuffix: '',
                timeText: '<fmt:message key="piker.time"/>',
                hourText: '<fmt:message key="piker.hour"/>',
                minuteText: '<fmt:message key="piker.minute"/>',
                isRTL: false
            }
            $.datepicker.regional['current'] = {
                currentText: '<fmt:message key="piker.now"/>',
                closeText: '<fmt:message key="piker.close"/>',
                prevText: '<fmt:message key="piker.previous"/>',
                nextText: '<fmt:message key="piker.following"/>',
                monthNames: [
                    '<fmt:message key="piker.month.january"/>',
                    '<fmt:message key="piker.month.february"/>',
                    '<fmt:message key="piker.month.march"/>',
                    '<fmt:message key="piker.month.april"/>',
                    '<fmt:message key="piker.month.may"/>',
                    '<fmt:message key="piker.month.june"/>', ,
                    '<fmt:message key="piker.month.july"/>',
                    '<fmt:message key="piker.month.august"/>',
                    '<fmt:message key="piker.month.september"/>',
                    '<fmt:message key="piker.month.october"/>',
                    '<fmt:message key="piker.month.november"/>',
                    '<fmt:message key="piker.month.december"/>'],
                dayNamesMin: [
                    '<fmt:message key="piker.day.sunday"/>',
                    '<fmt:message key="piker.day.monday"/>',
                    '<fmt:message key="piker.day.tuesday"/>',
                    '<fmt:message key="piker.day.wednesday"/>',
                    '<fmt:message key="piker.day.thursday"/>',
                    '<fmt:message key="piker.day.friday"/>',
                    '<fmt:message key="piker.day.saturday"/>'],
                dateFormat: 'dd.mm.yy',
                firstDay: 1,
                isRTL: false,
                showMonthAfterYear: false,
                yearSuffix: ''
            };
            $.datepicker.setDefaults($.datepicker.regional['current']);
            $.timepicker.setDefaults($.timepicker.regional['current']);
            $('#beginPeriod').datetimepicker();
            $('#endPeriod').datetimepicker();
        });
    </script>
</head>
<body>
<c:import url="../common/header.jsp" charEncoding="utf-8"/>
<div align="center">
    <c:choose>
        <c:when test="${not empty sessionScope.order}">
            <form action="/controller" method="POST"/>
            <input type="hidden" name="action" value="order_send"/>
            <table class="order_prepare">
                <tr>
                    <td colspan="2">
                        <h2><fmt:message key="page.order.label.order.prepare"/></h2>
                    </td>
                </tr>
                <tr align="center" valign="top">
                    <td><img src="/image/car/${sessionScope.order.car.photo}"/></td>
                    <td>
                        <b><fmt:message key="page.order.label.car.info"/></b>
                        <table>
                            <tr>
                                <td><fmt:message key="page.cars.label.regNumber"/></td>
                                <td>${sessionScope.order.car.regNumber}</td>
                            </tr>
                            <tr>
                                <td><fmt:message key="page.cars.label.name"/></td>
                                <td>${sessionScope.order.car.name}</td>
                            </tr>
                            <tr>
                                <td><fmt:message key="page.cars.label.model"/></td>
                                <td>${sessionScope.order.car.model.name}</td>
                            </tr>
                            <tr>
                                <td><fmt:message key="page.cars.label.vendor"/></td>
                                <td>${sessionScope.order.car.model.vendor.name}</td>
                            </tr>
                            <tr>
                                <td><fmt:message key="page.cars.label.price"/></td>
                                <td> ${sessionScope.order.car.price}</td>
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
                                <td><i>${sessionScope.order.client.lastName}</i></td>
                            </tr>
                            <tr>
                                <td><fmt:message key="page.users.label.firstName"/></td>
                                <td><i>${sessionScope.order.client.firstName}</i></td>
                            </tr>
                            <tr>
                                <td><fmt:message key="page.users.label.middleName"/></td>
                                <td><i>${sessionScope.order.client.middleName}</i></td>
                            </tr>
                            <tr>
                                <td><fmt:message key="page.users.label.email"/></td>
                                <td><i>${sessionScope.order.client.email}</i></td>
                            </tr>
                            <tr>
                                <td><fmt:message key="page.users.label.telephone"/></td>
                                <td><i>${sessionScope.order.client.telephone}</i></td>
                            </tr>
                            <tr>
                                <td><fmt:message key="page.users.label.iin"/></td>
                                <td><i>${sessionScope.order.client.iin}</i></td>
                            </tr>
                            <tr>
                                <td><fmt:message key="page.users.label.category"/></td>
                                <td><i>${sessionScope.order.client.category}</i></td>
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
                                    <input id="beginPeriod" name="beginPeriod" type="text" value="" class="input">
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <b><fmt:message key="page.order.label.period.end"/></b>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <input id="endPeriod" name="endPeriod" type="text" value="" class="input">
                                </td>
                            </tr>
                        </table>
                    </td>

                </tr>
                <tr>
                    <td colspan="2">
                        <input type="submit" value="<fmt:message key="page.order.button.order.send"/>" class="button">
                    </td>
                </tr>
            </table>
            </form>

            <%--TODO view free time for rent this car--%>
            <span style="color: red; ">TODO вывод графика свободного от прокта времени, чтобы клиенту было удобнее выбирать период проката</span>
        </c:when>
        <c:otherwise>

        </c:otherwise>
    </c:choose>
</div>
</body>
</html>
</fmt:bundle>