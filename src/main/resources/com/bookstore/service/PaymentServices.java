package main.resources.com.bookstore.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Details;
import com.paypal.api.payments.Item;
import com.paypal.api.payments.ItemList;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.PayerInfo;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.ShippingAddress;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import main.resources.com.bookstore.entity.Customer;
import main.resources.com.bookstore.entity.Orderline;
import main.resources.com.bookstore.entity.Product;
import main.resources.com.bookstore.entity.ProductOrder;

public class PaymentServices {
	// snadbox:
	// private static final String CLIENT_ID = "";
	// private static final String CLIENT_SECRET = "";
	
	private static final String CLIENT_ID = "";
	private static final String CLIENT_SECRET = "";
	
	private String mode = "live";
	
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	public PaymentServices(HttpServletRequest request, HttpServletResponse response) {
		super();
		this.request = request;
		this.response = response;
	}
	
	public void authorizePayment(ProductOrder order) throws ServletException, IOException {
		Payer payer = getPayerInformation(order);
		RedirectUrls redirectUrls = getRedirectURLs();
		
		List<Transaction> transactions = getTransactionInformation(order);
		
		Payment requestPayment = new Payment();
		requestPayment.setPayer(payer)
		              .setRedirectUrls(redirectUrls)
		              .setIntent("authorize")
		              .setTransactions(transactions);
		
		System.out.println("====== REQUEST PAYMENT: ======");
		System.out.println(requestPayment);
		
		APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, mode);
		
		try {
			Payment authorizedPayment = requestPayment.create(apiContext);
			System.out.println("====== AUTHORIZED PAYMENT: ======");
			System.out.println(authorizedPayment);
			
			String approvalURL = getApprovalURL(authorizedPayment);
			
			response.sendRedirect(approvalURL);
		} catch (PayPalRESTException e) {
			e.printStackTrace();
			throw new ServletException("Error in authorizing payment.");
		}
		
		// get approval link
		
		// redict to Paypal's payment page
		
	}
	
	private String getApprovalURL(Payment authorizedPayment) {
		String approvalURL = null;
		
		List<Links> links = authorizedPayment.getLinks();
		
		for (Links link : links) {
			if (link.getRel().equalsIgnoreCase("approval_url")) {
				approvalURL = link.getHref();
				break;
			}
		}
		
		return approvalURL;
	}

	private Payer getPayerInformation(ProductOrder order) {
		// get payer information
		Payer payer = new Payer();
		payer.setPaymentMethod("paypal");
		
		Customer customer = order.getCustomer();
		
		PayerInfo payerInfo = new PayerInfo();
		payerInfo.setFirstName(customer.getFullName());
		payerInfo.setEmail(customer.getEmail());
		payer.setPayerInfo(payerInfo);
		return payer;
	}
	
	private RedirectUrls getRedirectURLs() {
		// get redirect URLs
		String requestURL = request.getRequestURL().toString();
		String requestURI = request.getRequestURI();
		String baseURL = requestURL.replace(requestURI, "").concat(request.getContextPath());
		
		RedirectUrls redirectUrls = new RedirectUrls();
		String cancelUrl = baseURL.concat("/view_cart");
		String returnUrl = baseURL.concat("/review_payment");
		
		System.out.println("Return URL: " + returnUrl);
		System.out.println("Cance URL: " + cancelUrl);
		
		redirectUrls.setCancelUrl(cancelUrl);
		redirectUrls.setReturnUrl(returnUrl);
		
		return redirectUrls;
	}
	
	private Amount getAmountDetails(ProductOrder order) {
				
		Amount amount = new Amount();
		amount.setCurrency("USD");
		amount.setTotal(String.format("%.2f",order.getTotal()));
		
		return amount;
	}
	
	private ShippingAddress getRecipientInformation(ProductOrder order) {
		// shipping address (recipient info)
		ShippingAddress shippingAddress = new ShippingAddress();
		String recipientName = order.getRecipientName();
		shippingAddress.setRecipientName(recipientName)
		               .setPhone(order.getRecipientPhone())
		               .setLine1(order.getShippingAddress());
		return shippingAddress;
	}
	
	private List<Transaction> getTransactionInformation(ProductOrder order) {
		// get transaction details
		Transaction transaction = new Transaction();
		transaction.setDescription("Books ordered on SUHA");
		Amount amount = getAmountDetails(order);
		transaction.setAmount(amount);
		
		ItemList itemList = new ItemList();
		ShippingAddress shippingAddress = getRecipientInformation(order);
		itemList.setShippingAddress(shippingAddress);
		
		List<Item> paypalItems = new ArrayList<>();
		Iterator<Orderline> iterator = order.getOrderlines().iterator();
		
		while (iterator.hasNext()) {
			Orderline orderDetail = iterator.next();
			Product product = orderDetail.getProduct();
			Integer quantity = orderDetail.getQuantity();
			
			Item paypalItem = new Item();
			paypalItem.setCurrency("USD")
			          .setName(product.getName())
			          .setQuantity(String.valueOf(quantity))
			          .setPrice(String.format("%.2f",product.getPrice()));
			
			paypalItems.add(paypalItem);
		}
		
		itemList.setItems(paypalItems);
		transaction.setItemList(itemList);
		
		List<Transaction> listTransaction = new ArrayList<>();
		listTransaction.add(transaction);
		
		return listTransaction;
	}

	public void reviewPayment() throws ServletException, IOException {
		String paymentId = request.getParameter("paymentId");
		String payerId = request.getParameter("PayerID");
		
		if (paymentId == null || payerId == null) {
			throw new ServletException("Error in displaying payment review");
		}
		
		APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, mode);
		try {
			Payment payment = Payment.get(apiContext, paymentId);
			
			PayerInfo payerInfo = payment.getPayer().getPayerInfo();
			Transaction transaction = payment.getTransactions().get(0);
			ShippingAddress shippingAddress = transaction.getItemList().getShippingAddress();
			
			request.setAttribute("payer", payerInfo);
			request.setAttribute("recipient", shippingAddress);
			request.setAttribute("transaction", transaction);
			
			String reviewPage = "frontend/review_payment.jsp?paymentId=" + paymentId + "&PayerID=" + payerId;
			request.getRequestDispatcher(reviewPage).forward(request, response);
		} catch (PayPalRESTException e) {
			e.printStackTrace();
			throw new ServletException("Error in getting payment details from PayPal.");
		}
	}

	public Payment executePayment() throws PayPalRESTException {
		String paymentId = request.getParameter("paymentId");
		String payerId = request.getParameter("PayerID");
		
		PaymentExecution paymentExecution = new PaymentExecution();
		paymentExecution.setPayerId(payerId);
		
		Payment payment = new Payment().setId(paymentId);
		
		APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, mode);
		
		return payment.execute(apiContext, paymentExecution);
	} 
}
