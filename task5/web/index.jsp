<%@ page import="java.util.Enumeration" %><%--
  Created by IntelliJ IDEA.
  User: hp
  Date: 3/15/20
  Time: 8:12 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Web App</title>
  </head>
  <body>
  <% if (request.getParameter("name") != null){ %>
  Name is <%= request.getParameter("name")%>
  <% } else { %>
  Java Web Application
  <% } %>
  </body>
</html>
