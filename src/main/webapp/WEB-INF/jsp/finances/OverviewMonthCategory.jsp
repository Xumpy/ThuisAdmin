<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html ng-app="myApp">
    <%@include file="/resources/template/header.html" %>
    <script type="text/javascript" src="https://www.google.com/jsapi"></script>
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
               <div id="chart_div" style="height: 600px;"></div>
            </div>
            <div class="col-lg-5">
                <div>
                   <select multiple="multiple" id="mainGroups" name="my-select[]" style="height: 600px;">
                   </select>
                </div>
                <!--
                <div>
                    <input margin-top: 40px;" class="form-control" id="beginDatum" placeholder="Search" 
                               type="text" ng-model="filterReport.searchTekst">
                </div>
                -->
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
      </div>
  </body>
    <script type="text/javascript">
        google.load("visualization", "1", {packages:["corechart"]});
        google.setOnLoadCallback(drawVisualization);

        $(".datepicker").datepicker( {
            format: "mm/yyyy",
            viewMode: "months", 
            minViewMode: "months"
        });
        app.controller("fController", function($scope, $http) {
            <%@include file="/resources/template/globalScope.html" %>
            $('#mainGroups').multiSelect({
                afterSelect: function(values){
                    $scope.OverviewMonthCategory.mainGroupValues.push({pk_id: values[0]});
                    fetchGraphiek();
                },
                afterDeselect: function(values){
                    $scope.OverviewMonthCategory.mainGroupValues = $scope.OverviewMonthCategory.mainGroupValues
                            .filter(function (el){
                               return el.pk_id !== values[0]; 
                            });
                    fetchGraphiek();
                }
            });
            
            $scope.filterReport = {
                searchTekst: ""
            }
            
            $http.post('/ThuisAdmin/json/fetchMainGroups').success(function(data) {
                $('#mainGroups').multiSelect();
                $scope.mainGroups = data;
                for(var i in $scope.mainGroups){
                    $('#mainGroups').multiSelect('addOption', { value: $scope.mainGroups[i].pk_id, text: $scope.mainGroups[i].name, index: i });
                }
                $http.post('/ThuisAdmin/json/overviewMonthCategoryHeader').success(function(data2){
                    $scope.OverviewMonthCategory = {
                        beginDate: data2.beginDate,
                        endDate: data2.endDate,
                        mainGroupValues: []
                    }
                    for(var i in data2.mainGroupValues){
                        $('#mainGroups').multiSelect('select', data2.mainGroupValues[i].pk_id.toString());
                    }
                    $('#mainGroups').multiSelect('refresh');
                });
            });
        
            function fetchGraphiek(){
                if ($scope.OverviewMonthCategory !== undefined){
                    var res = $http.post('/ThuisAdmin/json/fetchOverviewMonthCategory', $scope.OverviewMonthCategory);
                    res.success(function(data) {
                        drawVisualization(data.overviewMonthCategory, $scope, $http);
                    });
                }
            }
            
            $scope.$watchCollection('OverviewMonthCategory', function(){
                fetchGraphiek();
            });
        });

        function drawVisualization(jsonData, scope, http) {
            if (jsonData.constructor === Array){
                var data = google.visualization.arrayToDataTable(jsonData);
                
                var options = {
                  title : 'Monthly Costs per Group',
                  vAxis: {title: "Values"},
                  hAxis: {title: "Month"},
                  seriesType: "bars",
                  series: {0: {type: "line"}}
                };

                var chart = new google.visualization.ComboChart(document.getElementById('chart_div'));
                if (jsonData.length > 1){
                    chart.draw(data, options);
                } else {
                    chart.draw(google.visualization.arrayToDataTable([["Month", "Average"], ["", 0]]), options);
                }
                google.visualization.events.addListener(chart, 'select', selectHandler);
              }
              
              function getPkId(columnName){
                  for(var i=0;i<scope.mainGroups.length;i++){
                      if (scope.mainGroups[i].name === columnName){
                        return scope.mainGroups[i].pk_id;
                      }
                  }
              }
              
              function selectHandler() {
                try{
                    var selection = chart.getSelection();
                    
                    var OverviewMonthCategoryReport = {
                        date: data.getFormattedValue(selection[0].row, 0),
                        mainGroup: getPkId(data.getColumnLabel(selection[0].column))
                    }   
                    http.post('/ThuisAdmin/json/report_overzicht_groep_bedragen_per_maand', OverviewMonthCategoryReport).success( function(data) {
                        scope.reportGroepBedragenTotal = data;
                    });
                }catch(err){}
            }
        }
    </script>
</html>