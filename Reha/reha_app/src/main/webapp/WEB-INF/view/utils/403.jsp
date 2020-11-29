<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<c:import url="../Header.jsp">
    <c:param name="pageTitle" value="Access denied"/>
</c:import>
<body class="text-center">
<c:import url="../Navigation.jsp">

</c:import>
<div class="content p-4">
    <main role="main" class="inner cover">
        <img class="mb-4" src=/reha_app/resources/img/human.png alt="" width="72" height="72">
        <h1 class="h3 mb-3 font-weight-normal">Sorry, you do not have access</h1>
    </main>
</div>
</body>
</html>