<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html ng-app="myApp">
    <body ng-controller="fController">
        <%@include file="/resources/template/header_anon.html" %>
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
                <input class="form-control" id="inputVoornaam" ng-model="persoon.voornaam" placeholder="Voornaam" type="text">
              </div>
            </div>
            <div class="form-group col-lg-12">
              <label for="inputUsername" class="col-lg-2 control-label">Gebruikersnaam</label>
              <div class="col-lg-4">
                <input class="form-control" id="inputUsername" ng-model="persoon.username" placeholder="Gebruikersnaam" type="text">
              </div>
            </div>
            <div class="form-group col-lg-12">
              <label for="inputPassword" class="col-lg-2 control-label">Wachtwoord</label>
              <div class="col-lg-4">
                <input class="form-control" id="inputPassword" ng-model="persoon.password" placeholder="Wachtwoord" type="password">
              </div>
            </div>
        </div>
        <div class="col-lg-12">
            <form class="col-lg-1" ng-submit="savePersoon()">
                <input class="col-lg-12 btn btn-primary" type="submit" value="Register"/>
            </form>
        </div>
    </body>
    <script type="text/javascript">
        app.controller("fController", function($scope, $http) {
            $scope.persoon = {
                    naam: null,
                    voornaam: null,
                    username: null,
                    password: null
                };
            $scope.login = {
                username: $scope.persoon.username,
                password: $scope.persoon.password
            };
            
            $scope.savePersoon = (function(){
                $http.post("/ThuisAdmin/register/registerUser", $scope.persoon).success( function() {
                    $(location).attr('href','/ThuisAdmin/finances/overview');
                }).error( function() {
                    bootbox.alert("Error occured during save");
                });
            });
        });
    </script>
</html>
