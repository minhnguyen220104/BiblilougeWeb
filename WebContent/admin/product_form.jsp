<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Add New Product</title>
	<link rel="stylesheet" href="../css/style.css">
	<link rel="stylesheet" href="../css/jquery-ui.min.css">
	<link rel="stylesheet" href="//netdna.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" />
	<link rel="stylesheet" href="..//css/richtext.min.css">
	
	<script type="text/javascript" src="../js/jquery-3.7.1.min.js"></script>
	<script type="text/javascript" src="../js/jquery.validate.min.js"></script>
	<script type="text/javascript" src="../js/jquery-ui.min.js"></script>
	<script type="text/javascript" src="../js/jquery.richtext.min.js"></script>
</head>
<body>
	<jsp:directive.include file="header.jsp"/>
	
	<div align="center">
		<h2 class="pageheading">
			<c:if test="${product != null}">Edit Product</c:if>
			<c:if test="${product == null}">Add New Product</c:if>
		</h2>
	</div>
	
	<div align="center">
		<c:if test="${product != null}">
				<form action="update_product" method="post" id="productForm" enctype="multipart/form-data">
				<input type="hidden" name="productId" value="${product.productId}">
		</c:if>
		<c:if test="${product == null}">
				<form action="create_product" method="post" id="productForm" enctype="multipart/form-data">
		</c:if>
		
		<table class="form">
			<tr>
				<td>Category</td>
				<td>
					<select name="category">
						<c:forEach items="${listCategory}" var="category">
							<c:if test="${category.categoryId eq product.category.categoryId}">
								<option value="${category.categoryId}" selected>
							</c:if>
							<c:if test="${category.categoryId ne product.category.categoryId}">
								<option value="${category.categoryId}">
							</c:if>
									${category.name}
								</option>						
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td align="right">Name</td>
				<td align="left"><input type="text" id="name" name="name" size="20" value="${product.name}"/></td>
			</tr>
			<tr>
				<td align="right">Origin</td>
				<td align="left"><input type="text" id="countryMade" name="countryMade" size="20" value="${product.countryMade}"/></td>
			</tr>
			<tr>
				<td align="right">Product Image</td>
				<td align="left">
					<input type="file" id="picture" name="picture" size="100"/><br/>
					<img align="middle" id="thumbnail" alt="Image Preview" style="width:20%; margin-top:10px"
						src="data:image/png;base64,${product.base64Image}"
					/>
				</td>
			</tr>
			<tr>
				<td align="right">Price</td>
				<td align="left"><input type="text" id="price" name="price" size="20" value="${product.price}"/></td>
			</tr>
			<tr>
				<td align="right">Produced Date</td>
				<td align="left"><input type="text" id="addDate" name="addDate" size="20"
				 	value="<fmt:formatDate pattern ="MM/dd/yyyy" value='${product.addDate}'/>" /></td>
			</tr>
			<tr>
				<td align="right">Description</td>
				<td align="left">
					<textarea rows="5" cols="50" name="description" id="description">${product.description}</textarea>
				</td>
			<tr>
			<tr>
				<td align="right">Availability Status</td>
				<td align="left"><input type="text" id="approve" name="approve" size="20" value="${product.approve}"/></td>
			</tr>
			<tr><td>&nbsp;</td></tr>
			<tr>
				<td colspan="2" align="center">
					<button type="submit">Save</button>&nbsp;&nbsp;&nbsp;
					<button id="buttonCancel">Cancel</button>
				</td>
			</tr>
		</table>
		</form>
	</div>
	
	<jsp:directive.include file="footer.jsp"/>
</body>
	<script type="text/javascript">
	$(document).ready(function(){
		$("#addDate").datepicker();
		
		$('#description').richText();

		
		$("#picture").change(function(){
			showImageThumbnail(this);
		});
		
		$("#productForm").validate({
			rules:{
				category: "required",
				name: "required",
				countryMade: "required",
				
				<c:if test="${product == null}">
				picture: "required",
				</c:if>
				
				price:"required",
				addDate:"required",
				approve:"required"
			},
			messages:{
				category: "Please select the category of the item",
				name: "Please enter the item",
				countryMade: "Please enter the origin of the item",
				picture: "Please upload the image of the item",
				price:"Please enter the price of the item",
				addDate:"Please enter the produced date of the item",
				approve:"Please enter the availability status of the product"
			}
		});
			
		$("#buttonCancel").click(function(){
			history.go(-1);
		});
			
	});
	
	function showImageThumbnail(fileInput) {
		var file = fileInput.files[0];
		
		var reader = new FileReader();
		
		reader.onload = function(e) {
			$("#thumbnail").attr("src", e.target.result);
		};
		
		reader.readAsDataURL(file);
	}
	
	</script>
</html>