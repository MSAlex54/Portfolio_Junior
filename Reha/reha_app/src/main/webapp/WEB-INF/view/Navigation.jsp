<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<nav class="navbar navbar-expand navbar-dark bg-dark">
    <a class="navbar-brand" href="/reha_app/">
        <img src=/reha_app/resources/img/human.png width="30" height="30" class="d-inline-block align-top" alt=""
             loading="eager">
        Reha
    </a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarsExample02"
            aria-controls="navbarsExample02" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarsExample02">
        <ul class="navbar-nav mr-auto">
            <sec:authorize access="hasAnyAuthority('ADMIN','DOCTOR')">
                <li class="nav-item">
                    <a class="nav-link" href="/reha_app/patient/list">Patients</a>
                </li>
            </sec:authorize>
            <sec:authorize access="hasAnyAuthority('ADMIN','NURSE')">
                <li class="nav-item">
                    <a class="nav-link" href="/reha_app/event/list">Events</a>
                </li>
            </sec:authorize>
            <sec:authorize access="hasAnyAuthority('ADMIN')">
                <li class="nav-item">
                    <a class="nav-link" href="/reha_app/user/list">Users</a>
                </li>
            </sec:authorize>
        </ul>
        <sec:authorize access="hasAnyAuthority('ADMIN', 'DOCTOR','NURSE')">
            <form class="form-inline my-2 my-md-0">
                <a href="/reha_app/password" type="button" class="btn btn-dark">Change Password</a>
            </form>
        </sec:authorize>
        <sec:authorize access="hasAnyAuthority('ADMIN', 'DOCTOR','NURSE')">
            <form class="form-inline my-2 my-md-0">
                <a href="/reha_app/logout" type="button" class="btn btn-dark">Logout</a>
            </form>
        </sec:authorize>
    </div>
</nav>