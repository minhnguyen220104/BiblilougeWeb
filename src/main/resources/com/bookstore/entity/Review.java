package main.resources.com.bookstore.entity;
// Generated Mar 3, 2024, 3:34:06 PM by Hibernate Tools 6.3.1.Final

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
public class Review implements java.io.Serializable {

	private int reviewId;
	private Customer customer;
	private Product product;
	private Integer rating;
	private String comment;
	private Timestamp reviewTime;

	public Review() {
	}

	public Review(int reviewId, Customer customer, Product product, Integer rating, String comment,
			Timestamp reviewTime) {
		this.reviewId = reviewId;
		this.customer = customer;
		this.product = product;
		this.rating = rating;
		this.comment = comment;
		this.reviewTime = reviewTime;
	}

	@Column(name = "review_id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getReviewId() {
		return this.reviewId;
	}

	public void setReviewId(int reviewId) {
		this.reviewId = reviewId;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "customer_id")
	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "product_id")
	public Product getProduct() {
		return this.product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Integer getRating() {
		return this.rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	@Lob
	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Column(name = "review_time")
	@Temporal(TemporalType.TIMESTAMP)
	public Timestamp getReviewTime() {
		return this.reviewTime;
	}

	public void setReviewTime(Timestamp reviewTime) {
		this.reviewTime = reviewTime;
	}

}
