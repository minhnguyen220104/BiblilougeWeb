package main.resources.com.bookstore.controller.frontend;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class CustomerLoginFilter extends HttpFilter implements Filter {
	private static final String[] loginRequiredURLs = {
			"/view_profile", "/edit_profile", "/update_profile","/place_order","/checkout","/view_orders","/show_order_detail"
	};
    public CustomerLoginFilter() {
        super();
    }

	public void destroy() {
		
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpSession session = httpRequest.getSession(false);
		String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());
		if(path.startsWith("/admin/")) {
			chain.doFilter(request, response);
			return;
		}
		boolean loggedIn = session != null && session.getAttribute("loggedCustomer") != null;
		String requestURL = httpRequest.getRequestURL().toString();
		if(!loggedIn && isLoginRequired(requestURL)) {
			String loginForm = "frontend/login.jsp";
			RequestDispatcher dispatcher = request.getRequestDispatcher(loginForm);
			dispatcher.forward(request, response);
		}else {
			chain.doFilter(request, response);
		}
	}
	
	public boolean isLoginRequired(String requestURL) {
		for (String loginRequiredURL : loginRequiredURLs) {
			if (requestURL.contains(loginRequiredURL)) {
				return true;
			}
		}
		
		return false;
	}

	public void init(FilterConfig fConfig) throws ServletException {
		
	}

}
