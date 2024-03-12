<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Bibliolouge - ${category.name}</title>
	<link rel="stylesheet" href="css/style.css"/>
</head>
<body>
	<jsp:directive.include file="header.jsp"/>
	
	<div class ="center">
		<h2>${category.name}</h2>
	</div>
	
	<div class="product_group">
		<c:forEach items="${listProduct}" var="product">
			<div class="product">
				<div>
					<a href = "view_product?id=${product.productId}">
						<img class="product-small" src="data:image/png;base64,${product.base64Image}"/>
					</a>
				</div>
				<div>
					<a href = "view_product?id=${product.productId}">
						<b>${product.name}</b>
					</a>
				</div>
				<div>Rating****</div>
				<div><i>Made in ${product.countryMade}</i></div>
				<div><b>$${product.price}</b></div>
			</div>
		</c:forEach>
	</div>
	
	<jsp:directive.include file="footer.jsp"/>
</body>
</html>