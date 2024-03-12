package main.resources.com.bookstore.controller.frontend;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import main.resources.com.bookstore.service.ProductServices;

/**
 * Servlet implementation class ViewProductServlet
 */
public class ViewProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public ViewProductServlet() {
    	super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ProductServices productServices = new ProductServices(request, response);
		productServices.viewProductDetail();
	}

}
