package main.resources.com.bookstore.service;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import main.resources.com.bookstore.dao.CategoryDAO;
import main.resources.com.bookstore.dao.ProductDAO;
import main.resources.com.bookstore.entity.Category;
import main.resources.com.bookstore.entity.Product;

public class ProductServices {
	private CategoryDAO categoryDAO;
	private ProductDAO productDAO;
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	public ProductServices(HttpServletRequest request, HttpServletResponse response) {
		super();
		this.request = request;
		this.response = response;
		productDAO = new ProductDAO();
		categoryDAO = new CategoryDAO();
	}
	
	public void listProduct() throws ServletException, IOException {
		listProduct(null);
	}

	public void listProduct(String message) throws ServletException, IOException {
		List<Product> listProduct = productDAO.listAll();
		
		request.setAttribute("listProduct", listProduct);
		
		if(message != null) {
			request.setAttribute("message", message);
		}
		
		String listPage = "product_list.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(listPage);
		requestDispatcher.forward(request, response);
		
	}

	public void showBookNewForm() throws ServletException, IOException {
		List<Category> listCategory = categoryDAO.listAll();
		request.setAttribute("listCategory", listCategory);
		
		String newPage = "product_form.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(newPage);
		requestDispatcher.forward(request, response);
		
	}

	public void createProduct() throws ServletException, IOException{
		String name = request.getParameter("name");
		
		Product existedProduct = productDAO.findByName(name);
		if(existedProduct != null) {
			String message= name + " was already addad";
			listProduct(message);
			return;
		}
		
		Product newProduct = new Product();
		readProductFields(newProduct);
		
		Product createdProduct = productDAO.create(newProduct);
		if(createdProduct.getProductId() > 0) {
			String message = "A new item has been added successfully!";
			request.setAttribute("message", message);
			listProduct(message);
		}
		

	}

	public void readProductFields(Product product) throws IOException, ServletException {
		String name = request.getParameter("name");
		String description = request.getParameter("description");
		String countryMade = request.getParameter("countryMade");
		float price = Float.parseFloat(request.getParameter("price"));
		byte approve = Byte.parseByte(request.getParameter("approve"));
		
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date addDate = null;
		
		try {
			addDate = dateFormat.parse(request.getParameter("addDate"));
		}catch (ParseException ex){
			ex.printStackTrace();
			throw new ServletException("Error parsing produced date (format: MM/dd/yyyy)");
		}
		
		product.setName(name);
		product.setDescription(description);
		product.setCountryMade(countryMade);
		product.setAddDate(addDate);
		product.setPrice(price);
		
		Integer categoryId = Integer.parseInt(request.getParameter("category"));
		Category category = categoryDAO.get(categoryId);
		product.setCategory(category);
		product.setApprove(approve);
		
		
		Part part = request.getPart("picture");
		if(part != null && part.getSize() > 0) {
			long size = part.getSize();
			byte[] imageBytes = new byte[(int)size];
			
			InputStream inputStream = part.getInputStream();
			inputStream.read(imageBytes);
			inputStream.close();
			
			product.setPicture(imageBytes);
		}
	}
	
	public void editProduct() throws ServletException, IOException {
		Integer productId = Integer.parseInt(request.getParameter("id"));
		Product product = productDAO.get(productId);
		String destPage = "product_form.jsp";
		
		if (product != null) {
			List<Category> listCategory = categoryDAO.listAll();
			
			request.setAttribute("product", product);
			request.setAttribute("listCategory", listCategory);
			
		} else {
			destPage = "message.jsp";
			String message = "Could not find product with ID " + productId;
			request.setAttribute("message", message);			
		}
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(destPage);
		requestDispatcher.forward(request, response);
		
	}

	public void updateProduct() throws IOException, ServletException {
		Integer productId = Integer.parseInt(request.getParameter("productId"));
		String name = request.getParameter("name");
		
		Product existedProduct = productDAO.get(productId);
		Product productByName = productDAO.findByName(name);
		
		if(!existedProduct.equals(productByName)) {
			String message = "Could not update product because there is another product have the same name";
			listProduct(message);
			return;
		}
		
		readProductFields(existedProduct);
		
		productDAO.update(existedProduct);
		String message = "The product has been updated successfully";
		listProduct(message);
		
	}

	public void deleteProduct() throws ServletException, IOException {
		Integer productId = Integer.parseInt(request.getParameter("id"));
		Product product = productDAO.get(productId);
		
		if (product == null) {
			String message = "Could not find product with ID " + productId 
					+ ", or it might have been deleted";
			request.setAttribute("message", message);
			request.getRequestDispatcher("message.jsp").forward(request, response);
			
		} else {
			String message = "The product has been deleted successfully.";
			productDAO.delete(productId);			
			listProduct(message);		
		}
		
	}

	public void listProductByCategory() throws ServletException, IOException {
		Integer categoryId = Integer.parseInt(request.getParameter("id"));
		List<Product> listProduct = productDAO.listByCategory(categoryId);
		Category category = categoryDAO.get(categoryId);
		
		if (category == null) {
			String message = "Sorry, the category ID " + categoryId + " is not available.";
			request.setAttribute("message", message);
			request.getRequestDispatcher("frontend/message.jsp").forward(request, response);
			
			return;
		}
		
		request.setAttribute("listProduct", listProduct);
		request.setAttribute("category", category);
		
		String listPage = "frontend/product_list_by_category.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(listPage);
		requestDispatcher.forward(request, response);
		
	}

	public void viewProductDetail() throws ServletException, IOException {
		Integer productId = Integer.parseInt(request.getParameter("id"));
		Product product = productDAO.get(productId);
		
		request.setAttribute("product", product);
		
		String detailPage = "frontend/product_detail.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(detailPage);
		requestDispatcher.forward(request, response);
		
	}

	public void search() throws ServletException, IOException {
		String keyword = request.getParameter("keyword");
		List<Product> result = null;
		
		if(keyword.equals("")) {
			result = productDAO.listAll();
		}else {
			result = productDAO.search(keyword);
		}
		
		request.setAttribute("keyword", keyword);
		request.setAttribute("result", result);
		
		String resultPage = "frontend/search_result.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(resultPage);
		requestDispatcher.forward(request, response);
		
	}
	
}

