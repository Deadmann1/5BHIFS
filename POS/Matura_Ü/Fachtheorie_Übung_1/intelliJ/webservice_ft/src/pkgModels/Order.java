package pkgModels;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Order {
	private int id = 0,
			    quantity = 0;
	private Customer customer = null;
	private Product product = null;
	private String orderdate = null;
				   
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public String getOrderdate() {
		return orderdate;
	}
	public void setOrderdate(String orderdate) {
		this.orderdate = orderdate;
	}
	
	@Override
	public String toString() {
		return "Order [id=" + id + ", quantity=" + quantity + ", customer="
				+ customer + ", product=" + product + ", orderdate="
				+ orderdate + "]";
	}
	public Order(int id, int quantity, Customer customer, Product product,
			String orderdate) {
		super();
		this.id = id;
		this.quantity = quantity;
		this.customer = customer;
		this.product = product;
		this.orderdate = orderdate;
	}
	public Order() {
		this(0, 0, new Customer(), new Product(), "01.01.1900");
	}
}
