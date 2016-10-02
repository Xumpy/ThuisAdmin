<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html ng-app="myApp">
    <%@include file="/resources/template/header.html" %>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <div class="col-lg-12">
            <form class="col-lg-1" ng-submit="saveCompany()">
                <input class="col-lg-12 btn btn-primary" type="submit" value="Save"/>
            </form>
            <form ng-show="rekening.pkId !== ''" class="col-lg-1" ng-submit="deleteCompany()">
                <input class="col-lg-12 btn btn-primary" type="submit" value="Delete"/>
            </form>
        </div>
        <div class="col-lg-6 well">
            <div class="form-group col-lg-12">
              <label for="inputNaam" class="col-lg-2 control-label">Naam</label>
              <div class="col-lg-4">
                <input class="form-control" id="inputNaam" ng-model="company.name" placeholder="Naam" type="text">
              </div>
            </div>
            <div class="form-group col-lg-12">
              <label for="inputDailyPayedHours" class="col-lg-2 control-label">Daily Payed Hours</label>
              <div class="col-lg-4">
                <input class="form-control" id="inputVoornaam" ng-model="company.dailyPayedHours" placeholder="Daily Payed Hours" type="text">
              </div>
            </div>
        </div>
    </body>
    <script type="text/javascript">
        app.controller("fController", function($scope, $http) {
            <%@include file="/resources/template/globalScope.html" %>
            if ("<c:out value="${pkId}"/>" !== ""){
              $http.post("/ThuisAdmin/json/fetch_company", '<c:out value="${pkId}"/>').success( function(data){
                  $scope.company = data;
              });  
            } else {
              $scope.company = {
                  pkId: "",
                  name: "",
                  dailyPayedHours: ""
              };
            }
            
            $scope.saveCompany = (function(){
                $http.post("/ThuisAdmin/json/save_company", $scope.company).success( function() {
                    $(location).attr('href','/ThuisAdmin/timesheets/companies');
                }).error( function() {
                    bootbox.alert("Error occured during save");
                });
            });
            
            $scope.deleteCompany = (function(){
                 bootbox.confirm("Are you sure you want to delete this Company?", function(result) {
                    if (result === true){
                        $http.post("/ThuisAdmin/json/delete_company", $scope.company).success( function() {
                            $(location).attr('href','/ThuisAdmin/timesheets/companies');
                        }).error( function(){
                          bootbox.alert("Error occured during delete");
                        });
                    }
                }); 
            });
        });
    </script>
</html>
