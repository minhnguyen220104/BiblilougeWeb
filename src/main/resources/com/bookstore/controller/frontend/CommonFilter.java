package main.resources.com.bookstore.controller.frontend;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import main.resources.com.bookstore.dao.CategoryDAO;
import main.resources.com.bookstore.entity.Category;

import java.io.IOException;
import java.util.List;

public class CommonFilter extends HttpFilter implements Filter {
	private CategoryDAO categoryDAO;

    public CommonFilter() {
        categoryDAO = new CategoryDAO();
    }
    
    public void init(FilterConfig fConfig) throws ServletException {

	}
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());
		
		if(!path.startsWith("/admin/")) {
			List<Category> listCategory = categoryDAO.listAll();
			request.setAttribute("listCategory", listCategory);
		}
		
		chain.doFilter(request, response);
	}
	
	public void destroy() {
		
	}

	

}
