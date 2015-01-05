<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <script src="<c:url value="/resources/jquery-1.11.1.min.js" />"></script>
        <script src="<c:url value="/resources/angular.min.js" />"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="<c:url value="/resources/bootstrap/css/bootstrap.min.css" />" rel="stylesheet">
        <link href="<c:url value="/resources/bootstrap/css/datepicker3.css" />" rel="stylesheet">
        <script src="<c:url value="/resources/bootstrap/js/bootstrap.min.js" />"></script>
        <script src="<c:url value="/resources/bootstrap/js/bootstrap-datepicker.js" />"></script>
        <title>ThuisAdministratie</title>
    </head>

    <body>
        <%@include file="/resources/template/header.html" %>
        <form class="form-horizontal">
            <div class="form-group">
                <label for="rekening" class="col-lg-1 control-label">Rekening</label>
                <div class="col-lg-2">
                  <input class="form-control" id="rekening" placeholder="Rekening" type="text">
                </div>
                <label for="beginDatum" class="col-lg-1 control-label">Begin Datum</label>
                <div class="col-lg-2">
                    <input data-provide="datepicker" class="form-control" id="beginDatum" placeholder="Begin Datum" type="text">
                </div>
                <label for="eindDatum" class="col-lg-1 control-label">Eind Datum</label>
                <div class="col-lg-2">
                  <input data-provide="datepicker" class="form-control" id="eindDatum" placeholder="Eind Datum" type="text">
                </div>
            </div>
        </form>
    </body>
</html>
