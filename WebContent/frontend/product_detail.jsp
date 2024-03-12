<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>${product.name}</title>
	<link rel="stylesheet" href="css/style.css">
	<script type="text/javascript" src="js/jquery-3.7.1.min.js"></script>
</head>
<body>
	<jsp:directive.include file="header.jsp"/>
	
	<div class = "center">
		<table class="product">
			<tr style="height:30px">
				<td colspan="3" align="left">
					<p id="product-name">${product.name}<p>
				</td>
			</tr>
			<tr>
				<td rowspan="2">
					<img class="product-large" src="data:image/png;base64,${product.base64Image}"/>
				</td>
				<td valign="top" align="left">
					Rating****
				</td>
				<td valign="top" rowspan="2">
					<h2>$${product.price}</h2>
					<br/>
					<button id="buttonAddToCart">Add to Cart</button>
				</td>
			</tr>
			<tr>
				<td id="description">
					${product.description}
				</td>
			</tr>
			<tr><td>&nbsp;</td></tr>
			<tr>
				<td><h2>Customer Reviews</h2></td>
				<td colspan="2" align="center">
					<button>Write a Customer Reivew</button>
				</td>
			</tr>
		</table>
	</div>
	
	<jsp:directive.include file="footer.jsp"/>
	
	 <script type="text/javascript">
    $(document).ready(function(){
        $("#buttonAddToCart").click(function(){
        	window.location = "add_to_cart?product_id=" + ${product.productId};
	    });
    });
    </script>
</body>
</html>