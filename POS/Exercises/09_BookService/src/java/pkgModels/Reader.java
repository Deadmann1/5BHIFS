package pkgModels;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Reader implements Serializable, Comparable {

    private int id;
    private String name;
    private String adress;

    public Reader(int id, String name, String adress) {
        super();
        this.id = id;
        this.name = name;
        this.adress = adress;
    }
    
    public Reader(int id) {
        super();
        this.id = id;
    }
    
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

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    

    public Reader() {
        super();
    }

    @Override
    public String toString() {
        return "Reader [name=" + name + ", adress=" + adress + "]";
    }

    @Override
    public int compareTo(Object o) {
        int retVal;
        Reader r = (Reader)o;
        if(this.id == r.id) {
            retVal = 0;
        }
        else {
            retVal = this.name.compareTo(r.name);
        }
        return retVal;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
    
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Reader other = (Reader) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
    
    
    
}
