package main.resources.com.bookstore.controller.frontend.shoppingcart;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import main.resources.com.bookstore.dao.ProductDAO;
import main.resources.com.bookstore.entity.Product;

import java.io.IOException;

public class AddProductToCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AddProductToCartServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		Integer productId = Integer.parseInt(request.getParameter("product_id"));
		
		Object cartObject = request.getSession().getAttribute("cart");
		
		ShoppingCart shoppingCart = null;
		
		if (cartObject != null && cartObject instanceof ShoppingCart) {
			shoppingCart = (ShoppingCart) cartObject;
		}else {
			shoppingCart = new ShoppingCart();
			request.getSession().setAttribute("cart", shoppingCart);
		}
		
		ProductDAO productDAO = new ProductDAO();
		Product product = productDAO.get(productId);
		
		shoppingCart.addItem(product);
		
		String cartPage = request.getContextPath().concat("/view_cart");
		response.sendRedirect(cartPage);
	}

}