<%-- 
    Document   : editBundle
    Created on : 27-11-2019, 08:45:29
    Author     : Andreas
--%>

<%@page import="businessLogic.Product"%>
<%@page import="java.util.ArrayList"%>
<%@page import="businessLogic.Bundle"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit Bundle</title>
    </head>
    <body>
        <%
            Bundle bundle = (Bundle) request.getAttribute("bundle");
            String bundleName = bundle.getBundleName();
            String bundleDist = bundle.getBundleDescription();
            int bundleID = bundle.getBundleID();
        %>
        <h1 align="center">Edit Bundle Info</h1>
        <form action="FrontController" method="POST">
            <input type="hidden" name="bundleID" value="<%=bundleID%>" />
        </form>
        <form action="FrontController">
            <input type="hidden" name="bundleID" value="<%=bundleID%>"/>

            <%
            String error = (String) request.getAttribute("error");
            if(error != null){
            %>
            <h2  align="center" style="color: red"><%=error%></h2>
            <%}%>

            <p align="center">
                Bundle Name:
                <br>
                <input type="text" name="Bundle Name" value="<%=bundleName%>" required="required"/>
            </p>

            <p align="center">
                Bundle Description:
                <br>
                <textarea name="Bundle Description" rows="8" cols="40" required="required"><%=bundleDist%></textarea>
            </p>

            <table align="center" border = "1" width = "50%" style="float: top" bgcolor="fffef2">
                <thead>
                    <tr bgcolor = "#FF4B4B">
                        <td align="center">ID</td>
                        <td align="center">Name</td>
                        <td align="center">Description</td>
                    </tr>
                </thead>

                <tbody>
                    <%
                        ArrayList<Product> productList = (ArrayList<Product>) request.getAttribute("productList");
                        for (Product product : productList) {
                            int ProductID = product.getProductID();
                            String ProductName = product.getName();
                            String ProductDescription = product.getDescription();
                            boolean alreadySelectedProduct = bundle.getBundleProducts().keySet().contains(product);
                    %>  
                    <tr>
                        <td align="center" width="3%"> <%=ProductID%> </td>
                        <td align="center" width="20%"> <%=ProductName%> </td>
                        <td align="center" width="30%"> <%=ProductDescription%> </td>
                        <%if (alreadySelectedProduct) {%>
                        <td align="center" width="1%"><input type="checkbox" name="productChoice" value="<%=ProductID%>" checked></td>
                        <td align="center" width="3%"><input type="text" name="ProductIDAmount<%=ProductID%>" value="<%=bundle.getBundleProducts().get(product)%>" size="1" style="text-align: center;"/></td>
                            <%} else {%>
                        <td align="center" width="1%"><input type="checkbox" name="productChoice" value="<%=ProductID%>"></td>
                        <td align="center" width="3%"><input type="text" name="ProductIDAmount<%=ProductID%>" value="1" size="1" style="text-align: center;"/></td>
                            <%}%>

                    </tr>
                </tbody>
                <%}%>
            </table>

            <p align="center">
                Save the changes:
                <br>
                <input type="hidden" name="command" value="editBundle" />
                <input type="submit" value="Update"/></p>
        </form>
        <form action="FrontController" method="POST">
            <input type="hidden" name="command" value="goToJsp" />
            <input type="hidden" name="goToJsp" value="viewAllBundles" />
            <p align="center"><input type="submit" value="Go Back" /></p>
        </form>    
    </body>
</html>
