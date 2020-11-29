<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="select" uri="http://www.springframework.org/tags/form" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<c:import url="../Header.jsp">
    <c:param name="pageTitle" value="Patient"/>
</c:import>
<body class="bg-light">
<c:import url="../Navigation.jsp">

</c:import>
<div class="content p-4">
    <h2 class="mb-4">
        <c:if test="${empty patient.name}">
            New Patient
        </c:if>
        <c:out value="${patient.name}"></c:out>
    </h2>
    <div class="card mb-4">
        <div class="card-body">
            <form:form modelAttribute="patient" id="add-patient-form" method="post">
                <div class="form-group">
                    <label for="inputName">Name</label>
                    <form:input type="text" class="form-control" id="inputName" path="name"
                                value="${patient.name}"/>
                    <form:errors path="name" class="has-error alert alert-danger" element="div"/>
                </div>
                <div class="form-group">
                    <label for="inputBdate">Birth date</label>
                    <form:input type="date" class="form-control" id="inputBdate" path="birthDate"
                                value="${patient.birthDate}" dataformatas="yyyy-MM-dd"/>
                    <form:errors path="birthDate" class="has-error alert alert-danger" element="div"/>
                </div>
                <div class="form-group">
                    <label for="inputInsureNum">Insure number</label>
                    <form:input type="text" class="form-control" id="inputInsureNum" path="insureNum"
                                value="${patient.insureNum}"/>
                    <form:errors path="insureNum" class="has-error alert alert-danger" element="div"/>
                </div>
                <div class="form-group">
                    <label for="inputHealthy">Patient Status</label>
                    <form:select path="healthy" id="inputHealthy" class="form-control">
                        <form:option value="false" label="Healing"/>
                        <form:option value="true" label="Cured"/>
                    </form:select>
                </div>
                <div class="form-group">
                    <label for="inputDoctorId">Doctor</label>
                    <form:select path="doctorId" id="inputDoctorId" class="form-control">
                        <c:forEach var="doc" items="${doctors}">
                            <option id="optionDoc" value="${doc.id}" }
                                    <c:if test="${doc.id eq patient.doctorId}">selected="selected"</c:if>>
                                    ${doc.name}
                            </option>
                        </c:forEach>
                    </form:select>
                </div>
                <button type="submit" class="btn btn-success">Submit</button>
            </form:form>
        </div>
    </div>
</div>
</body>