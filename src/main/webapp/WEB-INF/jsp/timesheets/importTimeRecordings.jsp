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
            <form class="form-horizontal" action="editGroup">
                <div class="col-lg-1">
                    <input class="btn btn-primary" type="submit" value="New Group"/>
                </div>
            </form>
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
                    <td>{{ticked.job.jobsGroup.name}}</td>
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
        });
    </script>
</html>
