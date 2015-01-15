<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html ng-app="myApp">
    <body ng-controller="fController">
        <%@include file="/resources/template/header.html" %>
        <div class="col-lg-2" ng-show="document.id !== ''" >
            <form ng-submit="deleteDocument()">
                 <input class="col-lg-6 btn btn-primary" type="submit" value="Delete"/>
            </form>
        </div>
        <form:form class="form-horizontal" method="post" action="/ThuisAdmin/finances/nieuwBedragDocument/saveDocument" modelAttribute="document" enctype="multipart/form-data">
            <div class="col-lg-2">
                <input class="col-lg-6 btn btn-primary" value="Save" type="submit">
            </div>
            <div form-group">
               <form:input type="hidden" path="pk_id"/>
            </div>
            <div class="col-lg-12">
               <form:input type="hidden" path="bedrag_id" value="${bedragId}"/>
            </div>
            <div class="col-lg-12 well">
                <div class="col-lg-12">
                   <form:textarea class="col-lg-2"  path="omschrijving"></form:textarea>
                </div>
                <div class="col-lg-12">
                    <input class="col-lg-1" name="file" id="file" type="file">
                </div>
            </div>
        </form:form>
    </body>
    <script text="text/javascript">
        app.controller("fController", function($scope, $http) {
            $scope.document = {
                pk_id: '<c:out value="${document.pk_id}"/>',
                bedrag_id: '<c:out value="${bedragId}"/>',
                omschrijving: '<c:out value="${document.omschrijving}"/>',
                datum: ''
        
            };
            
            $scope.deleteDocument = (function(){
                 bootbox.confirm("Are you sure you want to delete this Document?", function(result) {
                    if (result === true){
                        $http.post("/ThuisAdmin/json/delete_document", $scope.document).success( function() {
                            $(location).attr('href','/ThuisAdmin/finances/nieuwBedrag/' + $scope.document.bedrag_id);
                        }).error( function(){
                          bootbox.alert("Error occured during delete");
                        });
                    }
                }); 
            });
        });
    </script>
</html>
