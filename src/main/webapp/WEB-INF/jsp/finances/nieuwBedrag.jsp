<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html ng-app="myApp">
    <body ng-controller="fController">
        <%@include file="/resources/template/header.html" %>
        <div class="col-lg-3"
            data-angular-treeview="true"
            data-tree-id="groepTree"
            data-tree-model="groepTreeData"
            data-node-id="groep.pk_id"
            data-node-label="groep.naam"
            data-node-children="subGroep">
        </div>
        <div class="col-lg-9">
            <div class="col-lg-12">
                <form class="col-lg-2" ng-submit="saveGroep()">
                    <input class="col-lg-12 btn btn-primary" type="submit" value="Save"/>
                </form>
                <form ng-show="groep.pk_id !== ''" class="col-lg-2" ng-submit="deleteGroep()">
                    <input class="col-lg-12 btn btn-primary" type="submit" value="Delete"/>
                </form>
            </div>
            <div class="col-lg-10 well">
                <div class="form-group col-lg-12">
                  <div class="col-lg-3">
                      <select id="rekening" class="form-control" ng-model="bedrag.rekening"
                              ng-options="rekening.naam for rekening in rekeningen track by rekening.pk_id">
                          <option value="">--Kies Rekening--</option>
                      </select>
                  </div>
                </div>
                <div class="form-group col-lg-12">
                  <div class="col-lg-3">
                      <select id="rekening" class="form-control" ng-model="bedrag.persoon"
                              ng-options="persoon.voornaam + ' ' + persoon.naam for persoon in personen track by persoon.pk_id">
                          <option value="">--Kies Persoon--</option>
                      </select>
                  </div>
                </div>
                <div class="form-group col-lg-12">
                  <div class="col-lg-3">
                    <input class="form-control" id="inputNegatief" placeholder="bedrag" ng-model="bedrag.bedrag" type="text">
                  </div>
                </div>
                <div class="form-group col-lg-12">
                  <div class="col-lg-3">
                      <input data-provide="datepicker" data-date-format="dd/mm/yyyy" placeholder="Datum" class="form-control" id="inputNegatief" ng-model="bedrag.datum" type="text">
                  </div>
                </div>
                <div class="form-group col-lg-12">
                  <div class="col-lg-8">
                    <input class="form-control" id="inputNegatief" placeholder="Omschrijving" ng-model="bedrag.omschrijving" type="text">
                  </div>
                </div>
            </div>
        </div>
    </body>
    <script text="text/javascript">
        app.controller("fController", function($scope, $http, usSpinnerService) {
            $scope.bedrag = {
                groep: null,
                rekening: null,
                persoon: null,
                bedrag: null,
                datum: null,
                omschrijving: null
            }
            
            $http.get("/ThuisAdmin/json/groepTree").success( function(data){
                  $scope.groepTreeData = data;
              });
            
            $http.get("/ThuisAdmin/json/personen").success( function(data){
               $scope.personen = data; 
            });
            
            $http.get("/ThuisAdmin/json/rekeningen").success( function(data){
               $scope.rekeningen = data.rekeningen; 
            });
            
            $scope.$watch( 'groepTree.currentNode', function( newObj, oldObj ) {
                if( $scope.groepTree && angular.isObject($scope.groepTree.currentNode) ) {
                    $scope.bedrag.groep = $scope.groepTree.currentNode.groep;
                }
            }, false);
        });
    </script>
</html>
