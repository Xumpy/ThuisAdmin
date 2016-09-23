<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html ng-app="myApp">
    <%@include file="/resources/template/header.html" %>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit Group</title>
    </head>
    <body>
        <div class="col-lg-12">
            <form class="col-lg-1" ng-submit="saveGroup()">
                <input class="col-lg-12 btn btn-primary" type="submit" value="Save"/>
            </form>
            <form ng-show="rekening.pk_id !== ''" class="col-lg-1" ng-submit="deleteGroup()">
                <input class="col-lg-12 btn btn-primary" type="submit" value="Delete"/>
            </form>
        </div>
        <div class="col-lg-6 well">
            <div class="form-group col-lg-12">
              <label for="inputNaam" class="col-lg-2 control-label">Naam</label>
              <div class="col-lg-4">
                <input class="form-control" id="inputNaam" ng-model="group.name" placeholder="Naam" type="text">
              </div>
            </div>
            <div class="form-group col-lg-12">
              <label for="inputVoornaam" class="col-lg-2 control-label">Description</label>
              <div class="col-lg-4">
                <input class="form-control" id="inputVoornaam" ng-model="group.description" placeholder="Description" type="text">
              </div>
            </div>
            <div class="form-group col-lg-12">
              <label for="inputCompany" class="col-lg-2 control-label">Company</label>
              <div class="col-lg-4">
                <select class="form-control" ng-model="group.company"
                        ng-options="company.name for company in companies track by company.pk_id">
                            <option value="">-- No Company --</option>
                </select>
              </div>
            </div>
        </div>
        <div class="col-lg-12">
            <form class="form-horizontal" action="editGroupPrice">
                <div class="col-lg-1">
                    <input class="btn btn-primary" type="submit" value="New Price"/>
                </div>
            </form>
        </div>
        <div class="col-lg-12">
            <table st-safe-src="groupPrices" st-table="emptyGroupPrices" class="table table-striped table-hover ">
            <thead>
              <tr>
                  <th></th>
                  <th st-sort="startDate">Start Date</th>
                  <th st-sort="endDate">End Date</th>
                  <th st-sort="price">Price per hour</th>
              </tr>
            </thead>
            <tbody>
                <tr ng-repeat="groupPrice in emptyGroupPrices">
                    <td><a href="editGroupPrice/{{group.pk_id}}">Edit</a></td>
                    <td>{{groupPrice.startDate}}</td>
                    <td>{{groupPrice.endDate}}</td>
                    <td>{{groupPrice.pricePerHour}}</td>
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
            if ("<c:out value="${pk_id}"/>" !== ""){
              $http.get("/ThuisAdmin/json/fetch_job_group/<c:out value="${pk_id}"/>").success( function(data){
                  $scope.group = data;
              });  
            } else {
              $scope.group = {
                  pk_id: "",
                  name: "",
                  description: "",
                  company: {}
              };
            }
            
            $scope.saveGroup = (function(){
                $http.post("/ThuisAdmin/json/save_job_group", $scope.group).success( function() {
                    $(location).attr('href','/ThuisAdmin/timesheets/group');
                }).error( function() {
                    bootbox.alert("Error occured during save");
                });
            });
            
            $scope.deleteGroup = (function(){
                 bootbox.confirm("Are you sure you want to delete this Group?", function(result) {
                    if (result === true){
                        $http.post("/ThuisAdmin/json/delete_job_group", $scope.group).success( function() {
                            $(location).attr('href','/ThuisAdmin/timesheets/group');
                        }).error( function(){
                          bootbox.alert("Error occured during delete");
                        });
                    }
                }); 
            });
            
            $http.get("/ThuisAdmin/json/fetch_all_companies").success( function(data){
                $scope.companies = data;
            });
        });
    </script>
</html>
