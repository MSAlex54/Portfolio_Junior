<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<c:import url="../Header.jsp">
    <c:param name="pageTitle" value="Login"/>
</c:import>
<body class="bg-light">
<c:import url="../Navigation.jsp">

</c:import>
<div class="content p-4">
    <div class="row mb-4">
        <div class="col-sm-4">
            <form action="${loginUrl}" method="post">
                <img class="mb-4" src=./resources/img/human.png alt="" width="72" height="72">
                <div class="form-group">
                    <label for="login">Login</label>
                    <input type="text" class="form-control" name="username" path="username" id="login"
                           placeholder="Login"/>
                </div>
                <div class="form-group">
                    <label for="input_password">Password</label>
                    <input type="password" class="form-control" name="password" path="password" id="input_password"
                           placeholder="Password"/>
                </div>
                <c:if test="${param.error != null}">
                    <div class="alert alert-danger" role="alert">
                        Invalid username or password.
                    </div>
                </c:if>
                <button type="submit" class="btn btn-primary">Sign in</button>
            </form>
        </div>
    </div>
</div>
</body>
</html>

