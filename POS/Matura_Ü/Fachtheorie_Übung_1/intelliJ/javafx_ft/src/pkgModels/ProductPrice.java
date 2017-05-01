package pkgData;

import java.math.BigDecimal;
/**
 *
 * @author org
 * workaround for BigDecimal because JAXB needs default constructor
 */
public class ProductPrice {
	BigDecimal price = null;

	public String getPrice() {
		return price.toString();
	}

	public void setPrice(String price) {
		this.price = new BigDecimal(price);
	}

	@Override
	public String toString() {
		return "[price=" + price + "]";
	}
	public ProductPrice() {
		this("0.00");
	}

	public ProductPrice(String price) {
		super();
		setPrice(price);
	}

}
