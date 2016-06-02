<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:bundle basename="resource.i18nText">
<html>
<head>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <script type="text/javascript" src="/js/jquery.js"></script>
    <script type="text/javascript" src="js/map_api.js"></script>
    <script type="text/javascript">
        function loadMap(lat, lng, name) {
            var latlng = new google.maps.LatLng(lat, lng);
            var myOptions = {
                zoom: 13,
                center: latlng,
                mapTypeId: google.maps.MapTypeId.ROADMAP,
                mapTypeControlOptions: {
                    style: google.maps.MapTypeControlStyle.DROPDOWN_MENU,
                    position: google.maps.ControlPosition.TOP_CENTER
                }
            };
            var map = new google.maps.Map(document.getElementById("map_container"), myOptions);

            var marker = new google.maps.Marker({
                position: latlng,
                map: map,
                title: name
            });

        }
    </script>
    <title><fmt:message key="page.contacts.title"/></title>
</head>
<body onload="loadMap(51.160212, 71.462025, 'Автопрокат')">
<c:import url="../common/header.jsp" charEncoding="utf-8"/>
<div class="warning">
    <p><fmt:message key="message.contact.address"/></p>
    <p><fmt:message key="message.contact.telephone"/> 87774174086</p>
    <p>Email: test@test.com</p>
<div id="map_container" style="margin-left:275px; width: 682px; height: 300px"></div>
</div>
<c:import url="../common/footer.jsp" charEncoding="utf-8"/>
</body>
</html>
</fmt:bundle>