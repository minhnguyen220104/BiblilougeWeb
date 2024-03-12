package test.resources.com.bookstore.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceException;
import main.resources.com.bookstore.dao.UserDAO;
import main.resources.com.bookstore.entity.Users;

public class UserDAOTest{
	private static UserDAO userDAO;

	@BeforeClass
	public static void setupClass() throws Exception {
		userDAO = new UserDAO();
	}

	@Test
	public void testCreateUsers() {
		Users user1 = new Users();
		user1.setUserName("tran009");
		user1.setEmail("tran009@e.ntu.edu.sg");
		user1.setFullName("Danh Tran");
		user1.setPassword("tester10");

		user1 = userDAO.create(user1);

		assertTrue(user1.getUserId() > 0);
	}

	@Test(expected = PersistenceException.class)
	public void testCreateUsersFieldsNotSet() {
		Users user1 = new Users();
		user1 = userDAO.create(user1);
	}
	
	@Test
	public void testUpdateUsers() {
		Users user = new Users();
		user.setUserId(8);
		user.setUserName("tommyShelb");
		user.setEmail("ShelbyLead@gmail.com");
		user.setFullName("Tommy Shelby");
		user.setPassword("peakyblinder");
		
		user = userDAO.update(user);
		
		String expected = "peakyblinder";
		String actual = user.getPassword();
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void testGettUsersFound() {
		Integer userId = 1;
		Users user = userDAO.get(userId);
		
		if(user != null) {
			System.out.println(user.getEmail());
		}
		
		assertNotNull(user);
	}
	
	@Test
	public void testGettUsersNotFound() {
		Integer userId = 99;
		Users user = userDAO.get(userId);
		
		assertNull(user);
	}
	
	@Test
	public void testDeletetUsers() {
		Integer userId = 5;
		userDAO.delete(userId);
		
		Users user = userDAO.get(userId);
		assertNull(user);
	}
	
	@Test(expected = PersistenceException.class)
	public void testDeletetNonExistUsers() {
		Integer userId = 55;
		userDAO.delete(userId);
	}
	
	@Test
	public void testListAll(){
		List<Users> listUsers = userDAO.listAll();
		
		for (Users user : listUsers) {
			System.out.println(user.getEmail());
		}
		
		assertTrue(listUsers.size() > 0);
	}
	
	@Test
	public void testCount(){
		long totalUsers = userDAO.count();
		
		assertEquals(5, totalUsers);
	}
	
	@Test
	public void testfindByEmail(){
		String email = "ecllaw@e.ntu.edu.sg";
		Users user = userDAO.findByEmail(email);
		
		assertNotNull(user);
	}
	
	@Test
	public void testCheckLoginSuccess() {
		String email = "tuanminh002@e.ntu.edu.sg";
		String password = "tester1";
		boolean loginResult = userDAO.checkLogin(email, password);
		
		assertTrue(loginResult);
	}
	
	@Test
	public void testCheckLoginFailed() {
		String email = "ntminh220104@gmail.com";
		String password = "tester1";
		boolean loginResult = userDAO.checkLogin(email, password);
		
		assertFalse(loginResult);
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		userDAO.close();
	}

}
