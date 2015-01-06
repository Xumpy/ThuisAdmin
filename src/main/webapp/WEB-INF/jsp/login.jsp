<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html xmlns:th="http://www.thymeleaf.org" xmlns:tiles="http://www.thymeleaf.org">
  <head>
    <script src="<c:url value="/resources/jquery-1.11.1.min.js" />"></script>
    <script src="<c:url value="/resources/angular.min.js" />"></script>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="<c:url value="/resources/bootstrap/css/bootstrap.min.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/bootstrap/css/datepicker3.css" />" rel="stylesheet">
    <script src="<c:url value="/resources/bootstrap/js/bootstrap.min.js" />"></script>
    <script src="<c:url value="/resources/bootstrap/js/bootstrap-datepicker.js" />"></script>
    <title tiles:fragment="title">Login ThuisAdmin</title>
  </head>
  <body>
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3>Please Login</h3>
            </div>
        </div>
        <form class="form-horizontal" name="loginForm" action="<c:url value='j_spring_security_check' />" method="post">               
            <fieldset>
                <div th:if="${param.logout}" class="alert alert-warning"> 
                    You have been logged out.
                </div>
                <div class="form-group">
                    <label class="col-lg-1 control-label" for="username">Username</label>
                    <div class="col-lg-2">
                        <input class="form-control" placeholder="Username"type="text" id="username" name="username"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-lg-1 control-label" for="password">Password</label></td>
                    <div class="col-lg-2">
                        <input class="form-control" placeholder="Password" type="password" id="password" name="password"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-lg-offset-1 col-lg-2">
                        <button type="submit" class="btn-primary btn">Log in</button>
                    </div>
                </div>
            </fieldset>
        </form>
  </body>
</html>