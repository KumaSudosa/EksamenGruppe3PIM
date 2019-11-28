<%-- 
    Document   : viewAllAttributes
    Created on : 27-Nov-2019, 08:58:32
    Author     : Ashayla
--%>

<%@page import="businessLogic.Attribute"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View Attributes</title>
    </head>
    <body>
        <h1 align="center">Attributes list</h1>
        <form action="FrontController">
            <table align="center" border = "1" width = "50%" style="float: top" bgcolor="fffef2">
                <thead>
                    <tr bgcolor = "#FF4B4B">
                        <td align="center">Attribute ID</td>
                        <td align="center">Attribute Name</td>
                    </tr>
                </thead>

                <tbody>
                    <%
                        ArrayList<Attribute> attributeList = (ArrayList<Attribute>) request.getAttribute("attributeList");
                        for (Attribute attribute : attributeList) {
                            int AttributeID = attribute.getAttributeID();
                            String AttributeName = attribute.getAttributeName();
                    %>  
                    <tr>
                        <td align="center" width="5%"> <%=AttributeID%> </td>
                        <td align="center" width="20%"> <%=AttributeName%> </td>
                        <td align="center" width="1%"><input type="radio" name=attributeChoice value="<%=AttributeID%>"></td>
                    </tr>
                </tbody>
                <%}%>
            </table>
            <input type="hidden" name="command" value="selectAttribute" />
            <p align="center"><input type="submit" name="submitButton" value="Edit Attribute"/></p>
            <p align="center"><input type="submit" name="submitButton" value="Delete Attribute"/></p>
        </form>
        <%
                String error = (String) request.getAttribute("error");
                if (error != null) {%>
        <h2 style="color: red" align="center"><%=error%></h2>
        <%}%>
        <form action="FrontController" method="POST">
            <input type="hidden" name="command" value="goToJsp" />
            <input type="hidden" name="goToJsp" value="index" />
            <p align="center"><input type="submit" value="Go Back" /></p>
        </form>
    </body>
</html>