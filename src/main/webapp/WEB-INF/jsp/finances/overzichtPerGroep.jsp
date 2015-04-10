<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html ng-app="myApp">
    <script type="text/javascript" src="https://www.google.com/jsapi?autoload={'modules':[{'name':'visualization','version':'1.1','packages':['bar']}]}"></script>
    <body ng-controller="fController">
        <%@include file="/resources/template/header.html" %>
        <form class="form-horizontal" ng-submit="submitGraphiekData()">
            <div class="form-group">
                <label for="beginDatum" class="col-lg-1 control-label">Begin Datum</label>
                <div class="col-lg-2">
                    <input data-provide="datepicker" data-date-format="dd/mm/yyyy" class="form-control" id="beginDatum" placeholder="Begin Datum" 
                           type="text" ng-model="financeOverview.beginDatum">
                </div>
                <label for="eindDatum" class="col-lg-1 control-label">Eind Datum</label>
                <div class="col-lg-2">
                  <input data-provide="datepicker" data-date-format="dd/mm/yyyy" class="form-control" id="eindDatum" placeholder="Eind Datum" 
                         type="text" ng-model="financeOverview.eindDatum">
                </div>
                <div class="col-lg-2">
                    <label for="showPublicGroep" class="control-label">Show Public Groups</label>
                    <input class="control-label" id="showPublicGroep"
                             type="checkbox" ng-model="financeOverview.showPublicGroep">
                </div>
                <input class="btn btn-primary" type="submit" value="Submit"/>
            </div>
        </form>
        <div class="col-lg-12 form-group">
            <b>Totaal Kosten: {{overzichtGroep.totaal_kosten}}<br/></b>
            <b>Totaal Opbrengsten: {{overzichtGroep.totaal_opbrengsten}}</b>
        </div>
        <div>
            <div id="ex0" class="col-lg-6"></div>
            
            <div class="col-lg-6">
                <input class="form-control" id="beginDatum" placeholder="Search" 
                           type="text" ng-model="filterReport.searchTekst">
            </div>
            <div class="col-lg-6">
                <table st-safe-src="reportGroepBedragenTotal.overzichtGroepBedragen" st-table="emptyGroepBedragen" class="table table-striped table-hover ">
                <thead>
                  <tr>
                      <th st-sort="type_naam">Type Naam</th>
                      <th st-sort="bedrag">Bedrag</th>
                      <th st-sort="datum">Datum</th>
                      <th st-sort="rekening">Rekening</th>
                      <th st-sort="omschrijving">Omschrijving</th>
                  </tr>
                </thead>
                <tbody>
                    <tr ng-repeat="overzicht in emptyGroepBedragen">
                            <td>{{overzicht.type_naam}}</td>
                            <td>{{overzicht.bedrag}}</td>
                            <td>{{overzicht.datum}}</td>
                            <td>{{overzicht.rekening}}</td>
                            <td>{{overzicht.omschrijving}}</td>
                    </tr>
                </tbody>
		<tfoot>
                    <tr>
                        <td> 
                        </td>
                        <td>
                            <b>{{reportGroepBedragenTotal.somBedrag}}</b>
                        </td>
                        <td colspan="2"></td>
                    </tr>
                    <tr>
                        <td colspan="4" class="text-center">
                                <div st-pagination="" st-items-by-page="itemsByPage" st-displayed-pages="100"></div>
                        </td>
                    </tr>
		</tfoot>
                </table>
            </div>
        </div>
    </body>
    
    <script type="text/javascript">
        app.controller("fController", function($scope, $http) {
            <%@include file="/resources/template/globalScope.html" %>
            // Begin Set Header Info
            $scope.financeOverview = {
                beginDatum: "",
                eindDatum: "",
                rekening: "",
                showPublicGroep: false
            };
            
            $scope.filterReport = {
                searchTekst: ""
            }
            
            $http.get('/ThuisAdmin/json/getFinanceHeader').success(function(data){
                $scope.financeOverview = data; 
                if ($scope.financeOverview.beginDatum !== ""){
                    $scope.submitGraphiekData();
                }
            });
            
            $scope.overzichtGroepBedragen = {
                typeGroepId: "",
                typeGroepKostOpbrengst: "",
                beginDatum: $scope.financeOverview.beginDatum,
                eindDatum: $scope.financeOverview.eindDatum,
                showPublicGroep: $scope.financeOverview.showPublicGroep
            };
            
            $scope.$watchCollection('financeOverview', function() {
                if ($scope.financeOverview.beginDatum !== "")
                    $http.post('/ThuisAdmin/json/setFinanceHeader', $scope.financeOverview);
            });

            $scope.$watchCollection('overzichtGroepBedragen', function() {
                if ($scope.overzichtGroepBedragen.typeGroepId !== "")
                   $http.post('/ThuisAdmin/json/report_overzicht_groep_bedragen', $scope.overzichtGroepBedragen).success( function(data) {
                        $scope.reportGroepBedragenTotal = data; 
                });
            });
            
            $scope.$watchCollection('filterReport', function(){
                var res = $http.post('/ThuisAdmin/json/report_overzicht_groep_bedragen_filter', $scope.filterReport).success( function(data) {
                    $scope.reportGroepBedragenTotal = data;
                });
            });
            
            $http.get('/ThuisAdmin/json/rekeningen').success(function(data){
               $scope.rekeningen = data; 
            });
            
            $scope.submitGraphiekData = function(){
                var res = $http.post('/ThuisAdmin/json/graphiek_overzicht_per_groep', $scope.financeOverview);
                res.success(function(data) {
                    $scope.overzichtGroep = data;
                    drawChart($scope.overzichtGroep.overzichtGroep);
                });
            }
            
            $scope.itemsByPage=10;
        });
        
        function drawChart(jsonData) {
          var dataGoogle = new google.visualization.DataTable();
          var mapPkId = [];
          dataGoogle.addColumn('string', 'Groep');
          dataGoogle.addColumn('number', 'Opbrensten');
          dataGoogle.addColumn('number', 'Kosten');

          $.each(jsonData, function(key, value){
              mapPkId[key] = value.groepId;
              dataGoogle.addRow([value.naam, value.totaal_opbrengsten, value.totaal_kosten]); 
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
                    $scope.$apply(function() {
                        $scope.overzichtGroepBedragen.typeGroepId = mapPkId[selection[0].row];
                        $scope.overzichtGroepBedragen.typeGroepKostOpbrengst = selection[0].column;
                        $scope.overzichtGroepBedragen.beginDatum = $scope.financeOverview.beginDatum;
                        $scope.overzichtGroepBedragen.eindDatum = $scope.financeOverview.eindDatum;
                        $scope.overzichtGroepBedragen.showPublicGroep = $scope.financeOverview.showPublicGroep;
                    });
                } catch(err){}
           }
        }
    </script>
    <script src="<c:url value="/resources/smart-table.min.js" />"></script>
</html>
