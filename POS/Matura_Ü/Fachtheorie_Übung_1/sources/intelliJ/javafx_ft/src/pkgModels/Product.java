package pkgData;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Product {
	private int id=0,
			    onStock = 0,
			    criticalLevel = 0;
	private String name = null;
	private ProductPrice price = null;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getOnStock() {
		return onStock;
	}
	public void setOnStock(int onStock) {
		this.onStock = onStock;
	}
	public int getCriticalLevel() {
		return criticalLevel;
	}
	public void setCriticalLevel(int criticalLevel) {
		this.criticalLevel = criticalLevel;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ProductPrice getPrice() {
		return price;
	}
	public void setPrice(ProductPrice price) {
		this.price = price;
	}
	public Product(int id, String name, int onStock, int criticalLevel, 
			ProductPrice price) {
		super();
		this.id = id;
		this.onStock = onStock;
		this.criticalLevel = criticalLevel;
		this.name = name;
		this.price = price;
	}
	public Product() {
		this(0,"invalid product", 0, 0, new ProductPrice("0.0"));
	}
	public Product(int id, String name) {
		this(id,name, 0, 0, new ProductPrice("0.0"));
	}
	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name
				+ ", onStock=" + onStock
				+ ", criticalLevel=" + criticalLevel +  
				", price=" + price + "]";
	}
	
	
}
