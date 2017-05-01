package pkgData;


public class Addon {
    private int id_addon;
    private String name;
    private int price;
    private String desc;

    public Addon() {

    }

    public Addon(int _ID_Addon, String _name, int _price, String _description) {
        this.id_addon = _ID_Addon;
        this.name = _name;
        this.price = _price;
        this.desc = _description;
    }

    public int getID_Addon() {
        return id_addon;
    }

    public void setID_Addon(int ID_Addon) {
        this.id_addon = ID_Addon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return desc;
    }

    public void setDescription(String description) {
        this.desc = description;
    }

    @Override
    public String toString() {
        return name + "  €" + price;
    }
}
