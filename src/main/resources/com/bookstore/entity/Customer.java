package main.resources.com.bookstore.entity;
// Generated Mar 3, 2024, 3:34:06 PM by Hibernate Tools 6.3.1.Final

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import main.resources.com.bookstore.entity.ProductOrder;

@Entity
@NamedQueries({
	@NamedQuery(name = "Customer.findAll", query = "SELECT c FROM Customer c ORDER BY c.registerDate DESC"),
	@NamedQuery(name = "Customer.countAll", query = "SELECT COUNT(c.email) FROM Customer c"),
	@NamedQuery(name = "Customer.findByEmail", query = "SELECT c FROM Customer c WHERE c.email = :email"),
	@NamedQuery(name = "Customer.checkLogin", query = "SELECT c FROM Customer c WHERE c.email = :email AND c.password = :pass"),
})
public class Customer implements java.io.Serializable {

	private int customerId;
	private String email;
	private String fullName;
	private String address;
	private String city;
	private String country;
	private String phoneNumber;
	private String zipcode;
	private String password;
	private Date registerDate;
	private Set<ProductOrder> orders = new HashSet<ProductOrder>(0);
	private Set<Review> reviews = new HashSet<Review>(0);

	public Customer() {
	}

	public Customer(int customerId, String email, String fullName, String address, String city, String country,
			String phoneNumber, String zipcode, String password, Date registerDate, Set<ProductOrder> orders, Set<Review> reviews) {
		this.customerId = customerId;
		this.email = email;
		this.fullName = fullName;
		this.address = address;
		this.city = city;
		this.country = country;
		this.phoneNumber = phoneNumber;
		this.zipcode = zipcode;
		this.password = password;
		this.registerDate = registerDate;
		this.orders = orders;
		this.reviews = reviews;
	}

	@Column(name = "customer_id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "full_name")
	public String getFullName() {
		return this.fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Column(name = "phone_number")
	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getZipcode() {
		return this.zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "register_date")
	@Temporal(TemporalType.DATE)
	public Date getRegisterDate() {
		return this.registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
	public Set<ProductOrder> getOrders() {
		return this.orders;
	}

	public void setOrders(Set<ProductOrder> orders) {
		this.orders = orders;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
	public Set<Review> getReviews() {
		return this.reviews;
	}

	public void setReviews(Set<Review> reviews) {
		this.reviews = reviews;
	}

}
