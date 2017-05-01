package pkgDatamanager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import AsyncTasks.AsyncOrderSnackCart;
import AsyncTasks.AsyncSnackCat;
import AsyncTasks.AsyncSnacks;
import pkgData.Snack;
import pkgData.SnackCategory;
import pkgData.SnackOrder;
import pkgDatahelper.SnackHelper;


public class DatamanagerSnacks {

    private static DatamanagerSnacks dm = null;
    private List<String> listCategories = null;
    private List<Snack> listSnacks = null;


    private DatamanagerSnacks() {
        listSnacks = getAll();
    }

    public static DatamanagerSnacks getInstance() {
        if(dm == null) {
            dm = new DatamanagerSnacks();
        }
        return dm;
    }

    public List<Snack>getAll()
    {
        List<Snack> ret = null;
        try {
            ret = new AsyncSnacks().execute().get();
            for(int i = 0;i<ret.size();i++)
            {
                System.out.println(ret.get(i));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public List<SnackCategory> getAllCategories() {
        List<SnackCategory> ret = null;
        try {
            ret= new AsyncSnackCat().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    public List<Snack> getAllDrinks() {
        List<Snack> listDrinks = new ArrayList<Snack>();
        for(Snack s : listSnacks) {
            if(s.getSnackType().compareTo("Drink") == 0) {
                listDrinks.add(s);
            }
        }
        return listDrinks;
    }

    public List<Snack> getAllSweets() {
        List<Snack> listSweets = new ArrayList<Snack>();
        for(Snack s : listSnacks) {
            if(s.getSnackType().compareTo("Sweet") == 0) { //2 = sweets
                listSweets.add(s);
            }
        }
        return listSweets;
    }

    public List<Snack> getAllSnacks() {
        List<Snack> listS = new ArrayList<Snack>();
        for(Snack s : listSnacks) {
            if(s.getSnackType().compareTo("Snack") == 0) { //3 = Snacks
                listS.add(s);
            }
        }
        return listS;
    }

    public void orderShoppingCart(List<SnackHelper> shoppingCart) {
        SnackOrder ss = new SnackOrder();
        ss.setUsername(Credentials.getInstance().getUsername());
        ss.setSh(shoppingCart);
        new AsyncOrderSnackCart(ss).execute();
    }
}
