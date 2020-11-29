<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="select" uri="http://www.springframework.org/tags/form" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<c:import url="../Header.jsp">
    <c:param name="pageTitle" value="User"/>
</c:import>
<body class="bg-light">
<c:import url="../Navigation.jsp">

</c:import>
<div class="content p-4">
    <h2 class="mb-4">
        <c:if test="${empty user.fullName}">
            New User
        </c:if>
        <c:out value="${user.fullName}"></c:out>
    </h2>
    <div class="card mb-4">
        <div class="card-body">
            <form:form modelAttribute="user" id="add-patient-form" method="post">
                <div class="form-group">
                    <label for="fullName">Employee name</label>
                    <form:input type="text" class="form-control" id="fullName" path="fullName"
                                value="${user.fullName}"/>
                    <form:errors path="fullName" class="has-error alert alert-danger" element="div"/>
                </div>
                <div class="form-group">
                    <label for="username">Login</label>
                    <form:input type="text" class="form-control" id="username" path="username"
                                value="${user.username}"/>
                    <form:errors path="username" class="has-error alert alert-danger" element="div"/>
                </div>
                <div class="form-group">
                    <label for="password">Password</label>
                    <form:input type="password" class="form-control" id="password" path="password"
                                value="${user.password}" disabled="true"/>
                    <form:errors path="password" class="has-error alert alert-danger" element="div"/>
                </div>
                <div>
                    <label for="confirmPassword">Confirm Password</label>
                    <form:input type="password" class="form-control" id="confirmPassword" path="confirmPassword"
                                value="${user.confirmPassword}" disabled="true"/>
                    <form:errors path="confirmPassword" class="has-error alert alert-danger" element="div"/>
                </div>
                <div class="form-group" value="${user.password}" hidden></div>
                <div class="form-group" id="rolesForm">
                    <label>Roles</label>
                    <c:forEach var="role" items="${roles}">
                        <div class="form-check">
                            <form:checkbox path="roleId" value="${role.id}" label="${role.name}"/>
                        </div>
                    </c:forEach>
                    <form:errors path="roleId" class="has-error alert alert-danger" element="div"/>
                </div>
                <button type="submit" class="btn btn-success">Submit</button>
            </form:form>
        </div>
    </div>
</div>
</body>