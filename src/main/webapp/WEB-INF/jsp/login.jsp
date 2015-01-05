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
    <title tiles:fragment="title">Messages : Create</title>
  </head>
  <body>
    <div tiles:fragment="content">
        <form name="f" th:action="@{/login}" method="post">               
            <fieldset>
                <legend>Please Login</legend>
                <div th:if="${param.logout}" class="alert alert-success"> 
                    You have been logged out.
                </div>
                <table>
                    <tr>
                        <td><label for="username">Username</label></td>
                        <td><input type="text" id="username" name="username"/></td>
                    </tr>
                    <tr>
                        <td><label for="password">Password</label></td>
                        <td><input type="password" id="password" name="password"/></td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <div class="form-actions">
                                <button type="submit" class="btn">Log in</button>
                            </div>
                        </td>
                    </tr>
                </table>
            </fieldset>
        </form>
    </div>
  </body>
</html>