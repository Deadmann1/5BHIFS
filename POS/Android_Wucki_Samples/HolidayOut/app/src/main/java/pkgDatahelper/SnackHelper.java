package pkgDatahelper;

import pkgData.Snack;


public class SnackHelper {
    private Snack snack;
    private int amount;

    public SnackHelper() {
    }

    public SnackHelper(Snack _snack, int _amount) {
        this.snack = _snack;
        this.amount = _amount;
    }

    public Snack getSnack() {
        return snack;
    }

    public void setSnack(Snack snack) {
        this.snack = snack;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return snack.getSnackName() + "  €" + snack.getPrice() + "  " + amount + "x";
    }
}
