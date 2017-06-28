<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <%@include file="/resources/template/header.html" %>
    <script type="text/javascript" src="https://www.google.com/jsapi"></script>
    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
  </head>
  <body ng-controller="fController">
      <div class="col-lg-12">
            <label for="beginDatum" class="col-lg-1 control-label">Begin Date</label>
            <div class="col-lg-2">
                <input data-provide="datepicker" data-date-format="mm/yyyy" class="datepicker form-control" id="beginDatum" placeholder="Begin Datum" 
                       type="text" ng-model="OverviewMonthCategory.beginDate">
            </div>
            <label for="eindDatum" class="col-lg-1 control-label">End Date</label>
            <div class="col-lg-2">
              <input data-provide="datepicker" data-date-format="mm/yyyy" class="datepicker form-control" id="eindDatum" placeholder="Eind Datum" 
                     type="text" ng-model="OverviewMonthCategory.endDate">
            </div>
      </div>
      <div class="col-lg-12">
            <div class="col-lg-7">
               <div id="columnchart_material" style="height: 600px;"></div>
            </div>
      </div>
  </body>
    <script type="text/javascript">
        google.charts.load('current', {'packages':['bar']});
        google.setOnLoadCallback(function() {
            angular.bootstrap(document.body, ['myApp']);
        });

        function drawChart(jsonData) {
            if (jsonData !== null){
                var data = google.visualization.arrayToDataTable(jsonData);

                var options = {
                    chart: {
                        title: 'Company Performance',
                        subtitle: 'Sales, Expenses, and Profit: 2014-2017',
                    }
                };

                var chart = new google.charts.Bar(document.getElementById('columnchart_material'));

                chart.draw(data, google.charts.Bar.convertOptions(options));
            }
        }

        $(".datepicker").datepicker( {
            format: "mm/yyyy",
            viewMode: "months", 
            minViewMode: "months"
        });

        app.controller("fController", function($scope, $http) {
            google.charts.load('current', {'packages':['bar']});
            <%@include file="/resources/template/globalScope.html" %>

            $scope.$watchCollection('OverviewMonthCategory', function(){
                if ($scope.OverviewMonthCategory !== undefined){
                    var res = $http.post('/ThuisAdmin/json/fetchOverviewMonth', $scope.OverviewMonthCategory);
                    res.success(function(data) {
                        drawChart(data.values);
                    });
                }
            });

            $http.post('/ThuisAdmin/json/overviewMonthCategoryHeader').success(function(data2){
                google.charts.load('current', {'packages':['bar']});
                $scope.OverviewMonthCategory = {
                    beginDate: data2.beginDate,
                    endDate: data2.endDate
                }
            });
        });
    </script>
</html>