<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html ng-app="myApp">
    <%@include file="/resources/template/header.html" %>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Groups</title>
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
            <table st-safe-src="groups" st-table="emptyGroups" class="table table-striped table-hover ">
            <thead>
              <tr>
                  <th></th>
                  <th st-sort="name">Group Name</th>
                  <th st-sort="description">Group Description</th>
                  <th st-sort="company">Company</th>
              </tr>
            </thead>
            <tbody>
                <tr ng-repeat="group in emptyGroups">
                    <td><a href="editGroup/{{group.pk_id}}">Edit</a></td>
                    <td>{{group.name}}</td>
                    <td>{{group.description}}</td>
                    <td>{{group.company.name}}</td>
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
        });
    </script>
</html>
