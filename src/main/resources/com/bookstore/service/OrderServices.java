package main.resources.com.bookstore.service;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.paypal.api.payments.ItemList;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.ShippingAddress;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import main.resources.com.bookstore.controller.frontend.shoppingcart.ShoppingCart;
import main.resources.com.bookstore.dao.OrderDAO;
import main.resources.com.bookstore.entity.Customer;
import main.resources.com.bookstore.entity.Orderline;
import main.resources.com.bookstore.entity.Product;
import main.resources.com.bookstore.entity.ProductOrder;

public class OrderServices {
	private OrderDAO orderDAO;
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	
	
	public OrderServices(HttpServletRequest request, HttpServletResponse response) {
		super();
		this.request = request;
		this.response = response;
		this.orderDAO = new OrderDAO();
	}
	
	public void listAllOrder() throws ServletException, IOException {
		listAllOrder(null);
	}

	public void listAllOrder(String message) throws ServletException, IOException {
		List<ProductOrder> listOrder = orderDAO.listAll();
		
		if (message != null) {
			request.setAttribute("message", message);
		}
		
		request.setAttribute("listOrder", listOrder);
		System.out.println(listOrder);
		
		String listPage = "order_list.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(listPage);
		dispatcher.forward(request, response);
	}
	
	public void viewOrderDetailForAdmin() throws ServletException, IOException {
		int orderId = Integer.parseInt(request.getParameter("id"));
		
		ProductOrder order = orderDAO.get(orderId);
		request.setAttribute("order", order);
		
		String detailPage = "order_detail.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(detailPage);
		dispatcher.forward(request, response);
		
	}
	
	public void showCheckoutForm() throws ServletException, IOException {
		HttpSession session = request.getSession();
		ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute("cart");
		
		// tax is 10% of subtotal
		float tax = shoppingCart.getTotalAmount() * 0.1f;
		
		// shipping fee is 1.0 USD per copy
		float shippingFee = shoppingCart.getTotalQuantity() * 1.0f;
		
		float total = shoppingCart.getTotalAmount() + tax + shippingFee;
		
		session.setAttribute("total", total);
		
		CommonUtility.generateCountryList(request);
		
		String checkOutPage = "frontend/checkout.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(checkOutPage);
		dispatcher.forward(request, response);
	}
	
	public void placeOrder() throws ServletException, IOException {
		String paymentMethod = request.getParameter("paymentMethod");
		ProductOrder order = readOrderInfo();
		
		if (paymentMethod.equals("paypal")) {
			PaymentServices paymentServices = new PaymentServices(request, response);
			request.getSession().setAttribute("order4Paypal", order);
			paymentServices.authorizePayment(order);
		}else {
			// Cash on Delivery
			placeOrderCOD(order);
		}
	}
	
	public Integer placeOrderPaypal(Payment payment) {
		ProductOrder order = (ProductOrder) request.getSession().getAttribute("order4Paypal");
		ItemList itemList = payment.getTransactions().get(0).getItemList();
		ShippingAddress shippingAddress = itemList.getShippingAddress();
		String shippingPhoneNumber = itemList.getShippingPhoneNumber();
		
		String recipientName = shippingAddress.getRecipientName();
		String[] names = recipientName.split(" ");
		
		order.setRecipientName(recipientName);
		order.setShippingAddress(shippingAddress.getLine1());
		order.setRecipientPhone(shippingPhoneNumber);
		
		return saveOrder(order);
	}
	
	private Integer saveOrder(ProductOrder order) {
		ProductOrder savedOrder = orderDAO.create(order);
		
		ShoppingCart shoppingCart = (ShoppingCart) request.getSession().getAttribute("cart");
		shoppingCart.clear();
		
		return savedOrder.getOrderId();
	}
	
	private ProductOrder readOrderInfo() {
		String paymentMethod = request.getParameter("paymentMethod");
		String fullname = request.getParameter("fullname");
		String phone = request.getParameter("phone");
		String address = request.getParameter("address");
		
		
		ProductOrder order = new ProductOrder();
		order.setRecipientName(fullname);
		order.setRecipientPhone(phone);
		order.setShippingAddress(address);
		order.setPaymentMethod(paymentMethod);
		
		HttpSession session = request.getSession();
		Customer customer = (Customer) session.getAttribute("loggedCustomer");
		order.setCustomer(customer);
		
		ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute("cart");
		Map<Product, Integer> items = shoppingCart.getItems();
		
		Iterator<Product> iterator = items.keySet().iterator();
		
		Set<Orderline> orderDetails = new HashSet<>();
		
		while (iterator.hasNext()) {
			Product product = iterator.next();
			Integer quantity = items.get(product);
			float subtotal = quantity * product.getPrice();
			
			Orderline orderDetail = new Orderline();
			orderDetail.setProduct(product);
			orderDetail.setOrder(order);
			orderDetail.setQuantity(quantity);
			orderDetail.setSubtotal(subtotal);
			orderDetails.add(orderDetail);
		}
		
		order.setOrderlines(orderDetails);

		float total = (float) session.getAttribute("total");
		
		order.setTotal(total);
		
		return order;
	}
	
