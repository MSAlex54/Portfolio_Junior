<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="select" uri="http://www.springframework.org/tags/form" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<c:import url="../Header.jsp">
    <c:param name="pageTitle" value="change password"/>
</c:import>
<body class="bg-light">
<c:import url="../Navigation.jsp">

</c:import>
<div class="content p-4">
    <h2 class="mb-4">
        Change password
    </h2>
    <div class="card mb-4">
        <div class="card-body">
            <form:form modelAttribute="user" id="add-patient-form" method="post">
                <div class="form-group">
                    <label for="fullName">Employee name</label>
                    <form:input type="text" class="form-control" id="fullName" path="fullName"
                                value="${user.fullName}" readonly="true"/>
                    <form:errors path="fullName" class="has-error alert alert-danger" element="div"/>
                </div>
                <div class="form-group">
                    <label for="username">Login</label>
                    <form:input type="text" class="form-control" id="username" path="username"
                                value="${user.username}" readonly="true"/>
                    <form:errors path="username" class="has-error alert alert-danger" element="div"/>
                </div>
                <div class="form-group">
                    <label for="oldPassword">Current password</label>
                    <form:input type="password" class="form-control" id="oldPassword" path="oldPassword"
                                value="${user.oldPassword}"/>
                    <form:errors path="oldPassword" class="has-error alert alert-danger" element="div"/>
                </div>
                <div class="form-group">
                    <label for="password">New Password</label>
                    <form:input type="password" class="form-control" id="password" path="password"
                                value="${user.password}"/>
                    <form:errors path="password" class="has-error alert alert-danger" element="div"/>
                </div>
                <div class="form-group">
                    <label for="confirmPassword">Confirm Password</label>
                    <form:input type="password" class="form-control" id="confirmPassword" path="confirmPassword"
                                value="${user.confirmPassword}"/>
                    <form:errors path="confirmPassword" class="has-error alert alert-danger" element="div"/>
                </div>

                <button type="submit" class="btn btn-success">Submit</button>
            </form:form>
        </div>
    </div>
</div>
</body>