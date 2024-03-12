<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Edit Form</title>
	<link rel="stylesheet" href="css/style.css">
	<script type="text/javascript" src="js/jquery-3.7.1.min.js"></script>
	<script type="text/javascript" src="js/jquery.validate.min.js"></script>
</head>
<body>
	<jsp:directive.include file="header.jsp"/>
	
	<div align="center">
		<h2 class="pageheading">
			Edit My Profile
		</h2>
	</div>
	
	<div align="center">
		<form action="update_profile" method="post" id="customerForm">
		
		<table class="form">
			<tr>
				<td align="right">Email</td>
				<td align="left"><b>${loggedCustomer.email }</b> (Could not be changed)</td>
			</tr>
			<tr>
				<td align="right">Full Name</td>
				<td align="left"><input type="text" id="fullName" name="fullName" size="45" value="${loggedCustomer.fullName }"/></td>
			</tr>
			<tr>
				<td align="right">Phone Number</td>
				<td align="left"><input type="text" id="phoneNumber" name="phoneNumber" size="45" value="${loggedCustomer.phoneNumber}"/></td>
			</tr>
			<tr>
				<td align="right">Address</td>
				<td align="left"><input type="text" id="address" name="address" size="45" value="${loggedCustomer.address }"/></td>
			</tr>
			<tr>
				<td align="right">City</td>
				<td align="left"><input type="text" id="city" name="city" size="45" value="${loggedCustomer.city }"/></td>
			</tr>
			<tr>
				<td align="right">Zipcode</td>
				<td align="left"><input type="text" id="zipcode" name="zipcode" size="45" value="${loggedCustomer.zipcode }"/></td>
			</tr>
			<tr>
				<td align="right">Country</td>
				<td align="left"><input type="text" id="country" name="country" size="45" value="${loggedCustomer.country}"/></td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<i>(Leave password field blank if you don't want to change password)</i>
				</td>			
			</tr>
			<tr>
				<td align="right">Password</td>
				<td align="left"><input type="password" id="password" name="password" size="45"/></td>
			</tr>
			<tr>
				<td align="right">Confirmed Password</td>
				<td align="left"><input type="password" id="confirmedPassword" name="confirmedPassword" size="45"/></td>
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
			
			$("#customerForm").validate({
				rules:{
					email:{
						required:true,
						email:true
					},
					fullName: "required",
					confirmedPassword:{
						equalTo:"#password"
					},
					phoneNumber:"required",
					address:"required",
					city:"required",
					zipcode:"required",
					country:"required"
				},
				messages:{
					email:{
						required: "Please enter email address",
						email: "Please enter a valid email address"
					},
					fullName: "Please enter fullname",
					confirmedPassword:{
						equalTo:"Confirm password does not match"
					},
					phoneNumber:"Please enter phone number",
					address:"Please enter address",
					city:"Please enter city",
					zipcode:"Please enter zipcode",
					country:"Please enter country"
				}
			});
				
			$("#buttonCancel").click(function(){
				history.go(-1);
			});
		});
	
	</script>
</html>