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
            <div class="col-lg-2">
                <form class="col-lg-12 form-horizontal" ng-submit="processTickedJobs()">
                    <input class="btn btn-primary" type="submit" value="Process Completed Ticked Jobs"/>
                </form>
            </div>
            <div class="col-lg-5">
                <form class="col-lg-12 form-horizontal" method="post" action="/ThuisAdmin/timesheets/saveSQLite" enctype="multipart/form-data">
                    <input class="col-lg-4" name="file" id="file" type="file">
                    <input class="col-lg-6 btn btn-primary" type="submit" value="Upload New File"/>
                </form>
            </div>
            <div class="col-lg-5">
                <form class="col-lg-12 form-inline" method="post" action="/ThuisAdmin/timesheets/saveSSHSQLite" enctype="multipart/form-data">
                    <div class="col-lg-4 form-group">
                        <input class="col-lg-12 form-control" name="ip" id="ip" placeholder="IP Address" type="text">
                    </div>
                    <div class="col-lg-6 form-group">
                        <input class="col-lg-12 btn btn-primary" type="submit" value="Upload From SSH Server"/>
                    </div>
                </form>
            </div>
        </div>
        <div class="col-lg-12">
            <table st-safe-src="tickedJobs" st-table="emptyTickedJobs" class="table table-striped table-hover ">
            <thead>
              <tr>
                  <th></th>
                  <th st-sort="name">Job</th>
                  <th st-sort="started">Started</th>
                  <th st-sort="ticked">Ticked</th>
              </tr>
            </thead>
            <tbody>
                <tr ng-repeat="ticked in emptyTickedJobs">
                    <td></td>
                    <td>
                        <select id="rekening" class="form-control" ng-model="ticked.selectedJobId"
                              ng-options="job.pkId as job.jobsGroup.name + ' ' + job.workedHours for job in ticked.jobs">
                              <option value="">--Kies Job--</option>
                        </select>
                    </td>
                    <td>{{ticked.started}}</td>
                    <td>{{ticked.ticked}}</td>
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
            $http.get("/ThuisAdmin/json/fetch_all_not_processed_ticked_jobs").success( function(data){
                $scope.tickedJobs = data;
            });
            
            $scope.processTickedJobs = function(){
                $http.post("/ThuisAdmin/json/process_ticked_jobs", $scope.tickedJobs).success( function(data){
                    $scope.tickedJobs = data;
                });
            }
        });
    </script>
</html>
