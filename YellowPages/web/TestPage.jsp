<%--
  Created by IntelliJ IDEA.
  User: Albino
  Date: 28.06.2020
  Time: 16:40
  To change this template use File | Settings | File Templates.
--%>
<%@page import="java.util.Date" %>
<%@page import="entities.Person" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Main Servlet</title>
</head>
<body>
    <h1>Testing</h1>
    <p>
        <% Date now = new Date();
            String s = "Now is: " + now + "<br>";
        %>
    <%= s %>
        <%
            for (int i = 0; i < 3; i++) {
                out.println("STRIIING" + "<br>");
            }
            Person pers = new Person(1L,"Alex","Max","Serg","manager");
            out.println(pers);
        %>
    </p>
    <p>
        <%=
            request.getParameter("name")
        %>
    </p>
</body>
</html>
