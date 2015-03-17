<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html ng-app="myApp">
    <%@include file="/resources/template/header.html" %>
    <body ng-controller="fController">
        <div class="col-lg-12">
            <div class="col-lg-10">
                <div class="col-lg-12">
                    <form class="form-horizontal" ng-submit="submitRekening()">
                        <div class="col-lg-2">
                            <select id="rekening" class="form-control" ng-model="beheerBedragen.rekening"
                                    ng-options="rekening.naam for rekening in rekeningen track by rekening.pk_id">
                                <option value="">--Kies Rekening--</option>
                            </select>
                        </div>
                        <div class="col-lg-1">
                            <input class="btn btn-primary" type="submit" value="Submit"/>
                        </div>
                    </form>
                    <form class="form-horizontal" action="nieuwBedrag">
                        <div class="col-lg-1">
                            <input class="btn btn-primary" type="submit" value="Nieuw Bedrag"/>
                        </div>
                    </form>
                </div>
                <div class="col-lg-12">
                    <div class="row col-lg-12" style="margin-top:20px;">
                        <input class="form-control" placeholder="Search" type="text" ng-model="filterReport.searchTekst">
                    </div>
                    <table st-safe-src="bedragen" st-table="emptyBedragen" class="table table-striped table-hover ">
                        <thead>
                            <tr>
                                <th></th>
                                <th st-sort="rekening">Rekening</th>
                                <th st-sort="type_groep">Type Groep</th>
                                <th st-sort="persoon">Persoon</th>
                                <th st-sort="bedrag">Bedrag</th>
                                <th st-sort="datum">Datum</th>
                                <th st-sort="omschrijving">Omschrijving</th>
                            </tr>
                            <!--<tr>
                                <th colspan="5"><input st-search="" class="form-control" placeholder="Search" type="text"/></th>
                            </tr>-->
                        </thead>
                        <tbody>
                            <tr ng-repeat="bedrag in emptyBedragen">
                                <td><a href="nieuwBedrag/{{bedrag.pk_id}}">Edit</a></td>
                                <td>{{bedrag.rekening}}</td>
                                <td>{{bedrag.type_groep}}</td>
                                <td>{{bedrag.persoon}}</td>
                                <td>{{bedrag.bedrag}}</td>
                                <td>{{bedrag.datum}}</td>
                                <td>{{bedrag.omschrijving}}</td>
                            </tr>
                        </tbody>
                        <tfoot>
                            <tr>
                                <td colspan="5" class="text-center">
                                    <form ng-show="beheerBedragen.offset !== 0" ng-submit="previousBedrag()">
                                        <input class="btn btn-primary" type="submit" value="Vorige"/>
                                    </form>
                                </td>
                                <td>
                                    <form ng-submit="nextBedrag()">
                                        <input class="btn btn-primary" type="submit" value="Volgende"/>
                                    </form>
                                </td>
                            </tr>
                        </tfoot>
                    </table>
                </div>
            </div>
            <div class="col-lg-2">
                <table st-safe-src="rekeningen" st-table="emptyRekeningen" class="table table-striped table-hover ">
                    <thead>
                        <th>Naam</th>
                        <th>Waarde</th>
                    </thead>
                    <tbody>
                        <tr ng-repeat="rekening in rekeningen">
                            <td>{{rekening.naam}}</td>
                            <td>{{rekening.waarde}}</td>
                        </tr>
                    </tbody>
                    <tfoot>
                        <tr>
                            <td></td>
                            <td><b>{{rekeningenTotaal.totaal}}</b></td>
                        </tr>
                    </tfoot>
                </table>
            </div>
        </div>
    </body>
    <script type="text/javascript">
        app.controller("fController", function($scope, $http) {
            $scope.beheerBedragen = {
                offset: 0,
                rekening: null
            }
            
            $scope.filterReport = {
                searchTekst: ""
            }
            
            $http.get('/ThuisAdmin/json/fetch_beheer_bedragen').success(function(data){
                $scope.beheerBedragen = data;
                var res = $http.post("/ThuisAdmin/json/fetch_bedragen", $scope.beheerBedragen);
                res.success(function(data){
                   $scope.bedragen = data; 
                });
            });
            
            $http.get('/ThuisAdmin/json/rekeningen').success(function(data){
                $scope.rekeningenTotaal = data;
                $scope.rekeningen = $scope.rekeningenTotaal.rekeningen;
            });
            
            $scope.submitRekening = function(){
                $scope.beheerBedragen.offset = 0;
                var res = $http.post("/ThuisAdmin/json/fetch_bedragen", $scope.beheerBedragen);
                res.success(function(data){
                   $scope.bedragen = data; 
                });
            }
            
            $scope.nextBedrag = function(){
                $scope.beheerBedragen.offset++;
                var res = $http.post("/ThuisAdmin/json/fetch_bedragen", $scope.beheerBedragen);
                res.success(function(data){
                   $scope.bedragen = data; 
                });
            }
            
            $scope.previousBedrag = function(){
                $scope.beheerBedragen.offset--;
                var res = $http.post("/ThuisAdmin/json/fetch_bedragen", $scope.beheerBedragen);
                res.success(function(data){
                   $scope.bedragen = data; 
                });
            }
            
            $scope.$watchCollection('filterReport', function(){
                $scope.beheerBedragen.zoekOpdracht = $scope.filterReport.searchTekst;
                
                var res = $http.post('/ThuisAdmin/json/fetch_bedragen', $scope.beheerBedragen);
                res.success(function(data){
                   $scope.bedragen = data; 
                });
            });
            
            $scope.itemsByPage = 10;
        });
    </script>
</html>
