package main.resources.com.bookstore.service;

import java.io.IOException;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import main.resources.com.bookstore.dao.CategoryDAO;
import main.resources.com.bookstore.dao.ProductDAO;
import main.resources.com.bookstore.entity.Category;


public class CategoryServices {
	private CategoryDAO categoryDAO;
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	public CategoryServices(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
		
		categoryDAO = new CategoryDAO();
	}
	
	public void lisCategory() throws ServletException, IOException {
		listCategory(null);
	}

	public void listCategory(String message) throws ServletException, IOException {
		List<Category> listCategory = categoryDAO.listAll();
		
		request.setAttribute("listCategory", listCategory);
		
		if(message != null) {
			request.setAttribute("message", message);
		}
		
		String listPage = "category_list.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(listPage);
		requestDispatcher.forward(request, response);
		
	}
	
	public void createCategory() throws ServletException, IOException {
		String name = request.getParameter("name");
		String description = request.getParameter("description");
		
		Category existCategory = categoryDAO.findByName(name);
		
		if(existCategory != null) {
			String message = "Could not create new category. Category " + name + "already exists";
			request.setAttribute("message", message);
			RequestDispatcher dispatcher = request.getRequestDispatcher("message.jsp");
			dispatcher.forward(request, response);
		}else {
			Category newCategory = new Category();
			newCategory.setName(name);
			newCategory.setDescription(description);
			categoryDAO.create(newCategory);
			String message = "New category created successfully!";
			listCategory(message);
		}
	}

	public void editCategory() throws ServletException, IOException {
		int categoryId = Integer.parseInt(request.getParameter("id"));
		Category category = categoryDAO.get(categoryId);

		String destPage = "category_form.jsp";
		
		if (category == null) {
			destPage = "message.jsp";
			String errorMessage = "Could not find category with ID " + categoryId;
			request.setAttribute("message", errorMessage);
		} else {
			request.setAttribute("category", category);			
		}
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(destPage);
		requestDispatcher.forward(request, response);
		
	}

	public void updateCategory() throws ServletException, IOException {
		int categoryId = Integer.parseInt(request.getParameter("categoryId"));
		String name = request.getParameter("name");
		String description = request.getParameter("description");
		
		Category categoryById = categoryDAO.get(categoryId);
		
		Category categoryByName = categoryDAO.findByName(name);
		if(categoryByName != null && categoryByName.getCategoryId() != categoryById.getCategoryId()) {
			String message = "Could not update category. Category " + name + " already exists";
			request.setAttribute("message", message);
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("message.jsp");
			requestDispatcher.forward(request, response);
		}else {
			Category category = new Category();
			category.setCategoryId(categoryId);
			category.setName(name);
			category.setDescription(description);
			categoryDAO.update(category);
			
			String message = "Category has been updated successfully";
			listCategory(message);
		}
	}

	public void deleteCategory() throws ServletException, IOException {
		int categoryId = Integer.parseInt(request.getParameter("id"));
		ProductDAO productDAO = new ProductDAO();
		long numberOfProducts = productDAO.countByCategory(categoryId);
		String message;
		
		if(numberOfProducts >0) {
			message = "Could not delete the category(ID :%d) because it contains some products";
			message = String.format(message, numberOfProducts);
		}else {
			categoryDAO.delete(categoryId);
			message = "Category with ID " + categoryId + " has been deleted successfully";
		}
		
		listCategory(message);
		
	}
	
}
