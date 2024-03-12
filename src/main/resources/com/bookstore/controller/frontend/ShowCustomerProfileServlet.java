package main.resources.com.bookstore.controller.frontend;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import main.resources.com.bookstore.service.CustomerServices;

import java.io.IOException;

public class ShowCustomerProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public ShowCustomerProfileServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		CustomerServices customerServices = new CustomerServices(request, response);
		customerServices.showCustomerProfile();
	}

}
