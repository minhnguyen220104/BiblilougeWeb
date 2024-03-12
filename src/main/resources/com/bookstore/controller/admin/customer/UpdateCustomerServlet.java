package main.resources.com.bookstore.controller.admin.customer;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import main.resources.com.bookstore.service.CustomerServices;

import java.io.IOException;

public class UpdateCustomerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public UpdateCustomerServlet() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CustomerServices customerServices = new CustomerServices(request, response);
		customerServices.updateCustomer();
	}

}