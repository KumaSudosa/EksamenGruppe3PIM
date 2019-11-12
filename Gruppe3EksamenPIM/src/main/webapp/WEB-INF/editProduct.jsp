<%-- 
    Document   : EditProduct
    Created on : 12-11-2019, 10:34:52
    Author     : Andreas
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="businessLogic.Product"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit Product</title>
    </head>
    <body>                 <!-- skal lige fixes så den displayer product Id -->
        <h1 align="center">Edit Product Information for product =showProductID</h1>
        <form action="FrontController">
            <%
                Product productList = Product.findProductOnID((int) request.getAttribute("productID"));
                String ProductName = productList.getName();
                String ProductDescription = productList.getDescription();
                ArrayList<String> ProductDist = productList.getDistributors();
                String picturePath = productList.getPicturePath();

            %>
            <p align="center">
                Product Name:
                <br>
                <input type="text" name="Product Name" value="<%=ProductName%>"/>
            </p>

            <p align="center">
                Product Description:
                <br>
                <textarea name="Product Description" rows="8" cols="40" value="<%=ProductDescription%>"></textarea>
            </p>

            <p align="center">
                Product Distributors:
                <br>
                <input type="text" name="Product Distributors" value="<%=ProductDist%>"/>
                <img onclick="newField()" src="decorations/addIcon.png" width="15" height="15" alt="addIcon"/> 
            </p>
            
            <p align="center">
                Select Picture:
                <input type = "file" name = "file" size = "50" value="<%=picturePath%>"/>
            </p>

        </form>
        <script>
            function newField() {
                var x = document.createElement("INPUT");
                var br = document.createElement('br');
                var br2 = document.createElement('br');
                x.setAttribute("type", "text");
                x.setAttribute("name", "Product Distributors");
                document.getElementById("myDIV").appendChild(x);
                document.getElementById("myDIV").appendChild(br);
                document.getElementById("myDIV").appendChild(br2);
            }
        </script>
    </body>
</html>
