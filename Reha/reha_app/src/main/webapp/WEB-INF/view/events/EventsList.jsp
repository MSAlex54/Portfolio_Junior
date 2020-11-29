<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<c:import url="../Header.jsp">
    <c:param name="pageTitle" value="Events"/>
</c:import>
<body class="bg-light">
<c:import url="../Navigation.jsp">

</c:import>
<div class="content p-4">
    <div class="form-group">
        <div class="col-4">
            <div class="form-group">
                <a href="/reha_app/event/list" class="btn btn-primary btn-sm">All events</a>
                <a href="/reha_app/event/hour" class="btn btn-primary btn-sm">Nearest hour</a>
                <a href="/reha_app/event/day" class="btn btn-primary btn-sm">Today</a>
            </div>
        </div>
        <div class="col-4">
            <div class="form-group">
                <select class="custom-select" id="patient_id" onchange="filterPatient()">
                    <option value="" selected disabled hidden>Choose patient</option>
                    <c:forEach var="patient" items="${patients}">
                        <option value="${patient.id}" }
                                <c:if test="${patient.id eq 0 } }">selected="selected"</c:if>>
                                ${patient.name}
                        </option>
                    </c:forEach>
                </select>
                <script>
                    function filterPatient() {
                        var p_id = $("select[id='patient_id'] option:selected").val();
                        var newURL = window.location.protocol + "//" + window.location.host + "/reha_app/event/p/" + p_id;
                        document.location.replace(newURL);
                    }
                </script>
            </div>
        </div>
    </div>
</div>
<div class="card mb-4">
</div>
<div class="card-header bg-white font-weight-bold">
    <div class="col-2">
        Events <br>
        ${pathname}
    </div>
</div>
<div class="card-body">
    <form method="post">
        <table id="patient_table" class="table table-striped table-hover" cellspacing="0" width="100%">
            <thead>
            <tr>
                <th>Patient name</th>
                <th>Treatment</th>
                <th>When</th>
                <th>Dose</th>
                <th>Status</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="event" items="${events}">
                <tr>
                    <td>
                        <a href="/reha_app/event/${event.id}">
                            <c:out value="${event.patient.name}"/>
                        </a>
                    </td>
                    <td>
                        <c:out value="${event.treatment.name}"/>
                    </td>
                    <td>
                        <c:out value="${event.normalTime}"/>
                    </td>
                    <td>
                        <c:out value="${event.dose}"/>
                    </td>
                    <td>
                        <c:out value="${event.status}"/>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </form>
</div>
</div>
</div>
</body>