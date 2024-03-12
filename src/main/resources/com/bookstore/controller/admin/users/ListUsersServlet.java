package main.resources.com.bookstore.controller.admin.users;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import main.resources.com.bookstore.entity.Users;
import main.resources.com.bookstore.service.UserServices;

import java.io.IOException;
import java.util.List;

public class ListUsersServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public ListUsersServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserServices userServices = new UserServices(request, response);
		userServices.listUser();
		
	}
}
