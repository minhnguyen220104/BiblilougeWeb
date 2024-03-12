<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Results for "${keyword}"</title>
	<link rel="stylesheet" href="css/style.css">
</head>
<body>
	<jsp:directive.include file="header.jsp"/>
	
	<div class = "center">
		<c:if test="${fn:length(result) == 0}">
			<h2>No results for "${keyword}"</h2>
		</c:if>
		<c:if test="${fn:length(result) > 0}">
			<div class="product_group">
				<center><h2>Results for "${keyword}"</h2></center>
				<c:forEach items="${result}" var="product">
					<div>
						<div id="search-image">
							<div>
								<a href = "view_product?id=${product.productId}">
									<img class="product-small" src="data:image/png;base64,${product.base64Image}"/>
								</a>
							</div>
						</div>
						<div id="search-description">
							<div>
								<h2><a href = "view_product?id=${product.productId}"><b>${product.name}</b></a></h2>
							</div>
							<div>Rating****</div>
							<div><i>Made in ${product.countryMade}</i></div>
							<div>
								<p>${fn:substring(book.description, 0, 100)}...</p>
							</div>
						</div>
						<div id="search-price">
							<h3>${product.price} SGD</h3>
							<h3><a href="">Add To Cart</a></h3>
						</div>
					</div>
				</c:forEach>
			</div>
		</c:if>
	</div>
	
	<jsp:directive.include file="footer.jsp"/>
</body>
</html>