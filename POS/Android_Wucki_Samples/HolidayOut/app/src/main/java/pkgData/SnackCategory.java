package pkgData;

import java.io.Serializable;


public class SnackCategory implements Serializable {
    private String snack_type;

    public SnackCategory(String snack_type) {
        this.snack_type = snack_type;
    }

    public SnackCategory() {
    }

    public String getSnack_type() {
        return snack_type;
    }

    public void setSnack_type(String snack_type) {
        this.snack_type = snack_type;
    }

    @Override
    public String toString() {
        return snack_type;
    }
}
