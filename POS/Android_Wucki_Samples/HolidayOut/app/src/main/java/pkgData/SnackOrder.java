package pkgData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import pkgDatahelper.SnackHelper;

public class SnackOrder implements Serializable {
    private List<SnackHelper> sh;
    private String username;

    public SnackOrder() {
        sh = new ArrayList<SnackHelper>();
    }

    public SnackOrder(List<SnackHelper> sh, String username) {
        this.sh = sh;
        this.username = username;
    }

    public List<SnackHelper> getSh() {
        return sh;
    }

    public void setSh(List<SnackHelper> sh) {
        this.sh = sh;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
