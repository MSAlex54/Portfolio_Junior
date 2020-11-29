<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<c:import url="../Header.jsp">
    <c:param name="pageTitle" value="${patient.name}"/>
</c:import>
<body class="bg-light">
<c:import url="../Navigation.jsp">

</c:import>
<div class="content p-4">
    <div class="row mb-4">
        <div class="col-sm-4">
            <form action="/patient/${patient.id}" method="get">
                <div class="card">
                    <div class="card-header bg-white font-weight-bold">
                        ${patient.name}
                    </div>
                    <div class="card-body">
                        <div class="form-group row">
                            <label for="patientName" class="col-sm-5 col-form-label">Name</label>
                            <div class="col-sm-6">
                                <input type="text" readonly class="form-control-plaintext" id="patientName"
                                       value="${patient.name}">
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="patientInsNum" class="col-sm-5 col-form-label">Insurance number</label>
                            <div class="col-sm-6">
                                <input type="text" readonly class="form-control-plaintext" id="patientInsNum"
                                       value="${patient.insureNum}">
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="patientBirthDate" class="col-sm-5 col-form-label">Birth date</label>
                            <div class="col-sm-6">
                                <input type="text" readonly class="form-control-plaintext" dataformatas="dd-MM-yyyy"
                                       id="patientBirthDate"
                                       value="${patient.birthDate}">
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="patientAge" class="col-sm-5 col-form-label">Age</label>
                            <div class="col-sm-6">
                                <input type="text" readonly class="form-control-plaintext" id="patientAge"
                                       value="${patient.age}">
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="patientAge" class="col-sm-5 col-form-label">Doctor</label>
                            <div class="col-sm-6">
                                <input type="text" readonly class="form-control-plaintext" id="patientDoctor"
                                       value="${doctorName}">
                            </div>
                        </div>
                    </div>
                    <div class="card-footer">
                        <a href="/reha_app/patient/${patient.id}" class="btn btn-primary btn-sm">Edit</a>
                        <a href="/reha_app/patient/${patient.id}/discharge" class="btn btn-danger btn-sm">Discharge
                            patient</a>
                    </div>
                </div>
            </form>
        </div>

        <div class="col-sm-8">
            <div class="card">
                <div class="card-header bg-white font-weight-bold">
                    Diagnoses <a href="../${patient.id}/diagnoses" class="btn btn-primary btn-sm">Add</a>
                </div>
                <table id="patient_table" class="table table-striped table-hover" cellspacing="0" width="100%">
                    <thead>
                    <tr>
                        <th>MKB_Code</th>
                        <th>MKB_Name</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:choose>
                        <c:when test="${empty patient.patientsDiagnoses}">
                            <tr>
                                <td> No Diagnoses yet</td>
                                <td></td>
                            </tr>
                        </c:when>
                        <c:otherwise>
                            <c:forEach var="pDiagnosis" items="${patient.patientsDiagnoses}">
                                <tr>
                                    <td>
                                        <a href="../${patient.id}/diagnoses/${pDiagnosis.id}/">
                                            <c:out value="${pDiagnosis.diagnosis.mkb_code}"/>
                                        </a>
                                    </td>
                                    <td>
                                        <c:out value="${pDiagnosis.diagnosis.mkb_name}"/>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    <div class="card">
        <div class="card-header bg-white font-weight-bold">
            Assignments
            <a href="../${patient.id}/assignment" class="btn btn-primary btn-sm">Add</a>
        </div>
        <div class="card-body">
            <table id="table" class="table table-striped table-hover" cellspacing="0" width="100%">
                <thead>
                <tr>
                    <th>Treatment</th>
                    <th>Time patter</th>
                    <th>Date start</th>
                    <th>Date Finish</th>
                    <th>Dose</th>
                    <th>Status</th>
                    <th>Action</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="assignment" items="${patient.assignments}">
                    <tr>
                        <td>
                            <a href="../${patient.id}/assignment/${assignment.id}">
                                <c:out value="${assignment.treatment.name}"/>
                            </a>
                        </td>
                        <td>
                            <c:out value="${assignment.timePattern}"/>
                        </td>
                        <td>
                            <c:out value="${assignment.startDate}"/>
                        </td>
                        <td>
                            <c:out value="${assignment.finishDate}"/>
                        </td>
                        <td>
                            <c:out value="${assignment.dose}"/>
                        </td>
                        <td>
                            <c:out value="${assignment.status}"/>
                        </td>
                        <td>
                            <c:if test="${assignment.status eq 'Assigned'}">
                                <a href="../${patient.id}/assignment/${assignment.id}/cancel"
                                   class="btn btn-danger btn-sm">Cancel assignment
                                </a>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>

