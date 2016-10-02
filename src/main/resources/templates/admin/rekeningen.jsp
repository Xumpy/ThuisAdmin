<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html ng-app="myApp">
    <%@include file="/resources/template/header.html" %>
    <body ng-controller="fController">
        <div class="col-lg-12">
            <form class="form-horizontal" action="nieuwRekening">
                <div class="col-lg-1">
                    <input class="btn btn-primary" type="submit" value="Nieuw Rekening"/>
                </div>
            </form>
        </div>
        <div class="col-lg-12">
            <table st-safe-src="rekeningen" st-table="emptyRekeningen" class="table table-striped table-hover ">
            <thead>
              <tr>
                  <td></th>
                  <th st-sort="waarde">Waarde</th>
                  <th st-sort="naam">Naam</th>
                  <th st-sort="laatst_bijgewerkt">Laatst Bijgewerkt</th>
              </tr>
            </thead>
            <tbody>
                <tr ng-repeat="rekening in emptyRekeningen">
                        <td><a href="nieuwRekening/{{rekening.pkId}}">Edit</a></td>
                        <td>{{rekening.waarde}}</td>
                        <td>{{rekening.naam}}</td>
                        <td>{{rekening.laatst_bijgewerkt}}</td>
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
            $http.get("/ThuisAdmin/json/rekeningen").success( function(data){
                $scope.rekeningen = data.rekeningen;
            });
        });
    </script>
</html>
