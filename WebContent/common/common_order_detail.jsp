<div align="center">
		<h2>Order Overview:</h2>
		<table>
		    <tr>
		        <td><b>Ordered By: </b></td>
		        <td>${order.customer.fullName}</td>
		    </tr>
		    <tr>
		        <td><b>Order Status: </b></td>
		        <td>${order.status}</td>
		    </tr>
		     <tr>
		        <td><b>Order Date: </b></td>
		        <td>${order.orderDate}</td>
		    </tr>
		    <tr>
		        <td><b>Payment Method: </b></td>
		        <td>${order.paymentMethod}</td>
		    </tr>
		    <tr>
		        <td><b>Number of Item: </b></td>
		        <td>${order.itemNo}</td>
		    </tr>
		     <tr>
		        <td><b>Total Amount: </b></td>
		        <td><fmt:formatNumber value="${order.total}" type="currency"/></td>
		    </tr>
		</table>
		<h2>Recipient Information</h2>
		<table>
		     <tr>
		        <td><b>Full Name: </b></td>
		        <td>${order.recipientName}</td>
		    </tr>
		     <tr>
		        <td><b>Phone: </b></td>
		        <td>${order.recipientPhone}</td>
		    </tr>
		     <tr>
		        <td><b>Address Line: </b></td>
		        <td>${order.shippingAddress}</td>
		    </tr>
		</table>
	</div>
	<div align="center">
	   <h2>Ordered Products</h2>
	   <table border="1">
	       <tr>
	           <th>Index</th>
	           <th>Item</th>
	           <th>Origin</th>
	           <th>Price</th>
	           <th>Quantity</th>
	           <th>Subtotal</th>
	       </tr>
	       <c:forEach items="${order.orderlines}" var="orderline" varStatus="status">
	       <tr>
	           <td>${status.index + 1}</td>
	           <td>
	               <img style="vertical-align: middle;" src="data:image/jpg;base64,${orderline.product.base64Image}" width="48" height="64"/>
	               ${orderline.product.name}
	           </td>
	           <td>${orderline.product.countryMade}</td>
	           <td><fmt:formatNumber value="${orderline.product.price}" type="currency"/></td>
	           <td>${orderline.quantity}</td>
	           <td><fmt:formatNumber value="${orderline.subtotal}" type="currency"/></td>
	       </tr>
	       </c:forEach>
	       <tr>
	           <td colspan="6" align="right">
	              <p>TOTAL:&nbsp;&nbsp;&nbsp;&nbsp;<fmt:formatNumber value="${order.total}" type="currency"/></p>
	           </td>
	       </tr>
	   </table>
	</div>