package main.resources.com.bookstore.entity;
// Generated Mar 3, 2024, 3:34:06 PM by Hibernate Tools 6.3.1.Final

import java.util.Base64;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;
import main.resources.com.bookstore.entity.Orderline;


@Entity
@NamedQueries({
	@NamedQuery(name = "Product.findAll", query = "SELECT p FROM Product p"),
	@NamedQuery(name = "Product.countAll", query = "SELECT COUNT(*) FROM Product"),
	@NamedQuery(name = "Product.findByName", query = "SELECT p FROM Product p WHERE p.name = :name"),
	@NamedQuery(name = "Product.findByCategory", query = "SELECT p FROM Product p JOIN Category c ON p.category.categoryId = c.categoryId AND c.categoryId = :catId"),
	@NamedQuery(name = "Product.countByCategory", query = "SELECT COUNT(p) FROM Product p WHERE p.category.categoryId = :catId"),
	@NamedQuery(name = "Product.listNew", query = "SELECT p FROM Product p ORDER BY p.addDate DESC"),
	@NamedQuery(name = "Product.search", query = "SELECT p FROM Product p WHERE p.name LIKE '%' || :keyword || '%'"
					+ " OR p.countryMade LIKE '%' || :keyword || '%'"
					+ " OR p.description LIKE '%' || :keyword || '%'")
	
})
public class Product implements java.io.Serializable {

	private int productId;
	private Category category;
	private String name;
	private String description;
	private String countryMade;
	private byte[] picture;
	private String base64Image;
	private float price;
	private Date addDate;
	private byte approve;
	private Set<Review> reviews = new HashSet<Review>(0);
	private Set<Orderline> orderlines = new HashSet<Orderline>(0);

	public Product() {
	}

	
	public Product(int productId) {
		super();
		this.productId = productId;
	}


	public Product(int productId, Category category, String name, String description, String countryMade,
			byte[] picture, float price, Date addDate, byte approve, Set<Review> reviews, Set<Orderline> orderlines) {
		this.productId = productId;
		this.category = category;
		this.name = name;
		this.description = description;
		this.countryMade = countryMade;
		this.picture = picture;
		this.price = price;
		this.addDate = addDate;
		this.approve = approve;
		this.reviews = reviews;
		this.orderlines = orderlines;
	}

	@Column(name = "product_id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getProductId() {
		return this.productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "category_id")
	public Category getCategory() {
		return this.category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Lob
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "country_made")
	public String getCountryMade() {
		return this.countryMade;
	}

	public void setCountryMade(String countryMade) {
		this.countryMade = countryMade;
	}

	public byte[] getPicture() {
		return this.picture;
	}

	public void setPicture(byte[] picture) {
		this.picture = picture;
	}

	@Override
	public int hashCode() {
		return Objects.hash(productId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		return productId == other.productId;
	}

	public float getPrice() {
		return this.price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	@Column(name = "add_date")
	@Temporal(TemporalType.DATE)
	public Date getAddDate() {
		return this.addDate;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}

	public byte getApprove() {
		return this.approve;
	}

	public void setApprove(byte approve) {
		this.approve = approve;
	}

	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
	public Set<Review> getReviews() {
		return this.reviews;
	}

	public void setReviews(Set<Review> reviews) {
		this.reviews = reviews;
	}

	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
	public Set<Orderline> getOrderlines() {
		return this.orderlines;
	}

	public void setOrderlines(Set<Orderline> orderlines) {
		this.orderlines = orderlines;
	}
	
	@Transient
	public String getBase64Image() {
		this.base64Image = Base64.getEncoder().encodeToString(picture);
		return this.base64Image;
	}

	@Transient
	public void setBase64Image(String base64Image) {
		this.base64Image = base64Image;
	}
}
