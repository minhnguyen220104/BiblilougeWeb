package main.resources.com.bookstore.dao;

import java.util.Date;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import main.resources.com.bookstore.entity.Category;
import main.resources.com.bookstore.entity.Product;

public class ProductDAO extends JpaDAO<Product> implements GenericDAO<Product> {

	public ProductDAO() {
	}
	
	@Override
	public Product create(Product product) {
		product.setAddDate(new Date());
		return super.create(product);
	}
	
	@Override
	public Product update(Product product) {
		product.setAddDate(new Date());
		return super.update(product);
	}

	@Override
	public Product get(Object productId) {
		return super.find(Product.class, productId);
	}

	@Override
	public void delete(Object productId) {
		super.delete(Product.class, productId); 
	}

	@Override
	public List<Product> listAll() {
		return super.findWithNameQuery("Product.findAll");
	}
	
	public Product findByName(String name) {
		List<Product> listProduct = super.findWithNameQuery("Product.findByName", "name", name);
		
		if(!listProduct.isEmpty()) {
			return listProduct.get(0);
		}
		return null;
	}

	public List<Product> listByCategory(int categoryId){
		return super.findWithNameQuery("Product.findByCategory", "catId", categoryId);
	}
	
	public List<Product> search(String keyword){
		return super.findWithNameQuery("Product.search", "keyword", keyword);
	}
	
	public List<Product> listNewProduct(){
		
		return super.findWithNameQuery("Product.listNew", 0, 4);
	}
	
	@Override
	public long count() {
		return super.countWithNameQuery("Product.countAll");
	}
	
	public long countByCategory(int categoryId) {
		return super.countWithNameQuery("Product.countByCategory", "catId", categoryId);
		
	}

}
