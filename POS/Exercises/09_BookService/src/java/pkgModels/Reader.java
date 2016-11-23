package pkgModels;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Reader implements Serializable{
	private String name;
	private String adress;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAdress() {
		return adress;
	}
	public void setAdress(String adress) {
		this.adress = adress;
	}
	public Reader(String name, String adress) {
		super();
		this.name = name;
		this.adress = adress;
	}
	public Reader() {
		super();
	}
	@Override
	public String toString() {
		return "Reader [name=" + name + ", adress=" + adress + "]";
	}
}
