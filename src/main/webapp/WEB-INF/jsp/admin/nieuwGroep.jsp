<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html ng-app="myApp">
    <%@include file="/resources/template/header.html" %>
    <body ng-controller="fController">
        <div class="col-lg-3"
            data-angular-treeview="true"
            data-tree-id="groepTree"
            data-tree-model="groepTreeData"
            data-node-id="groep.pk_id"
            data-node-label="groep.naam"
            data-node-children="subGroep">
        </div>
        <div class="col-lg-6">
            <form class="col-lg-2" ng-submit="saveGroep()">
                <input class="col-lg-12 btn btn-primary" type="submit" value="Save"/>
            </form>
            <form ng-show="groep.pk_id !== ''" class="col-lg-2" ng-submit="deleteGroep()">
                <input class="col-lg-12 btn btn-primary" type="submit" value="Delete"/>
            </form>
        </div>
        <div class="col-lg-6 well">
            <div class="form-group col-lg-12">
              <label for="inputNaam" class="col-lg-2 control-label">Naam</label>
              <div class="col-lg-4">
                <input class="form-control" id="inputNaam" ng-model="groep.naam" placeholder="Naam" type="text">
              </div>
            </div>
            <div class="form-group col-lg-12">
              <label for="inputOmschrijving" class="col-lg-2 control-label">Omschrijving</label>
              <div class="col-lg-4">
                <input class="form-control" id="inputOmschrijving" ng-model="groep.omschrijving" placeholder="Omschrijving" type="text">
              </div>
            </div>
            <div class="form-group col-lg-12">
              <label for="inputNegatief" class="col-lg-2 control-label">Negatief</label>
              <div class="col-lg-4">
                <input id="inputNegatief" ng-model="groep.negatief" type="checkbox">
              </div>
            </div>
            <div class="form-group col-lg-12">
              <label for="inputNegatief" class="col-lg-2 control-label">Verberg Groep in Grahpiek</label>
              <div class="col-lg-4">
                <input id="inputNegatief" ng-model="hide_groep" type="checkbox">
              </div>
            </div>
            <div class="form-group col-lg-12">
              <label for="inputNegatief" class="col-lg-2 control-label">Public Groep</label>
              <div class="col-lg-4">
                <input id="inputNegatief" ng-model="public_groep" type="checkbox">
              </div>
            </div>
        </div>
        <div class="col-lg-12">
            <table st-safe-src="groepen" st-table="emptyGroepen" class="table table-striped table-hover ">
            <thead>
              <tr>
                  <td></th>
                  <th st-sort="hoofdGroep">Hoofdgroep</th>
                  <th st-sort="naam">Naam</th>
                  <th st-sort="omschrijving">Omschrijving</th>
                  <th st-sort="negatief">Status</th>
                  <th st-sort="personen">Personen</th>
              </tr>
            </thead>
            <tbody>
                <tr ng-repeat="groep in emptyGroepen">
                        <td><a href="/ThuisAdmin/admin/nieuwGroep/{{groep.pk_id}}">Edit</a></td>
                        <td>{{groep.hoofdGroep.naam}}</td>
                        <td>{{groep.naam}}</td>
                        <td>{{groep.omschrijving}}</td>
                        <td>{{groep.negatief}}</td>
                        <td>{{groep.persoon.voornaam}} {{groep.persoon.naam}}</td>
                </tr>
            </tbody>
            <tfoot>
                <tr>
                    <td colspan="4" class="text-center">
                            <div st-pagination="" st-items-by-page="100" st-displayed-pages="100"></div>
                    </td>
                </tr>
            </tfoot>
            </table>
        </div>
    </body>
    <script text="text/javascript">
        app.controller("fController", function($scope, $http, usSpinnerService) {
            <%@include file="/resources/template/globalScope.html" %>
            $http.get("/ThuisAdmin/json/personen").success( function(data){
               $scope.personen = data; 
            });
            
            $scope.$watch( 'groepTree.currentNode', function( newObj, oldObj ) {
                if( $scope.groepTree && angular.isObject($scope.groepTree.currentNode) ) {
                    $scope.groep.hoofdGroep = $scope.groepTree.currentNode.groep;
                }
            }, false);

            if ("<c:out value="${pk_id}"/>" !== ""){
              $http.get("/ThuisAdmin/json/groepen/<c:out value="${pk_id}"/>").success( function(data){
                  $scope.groep = data;
                  
                  if ($scope.groep.negatief === 1){
                      $scope.groep.negatief = true;
                  } else {
                      $scope.groep.negatief = false;
                  }
                  
                  if ($scope.groep.codeId === "INTER_REKENING"){
                      $scope.hide_groep = true;
                  } else {
                      $scope.hide_groep = false;
                  }
                  
                  if ($scope.groep.publicGroep === 1){
                      $scope.public_groep = true;
                  } else {
                      $scope.public_groep = false;
                  }
                  
                  if ($scope.groep.hoofdGroep === null){
                    $http.get("/ThuisAdmin/json/groepTree").success( function(data){
                        $scope.groepTreeData = data;
                    });
                  }
                  else {
                    $http.get("/ThuisAdmin/json/groepTree/" + $scope.groep.hoofdGroep.pk_id).success( function(data){
                        $scope.groepTreeData = data;
                    });
                }
                  var pkId = "<c:out value="${pk_id}"/>";
                  $http.get("/ThuisAdmin/json/subGroepen/" + pkId).success( function(data){
                      $scope.groepen = data;
                  });
              });
            } else {            
                $scope.groep = {
                    pk_id: null,
                    hoofdGroep: null,
                    naam: "",
                    omschrijving: "",
                    negatief: 0,
                    persoon: null,
                    codeId: null
                };

                $http.get("/ThuisAdmin/json/groepTree").success( function(data){
                      $scope.groepTreeData = data;
                  });
            }
            
            $scope.saveGroep = (function(){
                if ($scope.groep.negatief === true){
                    $scope.groep.negatief = 1;
                } else {
                    $scope.groep.negatief = 0;
                }
                
                if ($scope.hide_groep === true){
                    $scope.groep.codeId = "INTER_REKENING";
                } else {
                    $scope.groep.codeId = null;
                }
                
                if ($scope.public_groep === true){
                    $scope.groep.publicGroep = 1;
                } else {
                    $scope.groep.publicGroep = 0;
                }

                $http.post("/ThuisAdmin/json/saveGroep", $scope.groep).success( function() {
                    $(location).attr('href','/ThuisAdmin/admin/groepen');
                }).error( function() {
                    bootbox.alert("Error occured during save");
                });
            });
            
            $scope.deleteGroep = (function(){
                 bootbox.confirm("Are you sure you want to delete this Groep?", function(result) {
                    if (result === true){
                        if ($scope.groep.negatief === true){
                            $scope.groep.negatief = 1;
                        } else {
                            $scope.groep.negatief = 0;
                        }
                        $http.post("/ThuisAdmin/json/deleteGroep", $scope.groep).success( function() {
                            $(location).attr('href','/ThuisAdmin/admin/groepen');
                        }).error( function(){
                          bootbox.alert("Error occured during delete");
                        });
                    }
            }); 
            });
        });
    </script>
</html>
