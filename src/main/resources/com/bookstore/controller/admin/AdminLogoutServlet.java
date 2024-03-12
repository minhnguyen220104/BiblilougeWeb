package main.resources.com.bookstore.controller.admin;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class AdminLogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public AdminLogoutServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.removeAttribute("useremail");
		
		RequestDispatcher requestDispatcher =request.getRequestDispatcher("login.jsp");
		requestDispatcher.forward(request, response);
	}

}
