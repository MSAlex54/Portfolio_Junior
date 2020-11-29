<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<c:import url="../Header.jsp">
    <c:param name="pageTitle" value="Patient List"/>
</c:import>
<body class="bg-light">
<c:import url="../Navigation.jsp">

</c:import>
<div class="content p-4">
    <h2 class="mb-4">Treatment edit</h2>
    <div class="card mb-4">
        <div class="card-body">
            <form:form modelAttribute="treatments" id="add-patient-form">
                <select onchange="this.value()">
                    <c:forEach var="treatment" items="${treatments}">
                        <option value="${treatment.name}">${treatment.name}</option>
                    </c:forEach>
                </select>
            </form:form>
        </div>
    </div>
</div>
</body>