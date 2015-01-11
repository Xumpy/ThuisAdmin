<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html ng-app="myApp">
    <%@include file="/resources/template/header.html" %>
    <body ng-controller="fController">
        <div class="col-lg-12">
            <form class="col-lg-1" ng-submit="savePersoon()">
                <input class="col-lg-12 btn btn-primary" type="submit" value="Save"/>
            </form>
            <form ng-show="persoon.pk_id !== ''" class="col-lg-1" ng-submit="deletePersoon()">
                <input class="col-lg-12 btn btn-primary" type="submit" value="Delete"/>
            </form>
        </div>
        <div class="col-lg-6 well">
            <div class="form-group col-lg-12">
              <label for="inputNaam" class="col-lg-2 control-label">Naam</label>
              <div class="col-lg-4">
                <input class="form-control" id="inputNaam" ng-model="persoon.naam" placeholder="Naam" type="text">
              </div>
            </div>
            <div class="form-group col-lg-12">
              <label for="inputVoornaam" class="col-lg-2 control-label">Voornaam</label>
              <div class="col-lg-4">
                <input class="form-control" id="inputVoornaam" ng-model="persoon.voornaam" placeholder="Naam" type="text">
              </div>
            </div>
        </div>
    </body>
    <script type="text/javascript">
        app.controller("fController", function($scope, $http) {
            if ("<c:out value="${pk_id}"/>" !== ""){
              $http.get("/ThuisAdmin/json/personen/<c:out value="${pk_id}"/>").success( function(data){
                  $scope.persoon = data;
              });  
            } else {
              $scope.persoon = {
                  pk_id: "",
                  naam: "",
                  voornaam: ""
              };
            }
            
            $scope.savePersoon = (function(){
                $http.post("/ThuisAdmin/json/savePersoon", $scope.persoon).success( function() {
                    $(location).attr('href','/ThuisAdmin/admin/personen');
                }).error( function() {
                    bootbox.alert("Error occured during save");
                });
            });
            
            $scope.deletePersoon = (function(){
                 bootbox.confirm("Are you sure you want to delete this Persoon?", function(result) {
                    if (result === true){
                        $http.post("/ThuisAdmin/json/deletePersoon", $scope.persoon).success( function() {
                            $(location).attr('href','/ThuisAdmin/admin/personen');
                        }).error( function(){
                          bootbox.alert("Error occured during delete");
                        });
                    }
                }); 
            });
        });
    </script>
</html>
