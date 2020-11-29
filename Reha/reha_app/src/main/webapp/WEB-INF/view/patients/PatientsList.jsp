<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="en">
<c:import url="../Header.jsp">
    <c:param name="pageTitle" value="Patient List"/>
</c:import>
<body class="bg-light">
<c:import url="../Navigation.jsp">

</c:import>
<div class="content p-4">
    <div class="card mb-4">
        <div class="card-header bg-white font-weight-bold">
            Patients<br>
            <a href="../patient" class="btn btn-primary btn-sm">Add new patient</a>
        </div>
        <div class="card-body">
            <table id="patient_table" class="table table-striped table-hover" cellspacing="0" width="100%">
                <thead>
                <tr>
                    <th>Name</th>
                    <th>Age</th>
                    <th>Birth date</th>
                    <th>Insure number</th>
                    <th>Status</th>
                    <sec:authorize access="hasAnyAuthority('ADMIN')">
                        <th>Delete</th>
                    </sec:authorize>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="patient" items="${patients}">
                    <tr>
                        <td>
                            <a href="/reha_app/patient/${patient.id}/details">
                                <c:out value="${patient.name}"/>
                            </a>
                        </td>
                        <td>
                            <c:out value="${patient.age}"/>
                        </td>
                        <td>
                            <c:out value="${patient.birthDate}"/>
                        </td>
                        <td><c:out value="${patient.insureNum}"/></td>

                        <td>
                            <c:choose>
                                <c:when test="${patient.healthy}">
                                    <span class="badge badge-success">Cured</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="badge badge-info">Healing</span>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <sec:authorize access="hasAnyAuthority('ADMIN')">
                            <td><a href="/reha_app/patient/${patient.id}/delete"
                                   class="btn btn-danger btn-sm">Delete patient</a>
                            </td>
                        </sec:authorize>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>