	private void placeOrderCOD(ProductOrder order) throws ServletException, IOException {
		saveOrder(order);
		
		String message = "Thank you. Your order has been received." 
		+ " We will deliver your books within a few days.";
		request.setAttribute("message", message);
		String messagePage = "frontend/message.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(messagePage);
		dispatcher.forward(request, response);
	}
	
	public void listOrderByCustomer() throws ServletException, IOException {
		HttpSession session = request.getSession();
		Customer customer = (Customer) session.getAttribute("loggedCustomer");
		List<ProductOrder> listOrders = orderDAO.listByCustomer(customer.getCustomerId());
		
		request.setAttribute("listOrders", listOrders);
		
		String historyPage = "frontend/order_list.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(historyPage);
		dispatcher.forward(request, response);
	}
	
	public void showOrderDetailForCustomer() throws ServletException, IOException {
        int orderId = Integer.parseInt(request.getParameter("id"));
        
        HttpSession session = request.getSession();
        Customer customer = (Customer) session.getAttribute("loggedCustomer");
		
		ProductOrder order = orderDAO.get(orderId, customer.getCustomerId());
		request.setAttribute("order", order);
		
		String detailPage = "frontend/order_detail.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(detailPage);
		dispatcher.forward(request, response);
	}

	public void showEditOrderForm() throws ServletException, IOException {
		 Integer orderId = Integer.parseInt(request.getParameter("id"));
		
		 
		 HttpSession session = request.getSession();
		 Object isPendingBook = session.getAttribute("NewBookPendingToAddToOrder");
		 
		 if (isPendingBook == null) {
			 ProductOrder order = orderDAO.get(orderId);
			 session.setAttribute("order", order);
		 } else {
			 session.removeAttribute("NewBookPendingToAddToOrder");
		 }
		 
		 CommonUtility.generateCountryList(request);
		 
		 String editPage = "order_form.jsp";
		 RequestDispatcher dispatcher = request.getRequestDispatcher(editPage);
		 dispatcher.forward(request, response);
	}

	public void updateOrder() throws ServletException, IOException {
		HttpSession session = request.getSession();
		ProductOrder order = (ProductOrder) session.getAttribute("order");
		
		String fullname = request.getParameter("fullname");
		String phone = request.getParameter("phone");
		String address = request.getParameter("address");
		
		String paymentMethod = request.getParameter("paymentMethod");
		String orderStatus = request.getParameter("orderStatus");
		
		order.setRecipientName(fullname);
		order.setRecipientPhone(phone);
		order.setShippingAddress(address);
		order.setPaymentMethod(paymentMethod);
		order.setStatus(orderStatus);
		
		String[] arrayProductId = request.getParameterValues("productId");
		String[] arrayPrice = request.getParameterValues("price");
		String[] arrayQuantity = new String[arrayProductId.length];
		
		for (int i = 1; i <= arrayQuantity.length; i++) {
			arrayQuantity[i - 1] = request.getParameter("quantity" + i);
		}
		
		Set<Orderline> orderDetails = order.getOrderlines();
		orderDetails.clear();
		
		float totalAmount = 0.0f;
		
		for (int i = 0; i < arrayProductId.length; i++) {
			int productId = Integer.parseInt(arrayProductId[i]);
			int quantity = Integer.parseInt(arrayQuantity[i]);
			float price = Float.parseFloat(arrayPrice[i]);
			
			float subtotal = price * quantity;
			
			Orderline orderDetail = new Orderline();
			orderDetail.setProduct(new Product(productId));
			orderDetail.setQuantity(quantity);
			orderDetail.setSubtotal(subtotal);
			orderDetail.setOrder(order);
			
			orderDetails.add(orderDetail);
			
			totalAmount += subtotal;
		}
		
		order.setTotal(totalAmount);
		
		orderDAO.update(order);
		
		String message = "The order " + order.getOrderId() + " has been updated successfully";
		
		listAllOrder(message);
	}

	public void deleteOrder() throws ServletException, IOException {
		Integer orderId = Integer.parseInt(request.getParameter("id"));
		orderDAO.delete(orderId);
		
		String message = "The order ID " + orderId + "has been deleted.";
		listAllOrder(message);
	}

}
