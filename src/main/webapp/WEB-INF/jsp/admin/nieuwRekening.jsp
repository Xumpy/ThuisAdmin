<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html ng-app="myApp">
    <%@include file="/resources/template/header.html" %>
    <body ng-controller="fController">
        <div class="col-lg-12">
            <form class="col-lg-1" ng-submit="saveRekening()">
                <input class="col-lg-12 btn btn-primary" type="submit" value="Save"/>
            </form>
            <form ng-show="rekening.pk_id !== ''" class="col-lg-1" ng-submit="deleteRekening()">
                <input class="col-lg-12 btn btn-primary" type="submit" value="Delete"/>
            </form>
        </div>
        <div class="col-lg-6 well">
            <div class="form-group col-lg-12">
              <label for="inputWaarde" class="col-lg-2 control-label">Waarde</label>
              <div class="col-lg-4">
                <input class="form-control" id="inputWaarde" ng-model="rekening.waarde" placeholder="Waarde" type="text">
              </div>
            </div>
            <div class="form-group col-lg-12">
              <label for="inputNaam" class="col-lg-2 control-label">Naam</label>
              <div class="col-lg-4">
                <input class="form-control" id="inputNaam" ng-model="rekening.naam" placeholder="Naam" type="text">
              </div>
            </div>
        </div>
    </body>
    <script type="text/javascript">
        var today = new Date();
        var datum = today.getFullYear() + "-" + ("0" + (today.getMonth()+1)).slice(-2) + "-" + ("0" + today.getDate()).slice(-2);
        
        app.controller("fController", function($scope, $http) {
            <%@include file="/resources/template/globalScope.html" %>
            if ("<c:out value="${pk_id}"/>" !== ""){
              $http.get("/ThuisAdmin/json/rekeningen/<c:out value="${pk_id}"/>").success( function(data){
                  $scope.rekening = data;
              });  
            } else {
              $scope.rekening = {
                  pk_id: "",
                  waarde: "",
                  naam: "",
                  laatst_bijgewerkt: datum
              };
            }
            
            $scope.saveRekening = (function(){
                $http.post("/ThuisAdmin/json/saveRekening", $scope.rekening).success( function() {
                    $(location).attr('href','/ThuisAdmin/admin/rekeningen');
                }).error( function() {
                    bootbox.alert("Error occured during save");
                });
            });
            
            $scope.deleteRekening = (function(){
                 bootbox.confirm("Are you sure you want to delete this Rekening?", function(result) {
                    if (result === true){
                        $http.post("/ThuisAdmin/json/deleteRekening", $scope.rekening).success( function() {
                            $(location).attr('href','/ThuisAdmin/admin/rekeningen');
                        }).error( function(){
                          bootbox.alert("Error occured during delete");
                        });
                    }
                }); 
            });
        });
    </script>
</html>
