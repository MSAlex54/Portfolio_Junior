<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="select" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<c:import url="../Header.jsp">
    <c:param name="pageTitle" value="Event"/>
</c:import>
<body class="bg-light">
<c:import url="../Navigation.jsp">

</c:import>
<div class="content p-4">
    <h2 class="mb-4">Event</h2>
    <div class="card mb-4">
        <div class="card-body">
            <form:form modelAttribute="event" id="add-assignment-form">
                <div class="form-group">
                    <label for="inputTimePattern">Patient</label>
                    <input type="text" class="form-control" id="inputTimePattern" name="patient_"
                           value="${event.patientName}" readonly>
                </div>
                <div class="form-group">
                    <label for="inputStartDate">Date and time</label>
                    <input type="datetime-local" class="form-control" id="inputStartDate" name="timeStamp_"
                           value="${event.timeStamp}" readonly>
                </div>
                <div class="form-group">
                    <label for="inputTreatment">Treatment</label>
                    <input type="text" class="form-control" id="inputTreatment" name="treatment_"
                           value="${event.treatmentName}" readonly>
                </div>
                <div class="form-group">
                    <label for="inputDose">Dose</label>
                    <input type="text" class="form-control" id="inputDose" name="dose_"
                           value="${event.dose}" readonly>
                </div>
                <div class="form-group">
                    <label for="status">Event Status</label>
                    <form:select path="status" id="status" itemValue="${event.status}" class="form-control"
                                 onchange="noteFunction()" disabled="${!event.active}">
                        <c:forEach var="statusOption" items="${statuses}">
                            <option value="${statusOption.title}" }
                                    <c:if test="${statusOption.title eq event.status}">selected="selected"</c:if>>
                                    ${statusOption.title}
                            </option>
                        </c:forEach>
                    </form:select>
                </div>
                <script>
                    $(document).ready(function () {
                        if (${!event.active}) {
                            document.getElementById("status").disabled = true;
                            document.getElementById("note").disabled = true;
                            document.getElementById("btnSubmit").disabled = true;
                            document.getElementById("btnSubmit").hidden = true;
                        } else {
                            switch ($("select[id='status'] option:selected").val()) {
                                case 'Scheduled':
                                    document.getElementById("status").disabled = false;
                                    document.getElementById("note").disabled = true;
                                    document.getElementById("btnSubmit").disabled = true;
                                    document.getElementById("btnSubmit").hidden = true;
                                    break;
                                case 'Canceled':
                                    document.getElementById("note").disabled = false;
                                    break;
                                case 'Done':
                                    document.getElementById("note").disabled = true;
                                    break;
                            }
                        }
                    })
                    var noteFunction = function () {
                        switch ($("select[id='status'] option:selected").val()) {
                            case 'Scheduled':
                                document.getElementById("note").disabled = true;
                                document.getElementById("btnSubmit").disabled = true;
                                document.getElementById("btnSubmit").hidden = true;
                                break;
                            case 'Cancelled':
                                document.getElementById("note").disabled = false;
                                document.getElementById("note").value = "";
                                document.getElementById("btnSubmit").disabled = false;
                                document.getElementById("btnSubmit").hidden = false;
                                break;
                            case 'Done':
                                document.getElementById("note").disabled = true;
                                document.getElementById("btnSubmit").disabled = false;
                                document.getElementById("btnSubmit").hidden = false;
                                break;
                        }
                    }
                </script>
                <div class="form-group">
                    <label for="note">Note</label>
                    <form:input type="text" class="form-control" id="note" path="note" value="${event.note}"/>
                    <form:errors path="note" class="has-error alert alert-danger" element="div"/>
                </div>
                <button type="submit" id="btnSubmit" class="btn btn-success">
                    Submit
                </button>
                <button type="button" id="btnBack" name="back" class="btn btn-info" onclick="history.back()">
                    Go back
                </button>
            </form:form>
        </div>
    </div>
</div>
</body>