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
        <script type="text/javascript" src="https://www.google.com/jsapi?autoload={'modules':[{'name':'visualization','version':'1.1','packages':['bar']}]}"></script>
        <div class="col-lg-12">
            <form class="col-lg-1" ng-submit="showGraphic()">
                <input class="col-lg-12 btn btn-primary" type="submit" value="Submit"/>
            </form>
        </div>
        <div class="col-lg-6 well">
            <div class="form-group col-lg-12">
              <label for="inputBeginMonth" class="col-lg-2 control-label">Begin Month</label>
              <div class="col-lg-4">
                <input data-provide="datepicker" data-date-format="mm/yyyy" class="datepicker form-control" id="inputNaam" ng-model="overviewWorkHeader.beginMonth" placeholder="Begin Month" type="text">
              </div>
            </div>
            <div class="form-group col-lg-12">
              <label for="inputEndMonth" class="col-lg-2 control-label">End Month</label>
              <div class="col-lg-4">
                <input data-provide="datepicker" data-date-format="mm/yyyy" class="datepicker form-control" id="inputVoornaam" ng-model="overviewWorkHeader.endMonth" placeholder="End Month" type="text">
              </div>
            </div>
            <div class="col-lg-12">
                <table st-safe-src="overviewWorkHeader.jobsGroup" st-table="emptyGroups" class="table table-striped table-hover ">
                <thead>
                  <tr>
                      <th></th>
                      <th st-sort="name">Group Name</th>
                      <th st-sort="description">Group Description</th>
                  </tr>
                </thead>
                <tbody>
                    <tr ng-repeat="group in emptyGroups">
                        <td><input type="checkbox" ng-model="group.checked" ng-true-value="1" ng-false-value="0"></td>
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
            <div>
                <table class="table table-striped table-hover">
                    <tr>
                        <td>Hours Payed Per Day</td><td>{{overviewWork.workDetails.hoursPayedPerDay}}</td>
                        <td>Hours To Work Per Day</td><td>{{overviewWork.workDetails.hoursToWorkPerDay}}</td>
                    </tr>
                    <tr>
                        <td>Worked Week Hours</td><td>{{overviewWork.workDetails.workedWeekHours}}</td>
                        <td>Worked Week Days</td><td>{{overviewWork.workDetails.workedWeekDays}}</td>
                    </tr>
                    <tr>
                        <td>Worked Weekend Hours</td><td>{{overviewWork.workDetails.workedWeekendHours}}</td>
                        <td>Worked Weekend Days</td><td>{{overviewWork.workDetails.workedWeekendDays}}</td>
                    </tr>
                    <tr>
                        <td>Overtime Hours</td><td>{{overviewWork.workDetails.overtimeHours}}</td>
                        <td>Overtime Days</td><td>{{overviewWork.workDetails.overtimeDays}}</td>
                    </tr>
                </table>
            </div>
        </div>
         <div class="col-lg-6 well" id="ex0" class="col-lg-6"></div>
    </body>
    <script type="text/javascript">
        $(".datepicker").datepicker( {
            format: "mm/yyyy",
            viewMode: "months", 
            minViewMode: "months"
        });
        
        app.controller("fController", function($scope, $http) {
            <%@include file="/resources/template/globalScope.html" %>
            $http.get("/ThuisAdmin/json/fetch_all_jobs_group").success( function(data){
                $scope.overviewWorkHeader.jobsGroup = data;
            });
            
            $scope.overviewWorkHeader = {
                beginMonth: "",
                endMonth: "",
                jobsGroup: {}
            }
            
            $scope.showGraphic = function(){
                $http.post("/ThuisAdmin/json/fetch_overview_work", $scope.overviewWorkHeader).success(function(data){
                    $scope.overviewWork = data;
                    drawChart(data);
                });
            }
            
            function drawChart(jsonData) {
              var dataGoogle = new google.visualization.DataTable();
              var mapPkId = [];
              dataGoogle.addColumn('string', 'Groep');
              dataGoogle.addColumn('number', 'Worked Week Hours');
              dataGoogle.addColumn('number', 'Worked Weekend Hours');

              $.each(jsonData.monthlyWorkDetails, function(key, value){
                  dataGoogle.addRow([value.month, value.workDetails.workedWeekHours, value.workDetails.workedWeekendHours]); 
              });

              var options = {
                width: 600,
                height: 500,
                chart: {
                  title: 'Kosten/Opbrengsten',
                },
                bars: 'horizontal' // Required for Material Bar Charts.
              };

              var chart = new google.charts.Bar(document.getElementById('ex0'));
              chart.draw(dataGoogle, options);

              google.visualization.events.addListener(chart, 'select', selectHandler);

                function selectHandler() {
                    try{
                        var selection = chart.getSelection();
                        // Column 1 = Opbrengsten
                        // Column 2 = Kosten
                        var appElement = document.querySelector('[ng-controller="fController"]');
                        var $scope = angular.element(appElement).scope();
                    } catch(err){}
               }
           }
        });
    </script>
</html>
