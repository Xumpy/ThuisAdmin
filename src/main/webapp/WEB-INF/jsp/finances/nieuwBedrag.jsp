<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html ng-app="myApp">
    <head>
        <script src="<c:url value="/resources/jquery-1.11.1.min.js" />"></script>
        <script src="<c:url value="/resources/angular.min.js" />"></script>
        <script src="<c:url value="/resources/smart-table.min.js" />"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="<c:url value="/resources/bootstrap/css/bootstrap.min.css" />" rel="stylesheet">
        <link href="<c:url value="/resources/bootstrap/css/datepicker3.css" />" rel="stylesheet">
        <script src="<c:url value="/resources/bootstrap/js/bootstrap.min.js" />"></script>
        <script src="<c:url value="/resources/bootstrap/js/bootstrap-datepicker.js" />"></script>
        <title>ThuisAdministratie</title>
    </head>
    <body ng-controller="fController">
        <%@include file="/resources/template/header.html" %>
        <h1>Hello World!</h1>
    </body>
</html>
