<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html ng-app="myApp">
    <%@include file="/resources/template/header.html" %>
    <body ng-controller="fController">
        <div class="col-lg-12">
            <form class="form-horizontal" action="nieuwPersoon">
                <div class="col-lg-1">
                    <input class="btn btn-primary" type="submit" value="Nieuw Persoon"/>
                </div>
            </form>
        </div>
        <div class="col-lg-12">
            <table st-safe-src="personen" st-table="emptyPersonen" class="table table-striped table-hover ">
            <thead>
              <tr>
                  <td></th>
                  <th st-sort="naam">Naam</th>
                  <th st-sort="voornaam">Voornaam</th>
              </tr>
            </thead>
            <tbody>
                <tr ng-repeat="persoon in emptyPersonen">
                        <td><a href="nieuwPersoon/{{persoon.pk_id}}">Edit</a></td>
                        <td>{{persoon.naam}}</td>
                        <td>{{persoon.voornaam}}</td>
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
            $http.get("/ThuisAdmin/json/personen").success( function(data){
                $scope.personen = data;
            });
        });
    </script>
</html>
