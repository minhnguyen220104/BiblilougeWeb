package main.resources.com.bookstore.controller.frontend;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import main.resources.com.bookstore.dao.CategoryDAO;
import main.resources.com.bookstore.dao.ProductDAO;
import main.resources.com.bookstore.entity.Category;
import main.resources.com.bookstore.entity.Product;

import java.io.IOException;
import java.util.List;

@WebServlet("")
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public HomeServlet() {
        super();
        
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ProductDAO productDAO = new ProductDAO();
		
		List<Product> listNewProduct = productDAO.listNewProduct();
		
		request.setAttribute("listNewProduct", listNewProduct);
		
		String homepage = "/frontend/index.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(homepage);
		dispatcher.forward(request, response);
	}

}
