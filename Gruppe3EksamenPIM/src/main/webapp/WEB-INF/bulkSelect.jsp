<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.TreeSet"%>
<%@page import="businessLogic.Product"%>
<%@page import="businessLogic.Category"%>
<%@page import="businessLogic.Attribute"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <c:set var="pimObjectType" value='${requestScope["PIMObjectType"]}'/>
        <c:set var="category" value='${requestScope["pimObject"]}'/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Select</title>
        <link href="css/StyleTable.css" rel="stylesheet" type="text/css">
        <style>
            .buttonTable{
                width:68px;
                height: 17px; 
                border: 1px solid #000000; 
                background-color: #94e2ff; 
                cursor: pointer
            }
            .buttonTable:hover{
                background-color: #ffffff;
                box-shadow: 1px 1px 10px 0px #000000;
            }
        </style>
    </head>
    <body>
        <jsp:include page="/JSP Header/JSP-menu.jsp"/>

        <div class="main">
            <h1 align="center">
                Bulk edit for category: <br> 
                <c:out value="${category.getObjectTitle()}"/>, ID <c:out value="${category.getObjectID()}"/>
            </h1>

            <c:set var="error" value='${requestScope["error"]}'/>
            <c:if test="${not empty error}">
                <h2 style="color: red" align="center"><c:out value="${error}"/></h2>
            </c:if>


            <form action="FrontController" method="POST">
                <table align="center" border = "1" width = "60%" style="float: top" bgcolor="fffef2">
                    <thead>
                        <tr bgcolor = "#FF4B4B">
                            <th align="center">ID</th>
                            <th align="center">Name</th>
                            <th align="center">Description</th>
                            <th align="center">
                                <input type="button" value="Check" onclick="check()" class="buttonTable"/>
                                <input type="button" value="Uncheck" onclick="unCheck()" class="buttonTable"/>
                            </th>

                        </tr>
                    </thead>


                    <c:forEach items='${category.getCategoryProducts()}' var="product">
                        <tr>
                            <td align="center" width="3%"> <c:out value="${product.getObjectID()}"/> </td>
                            <td align="center" width="20%"> <c:out value="${product.getObjectTitle()}"/> </td>
                            <td align="center" width="30%"> <c:out value="${product.getObjectDescription()}"/> </td>
                            <td align="center" width="1%"><input type="checkbox" name=productChoice value="<c:out value="${product.getObjectID()}"/>" checked></td>
                        </tr>
                    </c:forEach>
                </table>
                <br>
                <input type="hidden" name="command" value="bulkSelect" />
                <input type="hidden" name="categoryID" value="<c:out value="${category.getObjectID()}"/>" />
                <p align="center"><input type="submit" value="Select" /></p>
            </form>
        </div>    

        <script>
            function check() {
                var boxes = document.getElementsByName("productChoice");
                for (var i = 0; i < boxes.length; i++) {
                    if (boxes[i].type === "checkbox") {
                        boxes[i].checked = true;
                    }
                }
            }

            function unCheck() {
                var boxes = document.getElementsByName("productChoice");
                for (var i = 0; i < boxes.length; i++) {
                    if (boxes[i].type === "checkbox") {
                        boxes[i].checked = false;
                    }
                }
            }
        </script>

    </body>
</html>
