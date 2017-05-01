package pkgModel;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by John_13 on 13.01.2017.
 */
public class Product implements Serializable{
    private int id, onStock;
    private String name;
    private Date onMarket;
    private Producer producer;

    public Product(int id, String name, int onStock, Date onMarket)  {
        this.id = id;
        this.onStock = onStock;
        this.name = name;
        this.onMarket = onMarket;
    }

    public Product() {
    }

    public Product(String name) {
        this(-99, name, -99, null);
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getOnMarket() {
        return onMarket;
    }

    public void setOnMarket(Date onMarket) {
        this.onMarket = onMarket;
    }

    public Producer getProducer() {
        return producer;
    }

    public void setProducer(Producer producer) {
        this.producer = producer;
    }

    public String toLongString() {
        String format = new SimpleDateFormat("dd.MMM.yyyy").format(onMarket);
        return "id=" + id + ", onStock=" + onStock + ", name=" + name + ", onMarket=" + format + ", producer=" + producer.toLongString() + '}';
    }

    @Override
    public String toString() {
        return name + ", onStock=" + onStock;
    }
}