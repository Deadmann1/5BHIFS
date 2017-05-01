package pkgData;

import java.io.Serializable;


public class AddonWrapper implements Serializable {
    private String username;
    private int id_addon;

    public AddonWrapper()
    {

    }

    public AddonWrapper(int id_addon, String username) {
        this.id_addon = id_addon;
        this.username = username;
    }

    public int getId_addon() {
        return id_addon;
    }

    public void setId_addon(int id_addon) {
        this.id_addon = id_addon;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "AddonWrapper{" +
                "id_addon=" + id_addon +
                ", username='" + username + '\'' +
                '}';
    }
}
