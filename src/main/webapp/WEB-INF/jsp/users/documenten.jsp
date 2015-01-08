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
        <div class="col-lg-12">
            <table st-safe-src="documenten" st-table="emptyDocumenten" class="table table-striped table-hover ">
            <thead>
              <tr>
                  <th st-sort="typeGroep">Type Naam</th>
                  <th st-sort="datum">Datum</th>
                  <th st-sort="bedrag">Bedrag</th>
                  <th st-sort="omschrijving">Omschrijving</th>
                  <th>Download</th>
              </tr>
            </thead>
            <tbody>
                <tr ng-repeat="document in emptyDocumenten">
                        <td>{{document.typeGroep}}</td>
                        <td>{{document.datum}}</td>
                        <td>{{document.bedrag}}</td>
                        <td>{{document.omschrijving}}</td>
                        <td><a href="/ThuisAdmin/json/fetch_document/{{document.pk_id}}">Download</a></td>
                </tr>
            </tbody>
            <tfoot>
                <tr>
                    <td colspan="4" class="text-center">
                            <div st-pagination="" st-items-by-page="10" st-displayed-pages="100"></div>
                    </td>
                </tr>
            </tfoot>
            </table>
        </div>
    </body>
    <script type="text/javascript">
        var app = angular.module('myApp', ['smart-table']);
        app.controller("fController", function($scope, $http) {
            $http.get("/ThuisAdmin/json/fetch_documenten").success( function(data){
                $scope.documenten = data;
            });
        });
    </script>
</html>
