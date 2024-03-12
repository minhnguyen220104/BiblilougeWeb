package main.resources.com.bookstore.dao;

import java.util.List;

import jakarta.persistence.EntityManager;
import main.resources.com.bookstore.entity.Category;

public class CategoryDAO extends JpaDAO<Category> implements GenericDAO<Category> {

	public CategoryDAO() {
	}

	@Override
	public Category create(Category category) {
		return super.create(category);
	}
	
	@Override
	public Category update(Category category) {
		return super.update(category);
	}
	
	@Override
	public Category get(Object id) {
		return super.find(Category.class, id);
	}

	@Override
	public void delete(Object id) {
		super.delete(Category.class, id);
		
	}

	@Override
	public List<Category> listAll() {
		return super.findWithNameQuery("Category.findAll");
	}

	@Override
	public long count() {
		return super.countWithNameQuery("Category.countAll");
	}
	
	public Category findByName(String name) {
		List<Category> listCategory = super.findWithNameQuery("Category.findByName", "name", name);
		
		if(listCategory != null && listCategory.size()> 0) {
			return listCategory.get(0);
		}
		return null;
	}

}
