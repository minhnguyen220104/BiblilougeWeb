package main.resources.com.bookstore.controller.frontend.shoppingcart;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import main.resources.com.bookstore.entity.Product;

import java.io.IOException;

public class RemoveProductFromCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public RemoveProductFromCartServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		Integer productId = Integer.parseInt(request.getParameter("product_id"));
		Object cartObject = request.getSession().getAttribute("cart");
		ShoppingCart shoppingCart = (ShoppingCart) cartObject;
	
		shoppingCart.removeItem(new Product(productId));
		
		String cartPage = request.getContextPath().concat("/view_cart");
		response.sendRedirect(cartPage);
	}

}
