package test.resources.com.bookstore.entity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import main.resources.com.bookstore.entity.Category;
import main.resources.com.bookstore.entity.Users;

public class CategoryTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Category newCategory = new Category();
		newCategory.setName("Smartphones");
		newCategory.setDescription("Mobile device that combines the functions of a traditional mobile phone with various advanced features typically found in computers");
		
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("BibliolougeShop");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		
		entityManager.getTransaction().begin();
		entityManager.persist(newCategory);
		entityManager.getTransaction().commit();
		entityManager.close();
		entityManagerFactory.close();
		
		System.out.println("A Category object was persisted");
	}
}
