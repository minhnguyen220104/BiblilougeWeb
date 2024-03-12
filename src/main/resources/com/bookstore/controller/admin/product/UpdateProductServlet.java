package main.resources.com.bookstore.controller.admin.product;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import main.resources.com.bookstore.service.ProductServices;

@MultipartConfig(
		fileSizeThreshold = 1024 * 10,  //10KB
		maxFileSize = 1024 * 300,       //300 KB
		maxRequestSize = 1024 * 1024    //1 MB
)
public class UpdateProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public UpdateProductServlet() {
        super();
    }
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ProductServices productServices = new ProductServices(request, response);
		productServices.updateProduct();
	}

}
