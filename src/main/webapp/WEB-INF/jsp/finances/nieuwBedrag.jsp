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
                <form class="col-lg-2" ng-submit="saveBedrag()">
                    <input class="col-lg-12 btn btn-primary" type="submit" value="Save"/>
                </form>
                <form ng-show="groep.pk_id !== ''" class="col-lg-2" ng-submit="deleteBedrag()">
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
                      <input data-provide="datepicker" data-date-format="yyyy-mm-dd" placeholder="Datum" class="form-control" id="inputNegatief" ng-model="bedrag.datum" type="text">
                  </div>
                </div>
                <div class="form-group col-lg-12">
                  <div class="col-lg-8">
                    <input class="form-control" id="inputNegatief" placeholder="Omschrijving" ng-model="bedrag.omschrijving" type="text">
                  </div>
                </div>
            </div>
        </div>
        <div class="col-lg-12">
            <form class="form-horizontal" ng-submit="nieuwDocument()">
                <div class="col-lg-1">
                    <input class="btn btn-primary" type="submit" value="Nieuw Document"/>
                </div>
            </form>
        </div>
        <div class="col-lg-12">
            <table st-safe-src="documenten" st-table="emptyDocumenten" class="table table-striped table-hover ">
            <thead>
              <tr>
                  <th></th>
                  <th st-sort="datum">Datum</th>
                  <th st-sort="omschrijving">Omschrijving</th>
                  <th>Download</th>
              </tr>
            </thead>
            <tbody>
                <tr ng-repeat="document in emptyDocumenten">
                        <td><a href="/ThuisAdmin/finances/editBedragDocument/{{document.pk_id}}">Edit</a></td>
                        <td>{{document.datum}}</td>
                        <td>{{document.omschrijving}}</td>
                        <td><a href="/ThuisAdmin/json/fetch_document/{{document.pk_id}}">Download</a></td>
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
    <script text="text/javascript">
        app.controller("fController", function($scope, $http) {
            if ("<c:out value="${pk_id}"/>" !== ""){
              $http.get("/ThuisAdmin/json/find_bedrag/<c:out value="${pk_id}"/>").success( function(dataBedrag){
                  $scope.bedrag = dataBedrag;
                  $http.get("/ThuisAdmin/json/groepTree/" + $scope.bedrag.groep.pk_id).success( function(dataGroepTree){
                        $scope.groepTreeData = dataGroepTree;
                  });
                  $http.get("/ThuisAdmin/json/fetch_bedrag_documenten/" + $scope.bedrag.pk_id).success( function(data){
                      $scope.documenten = data;
                  });
                  $scope.nieuwDocument = (function(){
                      $(location).attr('href','/ThuisAdmin/finances/nieuwBedragDocument/' + $scope.bedrag.pk_id);
                  });
              });
            } else {
                $scope.bedrag = {
                        pk_id: null,
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
            }
            
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
            
            $scope.saveBedrag = (function(){
                $http.post("/ThuisAdmin/json/save_bedrag", $scope.bedrag).success( function(data) {
                    if (data==="1"){
                        $(location).attr('href','/ThuisAdmin/finances/beheerBedragen');
                    } else {
                        bootbox.alert("Error occured during save");
                    }
                }).error( function() {
                    bootbox.alert("Error occured during save");
                });
            });
            
            $scope.deleteBedrag = (function(){
                 bootbox.confirm("Are you sure you want to delete this Bedrag?", function(result) {
                    if (result === true){
                        $http.post("/ThuisAdmin/json/delete_bedrag", $scope.bedrag).success( function(data) {
                            if (data==="1"){
                                $(location).attr('href','/ThuisAdmin/finances/beheerBedragen');
                            } else {
                                bootbox.alert("Error occured during delete");
                            }
                        }).error( function(){
                          bootbox.alert("Error occured during delete");
                        });
                    }
                }); 
            });
        });
    </script>
</html>
