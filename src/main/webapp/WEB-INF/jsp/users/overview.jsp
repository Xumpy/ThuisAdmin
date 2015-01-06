<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html ng-app>
    <head>
        <script src="<c:url value="/resources/jquery-1.11.1.min.js" />"></script>
        <script src="<c:url value="/resources/angular.min.js" />"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="<c:url value="/resources/bootstrap/css/bootstrap.min.css" />" rel="stylesheet">
        <link href="<c:url value="/resources/bootstrap/css/datepicker3.css" />" rel="stylesheet">
        <script src="<c:url value="/resources/bootstrap/js/bootstrap.min.js" />"></script>
        <script src="<c:url value="/resources/bootstrap/js/bootstrap-datepicker.js" />"></script>
        <script type="text/javascript" src="https://www.google.com/jsapi?autoload={'modules':[{'name':'visualization','version':'1','packages':['corechart']}]}"></script>
        <title>ThuisAdministratie</title>
    </head>

    <body ng-controller="fController">
        <%@include file="/resources/template/header.html" %>
        <form class="form-horizontal" ng-submit="submitGraphiekData()">
            <div class="form-group">
                <label for="rekening" class="col-lg-1 control-label">Rekening</label>
                <div class="col-lg-2">
                    <select id="rekening" class="form-control" ng-model="financeOverview.rekening"
                            ng-options="rekening.naam for rekening in rekeningen track by rekening.pk_id">
                        <option value="">--Kies Rekening--</option>
                    </select>
                </div>
                <label for="beginDatum" class="col-lg-1 control-label">Begin Datum</label>
                <div class="col-lg-2">
                    <input data-provide="datepicker" class="form-control" id="beginDatum" placeholder="Begin Datum" 
                           type="text" ng-model="financeOverview.beginDatum">
                </div>
                <label for="eindDatum" class="col-lg-1 control-label">Eind Datum</label>
                <div class="col-lg-2">
                  <input data-provide="datepicker" class="form-control" id="eindDatum" placeholder="Eind Datum" 
                         type="text" ng-model="financeOverview.eindDatum">
                </div>
                <input class="btn btn-primary" type="submit" value="Submit"/>
            </div>
        </form>
        <div id="ex0"></div>
    </body>
    
    <script type="text/javascript">
        function fController($scope, $http) {
            $http.get('/ThuisAdmin/json/rekeningen').success(function(data){
               $scope.rekeningen = data; 
            });
            
            $scope.submitGraphiekData = function(){
                var res = $http.post('/ThuisAdmin/json/graphiek_overview', $scope.financeOverview);
                res.success(function(data) {
                    drawChart(data);
                });
            }
        }
        
        function drawChart(jsonData) {
          var dataGoogle = new google.visualization.DataTable();
          dataGoogle.addColumn('string', 'Datum');
          dataGoogle.addColumn('number', 'Bedrag');

          dataGoogle.addRows(jsonData);

          var options = {
              width: 1500,
              height: 600,
              hAxis: {
                title: 'Datum'
              },
              vAxis: {
                title: 'Bedrag'
              }
          }
          var chart = new google.visualization.LineChart(document.getElementById('ex0'));
          chart.draw(dataGoogle, options);
        }
    </script>
</html>
