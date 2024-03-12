package test.resources.com.bookstore.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import main.resources.com.bookstore.dao.CategoryDAO;
import main.resources.com.bookstore.entity.Category;
import main.resources.com.bookstore.entity.Users;

public class CategoryDAOTest{
	private static CategoryDAO categoryDAO;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		categoryDAO = new CategoryDAO();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		categoryDAO.close();
	}

	@Test
	public void testCreateCategory() {
		Category newCat = new Category();
		newCat.setName("Calculator");
		newCat.setDescription("A device that performs arithmetic operations on numbers");
		Category category = categoryDAO.create(newCat);
		
		assertTrue(category != null && category.getCategoryId() > 0);
	}

	@Test
	public void testUpdateCategory() {
		Category cat = new Category();
		cat.setCategoryId(7);
		cat.setName("Keypad");
		cat.setDescription("A device that performs arithmetic operations on numbers");
		
		Category category = categoryDAO.update(cat);
		
		assertEquals(cat.getName(), category.getName());
	}

	@Test
	public void testGet() {
		Integer catId = 3;
		Category cat = categoryDAO.get(catId);
		
		assertNotNull(cat);
	}

	@Test
	public void testDeleteCategory() {
		Integer catId = 7;
		categoryDAO.delete(catId);
		
		Category cat = categoryDAO.get(catId);
		
		assertNull(cat);
	}

	@Test
	public void testListAll() {
		List<Category> listCategory = categoryDAO.listAll();
		
		for (Category category : listCategory) {
			System.out.println(category.getName());
		}
		
		assertTrue(listCategory.size() > 0);
	}

	@Test
	public void testCount() {
		long totalCategory = categoryDAO.count();
		
		assertEquals(6, totalCategory);
	}
	
	@Test
	public void testfindByName(){
		String name = "Laptop";
		Category category = categoryDAO.findByName(name);
		
		assertNotNull(category);
	}

}
