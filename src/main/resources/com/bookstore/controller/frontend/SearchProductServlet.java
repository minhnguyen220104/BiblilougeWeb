package main.resources.com.bookstore.controller.frontend;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import main.resources.com.bookstore.service.ProductServices;

public class SearchProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public SearchProductServlet() {
        super();
    }

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ProductServices productServices = new ProductServices(request, response);
		productServices.search();
	}

    
}
