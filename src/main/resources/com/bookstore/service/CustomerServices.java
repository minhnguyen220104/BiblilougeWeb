package main.resources.com.bookstore.service;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import main.resources.com.bookstore.dao.CategoryDAO;
import main.resources.com.bookstore.dao.CustomerDAO;
import main.resources.com.bookstore.dao.HashGenerator;
import main.resources.com.bookstore.dao.ProductDAO;
import main.resources.com.bookstore.entity.Category;
import main.resources.com.bookstore.entity.Customer;
import main.resources.com.bookstore.entity.Product;

public class CustomerServices {
	private HttpServletRequest request;
	private HttpServletResponse response;
	private CustomerDAO customerDAO;
	
	public CustomerServices(HttpServletRequest request, HttpServletResponse response) {
		super();
		this.request = request;
		this.response = response;
		this.customerDAO = new CustomerDAO();
		
	}
	
	public void listCustomer() throws ServletException, IOException {
		listCustomer(null);
	}

	public void listCustomer(String message) throws ServletException, IOException {
		List<Customer> listCustomer = customerDAO.listAll();
		
		if(message != null) {
			request.setAttribute("message", message);
		}
		request.setAttribute("listCustomer", listCustomer);
		String listPage = "customer_list.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(listPage);
		requestDispatcher.forward(request, response);
	}

	public void createCustomer() throws ServletException, IOException {
		String email = request.getParameter("email");
		Customer existCustomer = customerDAO.findByEmail(email);
		
		if(existCustomer != null) {
			String message = "Could not create new customer. Customer with email " + email + "already exists";
			listCustomer(message);
		}else {
			Customer newCustomer = new Customer();
			updateCustomerFieldsFromForm(newCustomer);
			customerDAO.create(newCustomer);
			String message = "New Customer has been created successfull";
			listCustomer(message);
		}
	}
	
	private void updateCustomerFieldsFromForm(Customer customer) {
		String email = request.getParameter("email");
		String fullName = request.getParameter("fullName");
		String password = request.getParameter("password");
		String phoneNumber = request.getParameter("phoneNumber");
		String address = request.getParameter("address");
		String city = request.getParameter("city");
		String zipcode = request.getParameter("zipcode");
		String country = request.getParameter("country");
		
		if (email != null && !email.equals("")) {
			customer.setEmail(email);
		}
		
		customer.setFullName(fullName);	
		
		if (password != null && !password.equals("")) {
			customer.setPassword(password);	
		}
		customer.setPhoneNumber(phoneNumber);
		customer.setAddress(address);
		customer.setCity(city);
		customer.setZipcode(zipcode);
		customer.setCountry(country);
	}
	
	public void registerCustomer() throws ServletException, IOException {
		String email = request.getParameter("email");
		Customer existCustomer = customerDAO.findByEmail(email);
		String message = null;
		
		if(existCustomer != null) {
			message = "Could not register new customer. Customer with email " + email + "already exists";
		}else {
			
			Customer newCustomer = new Customer();
			updateCustomerFieldsFromForm(newCustomer);
			customerDAO.create(newCustomer);
			message = "You have registered successfull<br/>" + " <a href='login'>Click here<a/> to log in";
		}
		String messagePage = "frontend/message.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(messagePage);
		request.setAttribute("message", message);
		requestDispatcher.forward(request, response);
	}

	public void editCustomer() throws ServletException, IOException {
		Integer customerId = Integer.parseInt(request.getParameter("id"));
		Customer customer = customerDAO.get(customerId);
		String destPage = "customer_form.jsp";
		
		if (customer == null) {
			destPage = "message.jsp";
			String errorMessage = "Could not find customer with ID " + customerId;
			request.setAttribute("message", errorMessage);
			RequestDispatcher requestDispatcher = request.getRequestDispatcher(destPage);
			requestDispatcher.forward(request, response);
		} else {
			request.setAttribute("customer", customer);
			RequestDispatcher requestDispatcher = request.getRequestDispatcher(destPage);
			requestDispatcher.forward(request, response);
		}
	}

	public void updateCustomer() throws ServletException, IOException {
		Integer customerId = Integer.parseInt(request.getParameter("customerId"));
		String email = request.getParameter("email");
		Customer customerByEmail = customerDAO.findByEmail(email);
		String message = null;
		
		if(customerByEmail != null && customerByEmail.getCustomerId() != customerId) {
			message = "Could not update customer with ID " + customerId + " because there is an existing customer with the same email";
		}else {
			
			
			Customer customerById = customerDAO.get(customerId);
			updateCustomerFieldsFromForm(customerById);
			
			
			customerDAO.update(customerById);
			message = "Customer with ID " + customerId + " has been updated successfully";
			
		}
		listCustomer(message);
	}

	public void deleteCustomer() throws ServletException, IOException {
		Integer customerId = Integer.parseInt(request.getParameter("id"));
		customerDAO.delete(customerId);
		
		String message = "Customer with ID " + customerId + " has been deleted successfully";
		listCustomer(message);
		
	}

	public void showLogin() throws ServletException, IOException {
		String loginForm = "frontend/login.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(loginForm);
		dispatcher.forward(request, response);
	}

	public void doLogin() throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		Customer customer = customerDAO.checkLogin(email, password);
		
		if (customer == null) {
			String message = "Login failed. Please check your email and password.";
			request.setAttribute("message", message);
			showLogin();
			
		} else {
			request.getSession().setAttribute("loggedCustomer", customer);
			showCustomerProfile();
		}
	}
	
	public void showCustomerProfile() throws ServletException, IOException {
		String profilePage = "frontend/customer_profile.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(profilePage);
		dispatcher.forward(request, response);
	}

	public void showCustomerProfileEditForm() throws ServletException, IOException {
		String editPage = "frontend/edit_profile.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(editPage);
		dispatcher.forward(request, response);
	}

	public void updateCustomerProfile() throws ServletException, IOException {
		Customer customer = (Customer) request.getSession().getAttribute("loggedCustomer");
		updateCustomerFieldsFromForm(customer);
		customerDAO.update(customer);
		showCustomerProfile();
	}
}
