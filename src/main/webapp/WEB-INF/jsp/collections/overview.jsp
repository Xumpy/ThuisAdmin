<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html ng-app="myApp">
    <%@include file="/resources/template/header.html" %>
    <body ng-controller="fController">
        <div class="col-lg-12">
            <form class="form-horizontal" action="newCollection">
                <div class="col-lg-1">
                    <input class="btn btn-primary" type="submit" value="Nieuw Verzameling"/>
                </div>
            </form>
        </div>
        <div class="col-lg-12">
            <table st-safe-src="collections" st-table="tempCollections" class="table table-striped table-hover ">
            <thead>
              <tr>
                  <td></th>
                  <th st-sort="naam">Naam</th>
                  <th st-sort="omschrijving">Omschrijving</th>
              </tr>
            </thead>
            <tbody>
                <tr ng-repeat="collection in tempCollections">
                        <td><a href="newCollection/{{collection.pkId}}">Edit</a></td>
                        <td>{{collection.name}}</td>
                        <td>{{collection.description}}</td>
                </tr>
            </tbody>
            <tfoot>
                <tr>
                    <td colspan="4" class="text-center">
                            <div st-pagination="" st-items-by-page="100" st-displayed-pages="100"></div>
                    </td>
                </tr>
            </tfoot>
            </table>
        </div>
    </body>
    <script type="text/javascript">
        app.controller("fController", function($scope, $http) {
            <%@include file="/resources/template/globalScope.html" %>
            $http.get("/ThuisAdmin/json/collections/hoofdCollections").success( function(data){
                $scope.collections = data;
            });
        });
    </script>
</html>
