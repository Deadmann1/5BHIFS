package pkgModels;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Customer {
	private int id = 0;
	private String name = null,
				   address = null;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@Override
	public String toString() {
		return "Customer [id=" + id + ", name=" + name + ", address=" + address
				+ "]";
	}
	public Customer(int id, String name, String address) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
	}
	public Customer() {
		this(0, "invalid name", "invalid address");
	}
}
