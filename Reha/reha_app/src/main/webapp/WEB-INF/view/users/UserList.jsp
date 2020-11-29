<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<c:import url="../Header.jsp">
    <c:param name="pageTitle" value="User List"/>
</c:import>
<body class="bg-light">
<c:import url="../Navigation.jsp">

</c:import>
<div class="content p-4">
    <div class="card mb-4">
        <div class="card-header bg-white font-weight-bold">
            Users<br>
            <a href="/reha_app/user" class="btn btn-primary btn-sm">Add new user</a>
        </div>
        <div class="card-body">
            <table id="patient_table" class="table table-striped table-hover" cellspacing="0" width="100%">
                <thead>
                <tr>
                    <th>Employee name</th>
                    <th>Username</th>
                    <th>Delete</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="user" items="${users}">
                    <tr>
                        <td>
                            <a href="/reha_app/user/${user.id}">
                                <c:out value="${user.name}"/>
                            </a>
                        </td>
                        <td>
                            <c:out value="${user.userName}"/>
                        </td>
                        <td><a href="./${user.id}/delete"
                               class="btn btn-danger btn-sm">Delete user</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>