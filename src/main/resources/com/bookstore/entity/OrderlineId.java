package main.resources.com.bookstore.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.util.Objects;

import jakarta.persistence.Column;

// Generated Mar 11, 2024, 7:51:47 AM by Hibernate Tools 6.3.1.Final

@Embeddable
public class OrderlineId implements java.io.Serializable {

	private Product product;
	private ProductOrder order;
	
	public OrderlineId() {
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id",insertable = false, updatable = false, nullable = false)
	public Product getProduct() {
		return this.product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id", insertable = false, updatable = false, nullable = false)
	public ProductOrder getOrder() {
		return this.order;
	}

	public void setOrder(ProductOrder order) {
		this.order = order;
	}

	@Override
	public int hashCode() {
		return Objects.hash(order, product);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderlineId other = (OrderlineId) obj;
		return Objects.equals(order, other.order) && Objects.equals(product, other.product);
	}
	
	

}
