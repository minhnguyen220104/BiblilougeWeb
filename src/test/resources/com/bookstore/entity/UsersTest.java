package test.resources.com.bookstore.entity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import main.resources.com.bookstore.entity.Users;

public class UsersTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Users user1 = new Users();
		user1.setUserName("chef");
		user1.setEmail("currychef@gmail.com");
		user1.setFullName("Stephe Curry");
		user1.setPassword("tester4");
		
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("BibliolougeShop");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		
		entityManager.getTransaction().begin();
		entityManager.persist(user1);
		entityManager.getTransaction().commit();
		entityManager.close();
		entityManagerFactory.close();
		
		System.out.println("A user object was persisted");
	}
}
