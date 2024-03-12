<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Customer Profile</title>
	<link rel="stylesheet" href="css/style.css">
</head>
<body>

	<jsp:directive.include file="header.jsp"/>
	
	<div align="center">
		<br/>
		<h3>Welcome, ${loggedCustomer.fullName }</h3>
		<br/>
		<table class="normal">
			<tr>
				<td><b>Email Address</b></td>
				<td>${loggedCustomer.email }</td>
			</tr>
			<tr>
				<td><b>Full Name</b></td>
				<td>${loggedCustomer.fullName }</td>
			</tr>
			<tr>
				<td><b>Phone Number</b></td>
				<td>${loggedCustomer.phoneNumber }</td>
			</tr>
			<tr>
				<td><b>Address</b></td>
				<td>${loggedCustomer.address }</td>
			</tr>
			<tr>
				<td><b>City</b></td>
				<td>${loggedCustomer.city }</td>
			</tr>
			<tr>
				<td><b>Zipcode</b></td>
				<td>${loggedCustomer.zipcode }</td>
			</tr>
			<tr>
				<td><b>Country</b></td>
				<td>${loggedCustomer.country }</td>
			</tr>
			<tr><td>&nbsp;</td></tr>
			<tr>
				<td colspan="2" align="center"><b><a href="edit_profile">Edit My Profile</a></b></td>
			</tr>
		</table>
	</div>
	
	<jsp:directive.include file="footer.jsp"/>
	
</body>
</html>