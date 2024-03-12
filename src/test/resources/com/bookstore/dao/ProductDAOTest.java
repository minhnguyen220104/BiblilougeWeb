package test.resources.com.bookstore.dao;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import main.resources.com.bookstore.dao.CategoryDAO;
import main.resources.com.bookstore.dao.ProductDAO;
import main.resources.com.bookstore.entity.Category;
import main.resources.com.bookstore.entity.Product;

public class ProductDAOTest{
	private static ProductDAO productDAO;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		productDAO = new ProductDAO();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		productDAO.close();
	}

	@Test
	public void testCreateProduct() throws IOException, ParseException {
		Product product = new Product();
		
		Category category = new Category();
		category.setCategoryId(5);
		category.setName("Accessories");
		
		product.setCategory(category);
		product.setName("WK61-Theme Keyboard");
		product.setDescription("Womier 60% Percent Keyboard, WK61 Mechanical RGB Wired Gaming Keyboard");
		product.setCountryMade("USA");
		product.setPrice((float) 56.55);
		product.setApprove((byte) 1);
		
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date addedDate = dateFormat.parse("03/03/2024");
		product.setAddDate(addedDate);
		
		String imagePath = "D:\\NTU\\IEM\\IM2073\\Icon\\WK61-Theme Keyboard.png";
		byte[] imageBytes = Files.readAllBytes(Paths.get(imagePath));
		product.setPicture(imageBytes);
		
		Product createdProduct = productDAO.create(product);
		
		assertTrue(createdProduct.getProductId() > 0);
		
	}

}
