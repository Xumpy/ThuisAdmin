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
        <div class="col-lg-12"/>
            <form class="form-horizontal" ng-submit="saveJobs()">
                <div class="col-lg-1">
                    <input class="btn btn-primary" type="submit" value="Save"/>
                </div>
            </form>
            <form class="form-horizontal" ng-submit="nieuwGroup()">
                <div class="col-lg-1">
                    <input class="btn btn-primary" type="submit" value="Nieuw Group"/>
                </div>
            </form>
            <div class="col-lg-2">
                <input data-provide="datepicker" data-date-format="mm/yyyy" class="datepicker form-control" id="beginDatum" placeholder="Select Month" 
                       type="text" ng-model="Overview.month">
            </div>
        </div>
        <table st-safe-src="bedragen" st-table="emptyBedragen" class="table table-striped table-hover ">
            <thead>
                <tr>
                    <th>Rekening</th>
                    <th ng-repeat="jobs in Overview.allJobsInJobsGroup[0].jobs">{{jobs.jobDay}}</th>
                </tr>
            </thead>
            <tbody>
                <tr ng-repeat="allJobsInJobsGroup in Overview.allJobsInJobsGroup">
                    <td>{{allJobsInJobsGroup.name}}</td>
                    <td ng-repeat="jobs in allJobsInJobsGroup.jobs">
                        <input ng-style="jobs.weekendDay === true && {'background-color': 'grey'}"
                            class="form-control input-sm" placeholder=".input-sm" type="text" ng-model="jobs.workedHours">
                    </td>
                </tr>
            </tbody>
        </table>
    </body>
    <script type="text/javascript">
        $(".datepicker").datepicker( {
            format: "mm/yyyy",
            viewMode: "months", 
            minViewMode: "months"
        });
        
        app.controller("fController", function($scope, $http) {
            <%@include file="/resources/template/globalScope.html" %>
            
            $http.post('/ThuisAdmin/json/fetch_overview').success(function(data){
                $scope.Overview = data;
                $scope.tempSelectMonth = data.month;
            });
            
            $scope.saveJobs = function(){
                $http.post('/ThuisAdmin/json/save_jobs_overview', $scope.Overview).success(function(data){
                    alert(data);
                });
            }
            
            $scope.$watchCollection('Overview.month', function(newValue, oldValue){
                if (newValue !== undefined && newValue !== $scope.tempSelectMonth){
                    $http.post('/ThuisAdmin/json/fetch_month', $scope.Overview.month).success(function(data){
                        $scope.Overview = data;
                    });
                    $scope.tempSelectMonth = $scope.Overview.month;
                }
            });
        });
    </script>
</html>
