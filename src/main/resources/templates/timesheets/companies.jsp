<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html ng-app="myApp">
    <%@include file="/resources/template/header.html" %>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Company</title>
    </head>
    <body>
        <div class="col-lg-12">
            <form class="form-horizontal" action="editCompanies">
                <div class="col-lg-1">
                    <input class="btn btn-primary" type="submit" value="New Company"/>
                </div>
            </form>
        </div>
        <div class="col-lg-12">
            <table st-safe-src="companies" st-table="emptyCompanies" class="table table-striped table-hover ">
            <thead>
              <tr>
                  <th></th>
                  <th st-sort="name">Company Name</th>
                  <th st-sort="dailyPayedHours">Daily Payed Hours</th>
              </tr>
            </thead>
            <tbody>
                <tr ng-repeat="company in emptyCompanies">
                    <td><a href="editCompanies/{{company.pk_id}}">Edit</a></td>
                    <td>{{company.name}}</td>
                    <td>{{company.dailyPayedHours}}</td>
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
            $http.get("/ThuisAdmin/json/fetch_all_companies").success( function(data){
                $scope.companies = data;
            });
        });
    </script>
</html>
