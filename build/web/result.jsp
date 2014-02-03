<%-- 
    Document   : result
    Created on : Jan 26, 2014, 8:20:50 PM
    Author     : siddarthr
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%= request.getAttribute("doctype") %>
<!-- notice that you do not put a semicolon at the end of this special
form of print-->


<html>
    <head>
        <title>Interesting Picture</title>
    </head>
    <body>
        <%-- print picture and details --%>
    <center><h1><%= request.getAttribute("pictureTag")%></h1><br>
        <img <%= request.getAttribute("pictureURL")%>><br><br>
        <%-- Form to accept input --%>
         <form action="PicServlet" method="post">
            <br><center> Enter Name of Artist: <input type="text" name="searchWord" value="" size="25" /><input type="submit" value="Submit" />
        </form>
    </center>
    </body>
</html>