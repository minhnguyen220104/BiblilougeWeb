package main.resources.com.bookstore.controller.frontend.order;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import main.resources.com.bookstore.service.OrderServices;

import java.io.IOException;

public class PlaceOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public PlaceOrderServlet() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		OrderServices orderServices = new OrderServices(request, response);
		orderServices.placeOrder();
	}

}