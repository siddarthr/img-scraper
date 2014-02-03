<%-- 
    Document   : index
    Created on : Jan 26, 2014, 6:53:21 PM
    Author     : siddarthr
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%= request.getAttribute("doctype") %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Project 1 Task 4</title>
    </head>
    <body>
        <%-- form to get input --%>
        <form action="PicServlet" method="post">
            <br><center> Enter Name of Artist: <input type="text" name="searchWord" value="" size="25" /><input type="submit" value="Submit" />
        </form>
    </body>
</html>
