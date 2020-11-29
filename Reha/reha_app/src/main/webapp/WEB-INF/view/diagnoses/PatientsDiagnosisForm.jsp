<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<c:import url="../Header.jsp">
    <c:param name="pageTitle" value="Patient diagnosis"/>
</c:import>
<body class="bg-light">
<c:import url="../Navigation.jsp">

</c:import>
<div class="content p-4">
    <h2 class="mb-4">${patient.name}</h2>
    <div class="card mb-4">
        <div class="card-body">
            <form:form modelAttribute="patientsDiagnosis">
                <div class="form-group">
                    <form:select path="diagnosisId" name="diagnosisId" id="inputDiagnosisId" class="form-control"
                                 onchange="this.value">
                        <form:options items="${diagnoses}" itemValue="id" itemLabel="mkb_name"/>
                    </form:select>
                </div>
                <button type="submit" class="btn btn-success">Submit</button>
            </form:form>
        </div>
    </div>
</div>
</body>