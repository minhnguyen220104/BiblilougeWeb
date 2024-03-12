package main.resources.com.bookstore.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.resources.com.bookstore.entity.ProductOrder;

public class OrderDAO extends JpaDAO<ProductOrder> implements GenericDAO<ProductOrder> {
	@Override
	public ProductOrder create(ProductOrder order) {
		order.setOrderDate(new Date());
		order.setStatus("Processing");
		return super.create(order);
	}

	@Override
	public ProductOrder update(ProductOrder order) {
		return super.update(order);
	}

	@Override
	public ProductOrder get(Object orderId) {
		return super.find(ProductOrder.class, orderId);
	}
	
	public ProductOrder get(Integer orderId, Integer customerId) {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("orderId", orderId);
		parameters.put("customerId", customerId);
		
		List<ProductOrder> result = super.findWithNameQuery("ProductOrder.findByIdAndCustomer", parameters);
		
		if (!result.isEmpty()) {
			return result.get(0);
		}
		return null;
	}

	@Override
	public void delete(Object orderId) {
		super.delete(ProductOrder.class, orderId);
		
	}

	@Override
	public List<ProductOrder> listAll() {
		return super.findWithNameQuery("ProductOrder.findAll");
	}

	@Override
	public long count() {
		return super.countWithNameQuery("ProductOrder.countAll");
	}
	
	public List<ProductOrder> listByCustomer(Integer customerId) {
		
		return super.findWithNameQuery("ProductOrder.findByCustomer", "customerId", customerId);
	}
	
	
	public List<ProductOrder> listMostRecentSales() {
		return super.findWithNameQuery("ProductOrder.findAll", 0, 3);
	}
}
