package main.resources.com.bookstore.entity;
// Generated Mar 11, 2024, 7:51:47 AM by Hibernate Tools 6.3.1.Final

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Column;
import main.resources.com.bookstore.entity.Product;
import main.resources.com.bookstore.entity.ProductOrder;

@Entity
public class Orderline implements java.io.Serializable {

	private OrderlineId id = new OrderlineId();
	private Product product;
	private ProductOrder order;
	private int quantity;
	private float subtotal;

	public Orderline() {
	}
	
	public Orderline(OrderlineId id) {
		this.id = id;
	}

	public Orderline(OrderlineId id, Product product, ProductOrder order, int quantity,float subtotal) {
		this.id = id;
		this.product = product;
		this.order = order;
		this.quantity = quantity;
		this.subtotal = subtotal;
	}

	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "orderId", column = @Column(name = "order_id", nullable = false)),
			@AttributeOverride(name = "productId", column = @Column(name = "product_id", nullable = false))})
	public OrderlineId getId() {
		return this.id;
	}

	public void setId(OrderlineId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "product_id")
	public Product getProduct() {
		return this.product;
	}

	public void setProduct(Product product) {
		this.product = product;
		this.id.setProduct(product);
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id")
	public ProductOrder getOrder() {
		return this.order;
	}

	public void setOrder(ProductOrder order) {
		this.order = order;
		this.id.setOrder(order);
	}
	
	@Column(name="quantity")
	public int getQuantity() {
		return this.quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Column(name="subtotal")
	public float getSubtotal() {
		return this.subtotal;
	}

	public void setSubtotal(float subtotal) {
		this.subtotal = subtotal;
	}

}
