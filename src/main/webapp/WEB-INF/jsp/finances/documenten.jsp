<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html ng-app="myApp">
    <%@include file="/resources/template/header.html" %>
    <body ng-controller="fController">
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
        app.controller("fController", function($scope, $http) {
            <%@include file="/resources/template/globalScope.html" %>
            $http.get("/ThuisAdmin/json/fetch_documenten").success( function(data){
                $scope.documenten = data;
            });
        });
    </script>
</html>
