<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="select" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<c:import url="../Header.jsp">
    <c:param name="pageTitle" value="Assignment"/>
</c:import>
<body class="bg-light">
<c:import url="../Navigation.jsp">

</c:import>
<div class="content p-4">
    <div class="col-8">
        <h2 class="mb-4">Assignment</h2>
        <div class="card mb-4">
            <div class="card-body">
                <form:form modelAttribute="assignment" id="add-assignment-form">
                    <div class="form-group">
                        <label for="treatment">Treatment</label>
                        <form:select path="treatmentId" name="treatmentId" id="treatment" class="form-control">
                            <form:options items="${treatments}" name="treatmentOption" itemValue="id" itemLabel="name"/>
                        </form:select>
                    </div>
                    <div class="form-group">
                        <label for="dose">Dose</label>
                        <input type="text" class="form-control" id="dose" name="dose" value="${assignment.dose}">
                        <form:errors path="dose" class="has-error alert alert-danger" element="div"/>
                    </div>
                    <div class="form-group row">
                        <label for="startDate" class="col-sm-5 row-form-label">From</label>
                        <div class="col">
                            <form:input type="date" class="form-control" id="startDate" path="startDate"
                                        value="${assignment.startDate}" dataformatas="yyyy-MM-dd"/>
                            <form:errors path="startDate" class="has-error alert alert-danger" element="div"/>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="finishDate" class="col-sm-5 row-form-label">To</label>
                        <div class="col">
                            <form:input type="date" class="form-control" id="finishDate" path="finishDate"
                                        value="${assignment.finishDate}"/>
                            <form:errors path="finishDate" class="has-error alert alert-danger" element="div"/>
                        </div>
                    </div>
                    <div class="form-group row">
                        <div class="col">
                            <label>Repeat at intervals</label>
                        </div>
                        <div class="col">
                            <input type="number" class="form-control" id="inputIntervalSize" name="intervalSize" min="1"
                                   max="31"
                                   value="${assignment.intervalSize}">
                        </div>
                        <div class="col">
                            <form:select path="intervalType" id="intervalType" itemValue="${assignment.intervalType}"
                                         class="form-control"
                                         onchange="hideShowFunction()">
                                <c:forEach var="type" items="${intervalsTypes}">
                                    <option value="${type.title}" }
                                            <c:if test="${type.title eq assignment.intervalType}">selected="selected"</c:if>>
                                            ${type.title}
                                    </option>
                                </c:forEach>
                            </form:select>
                        </div>
                    </div>
                    <div class="form-group" id="weeksForm">
                        <label>Дни недели</label>
                        <c:forEach var="day" items="${daysOfWeekList}">
                            <div class="form-check">
                                <form:checkbox path="daysOfWeek" value="${day}" label="${day}"/>
                            </div>
                        </c:forEach>
                        <form:errors path="daysOfWeek" class="has-error alert alert-danger" element="div"/>
                    </div>
                    <div class="form-group row" id="monthForm">
                        <label for="inputDayOfMonth" class="col-sm-2 row-form-label">Число</label>
                        <div class="col-sm-2">
                            <input type="number" class="form-control" name="dayOfMonth" id="inputDayOfMonth"
                                   min="1" max="31" value="${assignment.dayOfMonth}">
                        </div>
                    </div>
                    <div class="form-group row">
                        <div class="col">
                            <div class="card">
                                <div class="card-header bg-white font-weight-bold">
                                    Time marks
                                </div>
                                <div class="card-body" id="momentsForm">
                                    <form:errors path="moments" class="has-error alert alert-danger" element="div"/>
                                    <c:if test="${empty assignment.moments}">
                                        <div class="form-group row" id="momentRow">
                                            <form:input type="time" path="moments" class="form-control" value=""/>
                                        </div>
                                    </c:if>
                                    <c:forEach var="time" items="${assignment.moments}">
                                        <div class="form-group row" id="momentRow">
                                            <form:input type="time" path="moments" class="form-control"
                                                        value="${time}"/>
                                        </div>
                                    </c:forEach>
                                </div>
                                <div class="card-footer">
                                    <div class="btn btn-success" onclick="addLine()">Add time mark</div>
                                    <div class="btn btn-danger" onclick="dellLine()">Delete time mark</div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <script>
                        $(document).ready(function () {
                            hideShowFunction()
                        })

                        var hideShowFunction = function () {
                            switch ($("select[id='intervalType'] option:selected").val()) {
                                case 'Day':
                                    document.getElementById("weeksForm").hidden = true;
                                    document.getElementById("monthForm").hidden = true;
                                    break;
                                case 'Week':
                                    document.getElementById("weeksForm").hidden = false;
                                    document.getElementById("monthForm").hidden = true;
                                    break;
                                case 'Month':
                                    document.getElementById("weeksForm").hidden = true;
                                    document.getElementById("monthForm").hidden = false;
                                    break;
                                default:
                            }
                            if (document.getElementById('inputDayOfMonth').value == 0) {
                                document.getElementById('inputDayOfMonth').value = 1;
                            }
                            if (document.getElementById('inputIntervalSize').value == 0) {
                                document.getElementById('inputIntervalSize').value = 1;
                            }
                        }

                        function addLine() {
                            var itm = document.getElementById("momentRow");
                            var cln = itm.cloneNode(true);
                            cln.value = "";
                            document.getElementById("momentsForm").append(cln);
                        }

                        function dellLine() {
                            if (document.getElementById("momentsForm").childElementCount > 1) {
                                document.getElementById("momentsForm").removeChild(document.getElementById("momentsForm").lastChild);
                            }
                        }
                    </script>
                    <button type="submit" class="btn btn-success">Submit</button>
                </form:form>
            </div>
        </div>
    </div>
</div>
</body>