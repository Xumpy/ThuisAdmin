<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html ng-app="myApp">
    <%@include file="/resources/template/header.html" %>
    <body ng-controller="fController">
        <div class="col-lg-12">
            <form class="form-horizontal" action="nieuwGroep">
                <div class="col-lg-1">
                    <input class="btn btn-primary" type="submit" value="Nieuw Groep"/>
                </div>
            </form>
        </div>
        <div class="col-lg-12">
            <table st-safe-src="groepen" st-table="emptyGroepen" class="table table-striped table-hover ">
            <thead>
              <tr>
                  <td></th>
                  <th st-sort="hoofdGroep">Hoofdgroep</th>
                  <th st-sort="naam">Naam</th>
                  <th st-sort="omschrijving">Omschrijving</th>
                  <th st-sort="negatief">Status</th>
                  <th st-sort="personen">Personen</th>
              </tr>
            </thead>
            <tbody>
                <tr ng-repeat="groep in emptyGroepen">
                        <td><a href="nieuwGroep/{{groep.pk_id}}">Edit</a></td>
                        <td>{{groep.hoofdGroep.naam}}</td>
                        <td>{{groep.naam}}</td>
                        <td>{{groep.omschrijving}}</td>
                        <td>{{groep.negatief === 0 ? "Positief" : "Negatief"}}</td>
                        <td>{{groep.persoon.voornaam}} {{groep.persoon.naam}}</td>
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
            $http.get("/ThuisAdmin/json/hoofdGroepen").success( function(data){
                $scope.groepen = data;
            });
        });
    </script>
</html>
