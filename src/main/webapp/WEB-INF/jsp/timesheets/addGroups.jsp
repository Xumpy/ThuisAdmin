<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html ng-app="myApp">
    <%@include file="/resources/template/header.html" %>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add Groups</title>
    </head>
    <body>
        <div class="col-lg-12">
            <form class="col-lg-1" ng-submit="addGroups()">
                <input class="col-lg-12 btn btn-primary" type="submit" value="Add Groups"/>
            </form>
            <table st-safe-src="groups" st-table="emptyGroups" class="table table-striped table-hover ">
            <thead>
              <tr>
                  <th></th>
                  <th st-sort="name">Group Name</th>
                  <th st-sort="description">Group Description</th>
              </tr>
            </thead>
            <tbody>
                <tr ng-repeat="group in emptyGroups">
                    <td><input type="checkbox" ng-model="group.checked" ng-true-value="1"></td>
                    <td>{{group.name}}</td>
                    <td>{{group.description}}</td>
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
            $http.get("/ThuisAdmin/json/fetch_all_jobs_group").success( function(data){
                $scope.groups = data;
            });
            
            $scope.addGroups = function(){
                $http.post("/ThuisAdmin/json/add_jobs_group_in_controller", $scope.groups).success( function(){
                    $(location).attr('href','/ThuisAdmin/timesheets/overview');
                }).error( function(){
                  bootbox.alert("Error occured during delete");
                });
            }
        });
    </script>
</html>
