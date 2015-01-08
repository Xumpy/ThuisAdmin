<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html ng-app="myApp">
    <head>
        <script src="<c:url value="/resources/jquery-1.11.1.min.js" />"></script>
        <script src="<c:url value="/resources/angular.min.js" />"></script>
        <script src="<c:url value="/resources/smart-table.min.js" />"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="<c:url value="/resources/bootstrap/css/bootstrap.min.css" />" rel="stylesheet">
        <link href="<c:url value="/resources/bootstrap/css/datepicker3.css" />" rel="stylesheet">
        <script src="<c:url value="/resources/bootstrap/js/bootstrap.min.js" />"></script>
        <script src="<c:url value="/resources/bootstrap/js/bootstrap-datepicker.js" />"></script>
        <title>ThuisAdministratie</title>
    </head>
    <body ng-controller="fController">
        <%@include file="/resources/template/header.html" %>
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
                    <table st-safe-src="bedragen" st-table="emptyBedragen" class="table table-striped table-hover ">
                        <thead>
                            <tr>
                                <th st-sort="type_groep">Type Groep</th>
                                <th st-sort="persoon">Persoon</th>
                                <th st-sort="bedrag">Bedrag</th>
                                <th st-sort="datum">Datum</th>
                                <th st-sort="omschrijving">Omschrijving</th>
                            </tr>
                            <tr>
                                <th colspan="5"><input st-search="" class="form-control" placeholder="Search" type="text"/></th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr ng-repeat="bedrag in emptyBedragen">
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
                                        <div st-pagination="" st-items-by-page="10" st-displayed-pages="100"></div>
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
        var app = angular.module('myApp', ['smart-table']);
        app.controller("fController", function($scope, $http) {
            $scope.beheerBedragen = {
                rekening: "",
                zoekOpdracht: ""
            }
            
            $http.get('/ThuisAdmin/json/rekeningen').success(function(data){
                $scope.rekeningenTotaal = data;
                $scope.rekeningen = $scope.rekeningenTotaal.rekeningen;
            });
            
            $scope.submitRekening = function(){
                var res = $http.post("/ThuisAdmin/json/fetch_bedragen", $scope.beheerBedragen.rekening);
                res.success(function(data){
                   $scope.bedragen = data; 
                });
            }
            
            $scope.itemsByPage = 10;
        });
    </script>
</html>
