<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div align="center">
	<div>
		<a href="${pageContext.request.contextPath}/admin/">
			<img src="${pageContext.request.contextPath}/images/logo.png" />
		</a>
	</div>
	<div>
		Welcome, <c:out value="${sessionScope.useremail}"/> | <a href="logout">Logout</a>
		</br></br>
	</div>
	<div id="headermenu">
		<div>
			<a href="list_users">
				<img src="${pageContext.request.contextPath}/images/users.png" width="66" height="66"/></br>Users
			</a>
		</div>
		
		<div>
			<a href="list_category">
				<img src="${pageContext.request.contextPath}/images/category.png" width="66" height="66" /></br>Categories
			</a>
		</div>
		
		<div>
			<a href="list_product">
				<img src="${pageContext.request.contextPath}/images/products.png" width="66" height="66"/></br>Products
			</a>
		</div>
		
		<div>
			<a href="list_customer">
				<img src="${pageContext.request.contextPath}/images/customer.png" width="66" height="66" /></br>Customers
			</a>
		</div>
		
		<div>
			<a href="reviews">
				<img src="${pageContext.request.contextPath}/images/review.png" width="66" height="66"/></br>Reviews
			</a>
		</div>
		
		<div>
			<a href="list_order">
				<img src="${pageContext.request.contextPath}/images/order.png" width="66" height="66" /></br>Orders
			</a>
		</div>
		
	</div>
</div>
