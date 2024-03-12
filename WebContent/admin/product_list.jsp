<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Manage Products</title>
	<link rel="stylesheet" href="../css/style.css">
	<script type="text/javascript" src="../js/jquery-3.7.1.min.js"></script>
	<script type="text/javascript" src="../js/jquery.validate.min.js"></script>
</head>
<body>
	<jsp:directive.include file="header.jsp"/>
	
	<div align="center">
		<h2 class="pageheading">Products Management</h2>
		<h3><a href ="new_product">Add New Product</a></h3>
	</div>
	
	<c:if test="${message != null }">
	<div align="center">
		<h4 class="message">${message}</h4>
	</div>
	</c:if>
	
	<div align="center">
		<table border="1" cellpadding="5">
			<tr>
				<th>Index</th>
				<th>Id</th>
				<th>Product Image</th>
				<th>Name</th>
				<th>Made in</th>
				<th>Category</th>
				<th>Price</th>
				<th>Availability Status</th>
				<th>Produced Date</th>
				<th>Actions</th>
			</tr>
			<c:forEach var="product" items="${listProduct}" varStatus="status">
			<tr>
				<td>${status.index + 1}</td>
				<td>${product.productId}</td>
				<td>
					<img src="data:image/png;base64,${product.base64Image}" width="100" height="55"/>
				</td>
				<td>${product.name}</td>
				<td>${product.countryMade}</td>
				<td>${product.category.name}</td>
				<td>$${product.price}</td>
				<td>${product.approve}</td>
				<td><fmt:formatDate pattern ="MM/dd/yyyy" value="${product.addDate}"/></td>
				<td>
					<a href="edit_product?id=${product.productId}">Edit</a> &nbsp;
					<a href="javascript:void(0);" class="deleteLink" id="${product.productId}">Delete</a>
				</td>
			</tr>
			</c:forEach>
		</table>
	</div>
	
	<jsp:directive.include file="footer.jsp"/>

	<script>
		$(document).ready(function(){
			$(".deleteLink").each(function(){
				$(this).on("click", function(){
					productId = $(this).attr("id");
					if(confirm("Delete the product with ID " + productId + "?")){
						window.location = "delete_product?id=" + productId;
					};
				});
			});
		});
	</script>
</body>
</html>